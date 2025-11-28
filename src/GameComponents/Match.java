package GameComponents;
import Database.*;
import Quizgame.shared.Answer;
import Quizgame.shared.User;
import Server.Connections;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class Match {
    Random random = new Random();
    private final int matchID;
    private final int numberOfQuestions;
    private final int maxPlayers;
    private User player1;
    private User player2;
    private Question.Category category;
    private List<Connections>playerList = new ArrayList<>();
    private List<User> playersList = new ArrayList<>();
    private final List<Integer> pointsPlayer1 = new ArrayList<>();
    private final List<Integer> pointsPlayer2 = new ArrayList<>();
    private Question[] questions;
    private User [] players;
    private int numberOfCompletedQuestion = 0;
    private int numberOfSentQuestions = 0;
    private User winner;
    private final Question firstQuestion;
    private Set set;

    public Match(Set set, User user, Question.Category category, int numberOfQuestions, int maxPlayers) {
        this.set = set;
        this.category = category;
        this.numberOfQuestions = numberOfQuestions;
        this.maxPlayers = maxPlayers;
        this.matchID = random.nextInt(500) + random.nextInt(500);
        questions = set.getQuestions();
        firstQuestion = questions[0];
        players = new User[maxPlayers];
        addPlayer(user);
        loadMatchQuestions();
    }

    public void addPlayer(User player) {
        System.out.println("In match, addPlayer was reached");
        if (player != null) {
            if (players[0] == null) {
                player1 = player;
                players[0] = player1;
            } else if (players[1] == null) {
                player2 = player;
                players[1] = player2;
            }
            playersList.add(player);
            sendFirstQuestion();
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
        int score = 0;
        if (answer.getIsAnswerCorrect()){
            score = 1;
        }
        if (answer.getUser() == player1){
            pointsPlayer1.add(score);
        }
        else if (answer.getUser() == player2) {
            pointsPlayer2.add(score);
        }
        numberOfCompletedQuestion += 1;
    }

    public void sendFirstQuestion() {
            //Adjust method in DataBase, to get specific category?
            if (playersList.size() ==1 && pointsPlayer1.isEmpty()) {
                Game.sendQuestion(playersList, firstQuestion);
                System.out.println(" . . . For Match sendFirstQuestion, player is player1");

            } else if (!pointsPlayer1.isEmpty() && playersList.size() == 2 && pointsPlayer2.isEmpty()) {
                Game.sendQuestion(playersList, firstQuestion);
                numberOfSentQuestions += 1;
                    System.out.println(" . . . For Match sendFirstQuestion, player is: player2 ");
            } else if (playerList.size() == 2 && pointsPlayer1.isEmpty() && pointsPlayer2.isEmpty()) {
                sendQuestion();
            }
    }
    public void sendQuestion() {
        System.out.println("       sendQuestion in Match was reached. question is: "+ numberOfSentQuestions);
        for (User u : playersList){
            System.out.println("in sendQuestion, player is: " + u.getUsername());
        }
        if(pointsPlayer1.size() == pointsPlayer2.size() && !completedMatch()){
            Game.sendQuestion(playersList, questions[numberOfSentQuestions]);
            if (pointsPlayer1.size() == numberOfSentQuestions && pointsPlayer2.size() == numberOfSentQuestions) {
                numberOfSentQuestions += 1;
            }
        }
        else if (completedMatch()){
            Game.sendMatchScore(this);
        }
    }

    public void setWinner() {
        if (completedMatch()) {
            int points1 = pointsPlayer1.get(numberOfQuestions - 1);
            int points2 = pointsPlayer2.get(numberOfQuestions - 1);
            if (points1 > points2) {
                winner = player1;
            } else if (points1 < points2)
                winner = player2;
        } else {
            winner = null;
        }
        Game.sendMatchScore(this);
    }
    public List<User> getPlayersList(){
        return playersList;
    }
    public List<Connections> getPlayerList(){
        return playerList;
    }

    public boolean completedMatch() {
        return pointsPlayer1.size() == numberOfQuestions  && pointsPlayer2.size() == numberOfQuestions;
    }

    public void loadMatchQuestions() {
        this.questions = set.getQuestions();
    }

    public User getWinner() {
        return winner;
    }

    public int getNumberOfCompletedQuestion() {
        return numberOfCompletedQuestion;
    }

    public int getMatchID() {
        return matchID;
    }

    public List<Integer> getPointsPlayer1(){
       return pointsPlayer1;
    }
    public List<Integer>getPointsPlayer2(){
        return pointsPlayer2;
    }
}


