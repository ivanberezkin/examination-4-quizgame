package GameComponents;

import Database.*;
import Quizgame.shared.Answer;
import Quizgame.shared.Message;
import Quizgame.shared.MessageType;
import Quizgame.shared.User;
import Server.ServerProtocol;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Game implements Serializable {
    private int maxNumberOfQuestions = 3;
    private int maxNumberOfMatches = 2;
    private int maxPlayers = 2;

    private Question.Category category;
    private static final List<Set> activeSets = new ArrayList<>();
    private Set set;

    public Game(){

    }
    public void startGame(User player, Question.Category category) {
        this.category = category;
        if (!activeSets.isEmpty()) {
            for (Set s : activeSets) {
                if (s.getNumberOfPlayers() == 1) {
                    s.addPlayer(player);
                }
            }
        }
        else {
            startNewSet(player);
        }
    }

    private void startNewSet(User player) {
        Set set = new Set(player, category, maxPlayers, maxNumberOfQuestions, maxNumberOfMatches);
        activeSets.add(set);
        set.startMatch(player);
    }
    public static void continueGame(Answer answer){
        for (Set s : getActiveSets()) {
            for (Match m : s.getMatches()) {
                List<User> players = m.getPlayersList();
                for (User u : players) {
                    if (u.getUsername().equals(answer.getUser().getUsername())) {
                        m.addPointsToList(answer);
                        m.sendQuestion();
                    }
                }
            }
        }
    }

    public static void sendQuestion(List<User> users, Question question) {
        MatchQuestion matchQuestion = new MatchQuestion(users, question);
        ServerProtocol.processInput(new Message(MessageType.QUESTION, matchQuestion));
    }
    public static void sendMatchScore(Match match){
        ServerProtocol.processInput(new Message(MessageType.RESULT_ROUND, match));
        checkSets();
    }
    private static void removeCompletedSet(Set set){
        activeSets.remove(set);
    }
    private static void checkSets(){
        for (Set s : activeSets){
            if(s.checkIfCompleted()){
                removeCompletedSet(s);
            }
        }
    }
    private static List<Set>getActiveSets(){
        return activeSets;

    }
}
