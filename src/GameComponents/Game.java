package GameComponents;

import Database.*;
import GUI.LoginPanel;
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
    private static List<Set> activeSets = new ArrayList<>();
    private Set set;

    public Game(List <Connections> players, Question.Category category){
        this.category = category;
        startGame(players);
    }
    public void startGame(List<Connections>players){
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
    private void startNewSet(List <Connections> players) {
        set = new Set(players, category, maxPlayers, maxNumberOfQuestions, maxNumberOfMatches);
        activeSets.add(set);
    }
    public static void continueGame(List<Connections> players){
        for (Game g : players.getFirst().getUser().getGames()){
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

    public static void sendQuestion(List<Connections> connections, Question question) {
        ServerProtocol.processInput(new Message(MessageType.QUESTION, question));
        send(connections, new Message(MessageType.QUESTION, question));
    }
    public static void sendFirstQuestion(Connections connection, Question question) {
        List <Connections> onePlayer = new ArrayList<>();
        onePlayer.add(connection);
        send(onePlayer, new Message(MessageType.QUESTION, question));

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
    public static void sendMatchScore(List<Connections>players, int matchID){
        for (Set s : activeSets){
            for (Match thisMatch :  s.getMatches()) {
                if (thisMatch.getMatchID() == matchID) {
                    send(players, new Message(MessageType.RESULT_ROUND, thisMatch));
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
    private static void send(List<Connections> players, Message message){
        for (Connections c : players){
            c.send(message);
        }
    }
}
