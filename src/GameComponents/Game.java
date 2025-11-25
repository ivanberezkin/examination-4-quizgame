package GameComponents;

import Database.*;
import Quizgame.shared.User;

import java.util.ArrayList;
import java.util.List;

public class Game {
    private int maxNumberOfQuestions = 3;
    private int maxNumberOfMatches = 2;
    private int maxPlayers = 2;

    private Database.Question.Category[] category;
    private static List<Set> activeSets = new ArrayList<>();
    private Set set;

    public Game(User player, Database.Question.Category[] category){
        this.category = category;
        startGame(player);
    }
    public void startGame(User player){
        if (activeSets.size() > 0) {
            for (Set s : activeSets) {
                if (s.getNumberOfPlayers() < 2) {
                    s.addPlayer(player);
                }
            }
        }
        else {
            set = new Set(player, category, maxPlayers, maxNumberOfQuestions, maxNumberOfMatches);
            activeSets.add(set);
        }
    }
    public static void sendQuestion(Question question, User [] players) {

    }
    public static void sendFirstQuestion(Question question, User player) {

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
    public static void sendScore(Score score, User [] players){
        //send results for question
    }
    public static void sendMatchScore(User [] players, int matchID){
        for (Set s : activeSets){
            for (Match thisMatch :  s.getMatches()) {
                if (thisMatch.getMatchID() == matchID) {
                    //send results for match
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
}
