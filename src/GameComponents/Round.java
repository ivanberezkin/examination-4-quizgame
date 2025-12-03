package GameComponents;

import Database.*;
import Quizgame.shared.Answer;
import Quizgame.shared.User;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Round implements Serializable {
    Random random = new Random();
    private final int RoundID;
    private final int numberOfQuestions;
    private User player1;
    private User player2;
    private Question.Category category;
    private List<User> playersList = new ArrayList<>();
    private final List<Integer> pointsPlayer1 = new ArrayList<>();
    private final List<Integer> pointsPlayer2 = new ArrayList<>();
    private Question[] questions;
    private User [] players;
    private int numberOfCompletedQuestion = 0;
    private int numberOfSentQuestions = 0;
    private User roundWinner;
    private Question [] roundQuestions;
    private Game game;
    boolean player1IsWaiting = false;
    boolean player2IsWaiting = false;

    public Round(Game game, User user, Question.Category category, int numberOfQuestions, int maxPlayers) {
        System.out.println("Round constructor was reached");
        this.game = game;
        this.numberOfQuestions = numberOfQuestions;
        this.RoundID = random.nextInt(500) + random.nextInt(500);
        roundQuestions = game.getQuestions(category);
        this.category = category;
        players = new User[maxPlayers];
        addPlayer(user);
        sendNextQuestion(user);
    }

    public void addPlayer(User player) {
        if (player != null) {
            if (players[0] == null) {
                player1 = player;
                players[0] = player1;
                playersList.add(player1);
            } else if (players[1] == null) {
                player2 = player;
                players[1] = player2;
                playersList.add(player2);
            }
        }
    }

    public void addPointsToList(Answer answer) {

        int score = 0;
        if (answer.getIsAnswerCorrect()){
            score = 1;
        }
        System.out.println("In Round, addPointsToList, point is: " + score + "Answer is: " + answer.getIsAnswerCorrect());
        if (answer.getUser().getUsername().equals(player1.getUsername())){
            pointsPlayer1.add(score);
            if (pointsPlayer1.size() == pointsPlayer2.size()){
                numberOfCompletedQuestion = pointsPlayer1.size();
            }
        }
        else if (answer.getUser().getUsername().equals(player2.getUsername())) {
            pointsPlayer2.add(score);
            if (pointsPlayer1.size() == pointsPlayer2.size()){
                numberOfCompletedQuestion = pointsPlayer1.size();
            }
        }
        if (pointsPlayer1.size() == numberOfQuestions && pointsPlayer2.size() == numberOfQuestions){
            game.setRoundScore(this);
        }

        else sendNextQuestion(answer.getUser());
    }
    public void sendNextQuestion(User player) {
        if (!completedRound() && !isUserWaiting(player)) {
            int index = 0;
            List<User> singleUserToList = new ArrayList<>();
            if (player.getUsername().equals(player1.getUsername()) && pointsPlayer1.size() < numberOfQuestions) {
                index = pointsPlayer1.size();
                singleUserToList.add(player1);
            } else if (player2 != null && player.getUsername().equals(player2.getUsername()) && pointsPlayer2.size() < numberOfQuestions) {
                index = pointsPlayer2.size();
                singleUserToList.add(player2);
            }
            Question question = roundQuestions[index];
            GameManager.sendQuestions(singleUserToList, question);
        }
    }
    private boolean isUserWaiting(User player){
        if (player1.getUsername().equals(player.getUsername()) && pointsPlayer1.size() == numberOfQuestions){
            GameManager.sendWaitingMessage(player);
            player1IsWaiting = true;
            return true;
        }
        else if (player2 != null && player2.getUsername().equals(player.getUsername()) && pointsPlayer2.size() == numberOfQuestions){
            GameManager.sendWaitingMessage(player);
            player2IsWaiting = true;
            return true;
        }
        else {
            return false;
        }
    }

    public void findRoundWinner() {
            int points1 = pointsPlayer1.get(numberOfQuestions - 1);
            int points2 = pointsPlayer2.get(numberOfQuestions - 1);
            if (points1 > points2) {
                roundWinner = player1;
            } else if (points1 < points2) {
                roundWinner = player2;
        } else {
            roundWinner = null;
        }
    }
    public List<User> getPlayersList(){
        return playersList;
    }

    public boolean completedRound() {
        if(pointsPlayer1.size() == numberOfQuestions  && pointsPlayer2.size() == numberOfQuestions) {
            findRoundWinner();
            return true;
        }
        else{
            return false;
        }
    }

    public User getRoundWinner() {
        return roundWinner;
    }

    public int getNumberOfCompletedQuestions() {
        return numberOfCompletedQuestion;
    }

    public int getRoundID() {
        return RoundID;
    }

    public List<Integer> getPointsPlayer1(){
        return pointsPlayer1;
    }
    public List<Integer>getPointsPlayer2(){
        return pointsPlayer2;
    }
    public String getCategory(){
        return category.name();
    }
}


