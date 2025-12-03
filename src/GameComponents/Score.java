package GameComponents;

import Database.Question;
import Quizgame.shared.User;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Score implements Serializable {
    User player1;
    User player2;
    List<int[]>roundScore;
    String category;

    public Score(List<int[]> roundScore, User player1, User player2, String category){
        //roundScore är typ [1, 0] [1, 1] [0, 0]
        this.player1 = player1;
        this.player2 = player2;
        this.roundScore = roundScore;
        this.category = category;

    }
    public int[] getRoundScoresPlayer1(){
        int[] player1Scores = new int[roundScore.size()];
        for (int i = 0; i < roundScore.size(); i++){
            player1Scores[i] = roundScore.get(i)[0];
        }
        return player1Scores;
    }
    public int[] getRoundScoresPlayer2(){
        int[] player2Scores = new int[roundScore.size()];
        for (int i = 0; i < roundScore.size(); i++){
            player2Scores[i] = roundScore.get(i)[1];
        }
        return player2Scores;
    }

    public User getPlayer1(){
        return player1;
    }
    public User getPlayer2(){
        return player2;
    }
    public List<int[]> getRoundScore (){
        return roundScore;
    }
    public String getCategory(){
        return category;
    }

    public List<User>getPlayers(){
        List<User>players = new ArrayList<>();
        players.add(player1);
        players.add(player2);
        return players;
    }


}
