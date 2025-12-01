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

    public Round(Game game, User user, Question.Category category, int numberOfQuestions, int maxPlayers) {
        this.game = game;
        this.numberOfQuestions = numberOfQuestions;
        this.RoundID = random.nextInt(500) + random.nextInt(500);
        roundQuestions = game.getQuestions(category);
        this.category = category;
        players = new User[maxPlayers];
        roundQuestions = game.getQuestions(category);
        addPlayer(user);
    }

    public void addPlayer(User player) {
        if (player != null) {
            if (players[0] == null) {
                player1 = player;
                players[0] = player1;
                playersList.add(player1);
                sendNextQuestion(player);
            } else if (players[1] == null) {
                System.out.println(". . . . . in Round, addPlayer[1] was reached");
                player2 = player;
                players[1] = player2;
                playersList.add(player2);
                sendNextQuestion(player);
            }
        }
    }

    private int getPlayerIndex(Score score) {
        for (int i = 0; i < players.length; i++) {
            if (players[i].getUsername().equals(score.getPlayer().getUsername())) {
                return i;
            }
        }
        return -1;
    }

    public void addPointsToList(Answer answer) {
        System.out.println("_ _ _ addPointsToLIst in Round was reached");
        int score = 0;
        if (answer.getIsAnswerCorrect()){
            score = 1;
        }
        if (answer.getUser().getUsername().equals(player1.getUsername())){
            pointsPlayer1.add(score);
        }
        else if (answer.getUser().getUsername().equals(player2.getUsername())) {
            pointsPlayer2.add(score);
        }
    }
    public void sendNextQuestion(User player) {
        if (!completedRound()) {
            int index = 0;
            List<User> singleUserToList = new ArrayList<>();
            if (player.getUsername().equals(player1.getUsername()) && pointsPlayer1.size() < numberOfQuestions) {
                index = pointsPlayer1.size();
                singleUserToList.add(player1);
            } else if (player.getUsername().equals(player2.getUsername()) && pointsPlayer2.size() < numberOfQuestions) {
                index = pointsPlayer2.size();
                singleUserToList.add(player2);
            }
            Question question = roundQuestions[index];
            GameManager.sendQuestions(singleUserToList, question);
        }
    }

    public void findRoundWinner() {
        if (completedRound()) {
            int points1 = pointsPlayer1.get(numberOfQuestions - 1);
            int points2 = pointsPlayer2.get(numberOfQuestions - 1);
            if (points1 > points2) {
                roundWinner = player1;
            } else if (points1 < points2)
                roundWinner = player2;
        } else {
            roundWinner = null;
        }
        GameManager.sendRoundScore(this);
    }
    public List<User> getPlayersList(){
        return playersList;
    }

    public boolean completedRound() {
        if(pointsPlayer1.size() == numberOfQuestions  && pointsPlayer2.size() == numberOfQuestions) {
            return true;
        }
        else{
            return false;
        }
    }

    public void loadRoundQuestions() {
        this.questions = game.getQuestions(category);
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


