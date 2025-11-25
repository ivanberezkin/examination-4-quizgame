package GameComponents;

import Database.*;
import Quizgame.shared.Message;
import Quizgame.shared.User;

import java.util.Random;

public class Match {
    private int matchID;
    private User player1;
    private User player2;
    private User [] players;
    private Database.Question.Category category;
    private int pointsPlayer1 = 0;
    private int pointsPlayer2 = 0;
    private int[]matchPoints = new int []{50, 50};
    private int numberOfQuestions;
    private Question[]questions;
    private int numberOfAnswers = 0;
    Random random = new Random();

    private User winner;
    private boolean completed = false;

    public Match(User user, Database.Question.Category category, int numberOfQuestions){
        addPlayer(user);
        this.category = category;
        this.numberOfQuestions = numberOfQuestions;
        this.questions = Set.getQuestions();
        this.matchID = random.nextInt(500) + random.nextInt(500);
        sendQuestion(numberOfAnswers);
    }
    public void addPlayer(User user){
        while (players.length <3) {
            if (players.length == 0) {
                this.player1 = user;
            } else {
                player2 = user;
            }
        }
    }

    public void setPoints(Score score) {
        int points = score.getPoints();
        for (User player : players) {
            if (player.getId().equals(score.getPlayer().getId())) {
                if (points == 0) {
                    player.addIncorrectAnswers();
                } else {
                    player.addCorrectAnswers(points);
                }
            }
        }
    }
    public Question sendQuestion(int numberOfAnswers){
        return questions[numberOfAnswers];
    }

    public void setWinner(){
        if(checkIfComplete()){
            if(pointsPlayer1 > pointsPlayer2){
                winner = player1;
            }
            else if (pointsPlayer1 < pointsPlayer2)
                winner = player2;
        }
        else {
            winner = null;
        }
    }
    public boolean checkIfComplete(){
        for (int score : matchPoints){
            if (score == 50){
                return false;
            }
        }
        completed = true;
        return true;
    }

    public Question[] getMatchQuestions() {
        return questions;
    }
    public int[]getMatchPoints(){
        return matchPoints;
    }
    public User getPlayer1(){
        return player1;
    }
    public User getPlayer2(){
        return player2;
    }
    public int getPointsPlayer1(){
        return pointsPlayer1;
    }
    public int getPointsPlayer2(){
        return pointsPlayer2;
    }
    public boolean ifCompleted(){
        return completed;
    }
    public User getWinner(){
        return winner;
    }
    public int getNumberOfAnswers(){
        return numberOfAnswers;
    }
    public int getMatchID(){
        return matchID;
    }
}

