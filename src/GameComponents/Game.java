package GameComponents;

import Database.*;
import Quizgame.shared.Message;
import Quizgame.shared.MessageType;
import Quizgame.shared.User;
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
    private static List<Set> activeSets = new ArrayList<>();
    private Set set;

    public Game(List <User> players, Question.Category category){
        this.category = category;
        startGame(players);
    }
    public void startGame(List<User>players){
         if (players.size() == 1 && !activeSets.isEmpty()) {
            for (Set s : activeSets) {
                if (s.getNumberOfPlayers() == 1) {
                    s.addPlayer(players.getFirst());
                }
            }
        }
        else {
            startNewSet(players);
        }
    }
    private void startNewSet(List <User> players) {
        set = new Set(players, category, maxPlayers, maxNumberOfQuestions, maxNumberOfMatches);
        activeSets.add(set);
    }
    public static void continueGame(List<User> players){
        for (Game g : players.getFirst().getGames()){
            for (Set s : g.getActiveSets())
                for (Match m : s.getMatches()) {
                    for (User u : m.getPlayerList()) {
                        if (u.getUsername().equals(players.get(0)) || u.getUsername().equals(players.get(1))){
                            m.sendQuestion();
                        }
                    }
                }
        }
    }

    public static void sendQuestion(Question question, List<User> players, Match match) {
        ServerProtocol.processInput(new Message(MessageType.QUESTION, question, players));
    }
    public static void sendFirstQuestion(Question question, User player) {
        List<User> players = new ArrayList<>();
        players.add(player);
        ServerProtocol.processInput(new Message(MessageType.QUESTION, question, players));
    }

    public void receiveScore (Score score){
        for (Set s : activeSets){
            for (Match thisMatch :  s.getMatches()){
                if (thisMatch.getMatchID() == score.getMatch().getMatchID()){
                    thisMatch.assignPoints(score);
                    s.getMatchScore();
                    s.continuePlaying();
                }
            }
        }
    }
    public static void sendMatchScore(List<User>players, int matchID){
        for (Set s : activeSets){
            for (Match thisMatch :  s.getMatches()) {
                if (thisMatch.getMatchID() == matchID) {
                    Message message = new Message(MessageType.RESULT_ROUND, thisMatch, players);
                }
            }
        }
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
    private List<Set>getActiveSets(){
        return activeSets;
    }
}
