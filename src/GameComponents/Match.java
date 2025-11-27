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
    private Connections[] players;
    private Connections player1;
    private Connections player2;
    private Question.Category category;
    private List<Connections>playerList = new ArrayList<>();
    private List<User> playersList = new ArrayList<>();
    private final List<Integer> pointsPlayer1 = new ArrayList<>();
    private final List<Integer> pointsPlayer2 = new ArrayList<>();
    private Question[] questions;
    private int numberOfCompletedQuestion = 0;
    private int numberOfSentQuestions = 0;
    private Connections winner;
    private final Question firstQuestion;
    private Set set;

    public Match(Set set, Connections player, Question.Category category, int numberOfQuestions, int maxPlayers) {
        this.set = set;
        this.category = category;
        this.numberOfQuestions = numberOfQuestions;
        this.maxPlayers = maxPlayers;
        this.matchID = random.nextInt(500) + random.nextInt(500);
        questions = set.getQuestions();
        firstQuestion = questions[0];
        players = new Connections[maxPlayers];
        addPlayer(player);
        loadMatchQuestions();
    }

    public void addPlayer(Connections player) {
        System.out.println("addPlayer in Match was reached");
        if (player != null) {
            if (players[0] == null) {
                player1 = player;
                players[0] = player1;
            } else if (players[1] == null) {
                player2 = player;
                players[1] = player2;
            }
            playersList.add(player.getUser());
            sendFirstQuestion();
        }

    }

    private int getPlayerIndex(Score score) {
        for (int i = 0; i < players.length; i++) {
            if (players[i].getUser().getUsername().equals(score.getPlayer().getUsername())) {
                return i;
            }
        }
        return -1;
    }

    public void addPointsToList(Answer answer) {
        System.out.println("addPointsToList in Match was reached");
        int score = 0;
        if (answer.getIsAnswerCorrect()){
            score = 1;
        }
        if (answer.getUser() == player1.getUser()){
            pointsPlayer1.add(score);
        }
        else if (answer.getUser() == player2.getUser()) {
            pointsPlayer2.add(score);
        }
        numberOfCompletedQuestion += 1;
    }

    public void sendFirstQuestion() {
            //Adjust method in DataBase, to get specific category?
            if (playersList != null && pointsPlayer1.isEmpty()) {
                Game.sendFirstQuestion(player1, firstQuestion);
                System.out.println(" . . . For Match sendFirstQuestion, player is player1");

            } else if (!pointsPlayer1.isEmpty() && playersList.size() == 2 && pointsPlayer2.isEmpty()) {
                Game.sendFirstQuestion(player2, firstQuestion);
                numberOfSentQuestions += 1;
                    System.out.println(" . . . For Match sendFirstQuestion, player is: player2 ");
            } else if (playerList.size() == 2 && pointsPlayer1.isEmpty() && pointsPlayer2.isEmpty()) {
                sendQuestion();
            }
    }
    public void sendQuestion() {
        System.out.println("sendQuestion in Match was reached");
        if((pointsPlayer1.size() == pointsPlayer2.size()) && !completedMatch()){
            Game.sendQuestion(playerList, questions[numberOfSentQuestions]);
            numberOfSentQuestions += 1;
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

    public Connections getWinner() {
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


