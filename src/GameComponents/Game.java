package GameComponents;

import Database.*;
import GUI.LoginPanel;
import Quizgame.shared.Answer;
import Quizgame.shared.Message;
import Quizgame.shared.MessageType;
import Quizgame.shared.User;
import Server.Connections;
import Server.ServerListener;
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
        System.out.println("In Game, startGame was reached. User name is: " + player.getUsername());
        this.category = category;
        if (!activeSets.isEmpty()) {
            System.out.println("In Game, startGame, active sets is NOT empty");
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
        System.out.println("In Game, startNewSet was reached");
        set = new Set(player, category, maxPlayers, maxNumberOfQuestions, maxNumberOfMatches);
        activeSets.add(set);
    }
    public static void continueGame(Answer answer){
        System.out.println("In Game, continueGame was reached");
        for (Set s : getActiveSets()) {
            for (Match m : s.getMatches()) {
                List<User> players = m.getPlayersList();
                for (User u : players) {
                    System.out.println("In continue game, user is: " + u.getUsername());
                    if (u.getUsername().equals(answer.getUser().getUsername())) {
                        m.addPointsToList(answer);
                        m.sendQuestion();
                    }
                }
            }
        }
    }

    public static void sendQuestion(List<User> users, Question question) {
        System.out.println("-_-_-_-_In Game, sendQuestion was reached");
        MatchQuestion matchQuestion = new MatchQuestion(users, question);
        ServerProtocol.processInput(new Message(MessageType.QUESTION, matchQuestion));
    }
    public static void sendMatchScore(Match match){
        System.out.println("In Game, sendMatchScore was reached");
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
