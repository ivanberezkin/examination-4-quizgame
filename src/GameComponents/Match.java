package GameComponents;

import Database.*;
import Quizgame.shared.User;

public class Match {
    Database db = new Database();
    private User player1;
    private User player2;
    private Database.Question.Category category;
    private int pointsPlayer1 = 0;
    private int pointsPlayer2 = 0;
    private int[]matchPoints = new int []{50, 50};
    private int numberOfQuestions = 3;
    Question[]questions = new Question[numberOfQuestions];
    private User winner;
    private boolean completed = false;

    public Match(User[] users, Database.Question.Category category){
        this.player1 = users[0];
        this.player2 = users[1];
        this.category = category;
    }
    private void setUpQuestions(){
        for (int i = 0; i<= numberOfQuestions; i++) {
            Question question = db.getNewQuestion();
            if (!checkQuestion(question)) {
                questions[i] = question;
            }
        }
    }
    public Question[] sendQuestions(){
        setUpQuestions();
        return questions;
    }
    private boolean checkQuestion(Question question){
        for (Question q : questions){
            if (q.getPrompt().equals(question.getPrompt())){
                return true;
            }
        }
        return false;
    }
    public void setPoints(int score) {
        if (matchPoints[0] == 50) {
            pointsPlayer1 = score;
            if (score == 0) {
                player1.addIncorrectAnswers();
            } else {
                player1.addCorrectAnswers(score);
            }
        }
        else {
            pointsPlayer2 = score;
            if (score == 0) {
                player2.addCorrectAnswers(score);
            }
            else player2.addIncorrectAnswers();
        }
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
}

