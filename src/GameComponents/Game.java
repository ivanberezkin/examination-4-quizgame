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
    private static List<Set> activeSets = new ArrayList<>();
    private Set set;

    public Game(){
        System.out.println("Game was reached");

    }
    public void startGame(Connections player, Question.Category category) {
        System.out.println("- - - startGame in Game is reached");
        this.category = category;
        if (!activeSets.isEmpty()) {
            for (Set s : activeSets) {
                if (s.getNumberOfPlayers() == 1) {
                    s.addPlayer(player);
                    System.out.println("in startGame, added player is: " + player.getUser().getUsername());
                }
            }
        }
           else {
                startNewSet(player);
            }
        }

    private void startNewSet(Connections player) {
        System.out.println("startNewSet is reached, activeSets.length is: " + activeSets.size());
        set = new Set(player, category, maxPlayers, maxNumberOfQuestions, maxNumberOfMatches);
        activeSets.add(set);
    }
    public static void continueGame(Answer answer){
        System.out.println("In Game, continueGame is reached");
        for (Set s : getActiveSets())
            for (Match m : s.getMatches()) {
                List<User>players = m.getPlayersList();
                for (User u : players)
                    if (u.getUsername().equals(answer.getUser().getUsername())){
                        System.out.println("Username in continueGame is: " + answer.getUser().getUsername());
                        m.addPointsToList(answer);
                        m.sendQuestion();
                    }
                }
            }

    public static void sendQuestion(List<Connections> connections, Question question) {
        System.out.println("sendQuestion in Game is reached");
        ServerProtocol.processInput(new Message(MessageType.QUESTION, question));
        send(connections, new Message(MessageType.QUESTION, question));
    }
    public static void sendFirstQuestion(Connections connection, Question question) {
        List <Connections> onePlayer = new ArrayList<>();
        onePlayer.add(connection);
        send(onePlayer, new Message(MessageType.QUESTION, question));

    }

    public static void sendMatchScore(Match match){
        List<Connections> players = match.getPlayerList();
                    send(players, new Message(MessageType.RESULT_ROUND, match));
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
    private static void send(List<Connections> players, Message message){
        for (Connections c : players){
            c.send(message);
        }
    }
}
