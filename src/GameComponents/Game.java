package GameComponents;

import Database.*;
import Quizgame.shared.User;

import java.util.ArrayList;
import java.util.List;

public class Game {
    private int numberOfQuestions;
    private int numberOfMatches = 2;
    private User player1;
    private User player2;
    private User[]players;
    private Database.Question.Category[] category;
    private List<Set> activeSets = new ArrayList<>();
    private Set set;

    public Game(User player, Database.Question.Category[] category){
        this.player1 = player;
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
             set = new Set(player, category, numberOfQuestions, numberOfMatches);
                activeSets.add(set);
            }
        }

    public void receiveScore (Score score){
        for (Set s : activeSets){
            for (Match m :  s.getMatches()){
                if (m.getMatchID() == score.getMatch().getMatchID()){
                    s.getMatchScore(m, score);
                }
            }
        }
    }


    private void removeCompletedSet(Set set){
        activeSets.remove(set);
    }
    private void checkSets(){
        for (Set s : activeSets){
            if(s.checkIfCompleted()){
                removeCompletedSet(s);
            }
        }
    }
}
