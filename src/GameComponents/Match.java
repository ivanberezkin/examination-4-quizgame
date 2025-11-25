package GameComponents;

import Database.*;
import Quizgame.shared.User;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Match {
    Random random = new Random();
    private final int matchID;
    private final int numberOfQuestions;
    private final int numberOfPlayers;
    private final User[] players;
    private Database.Question.Category category;
    private final List<Integer> pointsPlayer1 = new ArrayList<>();
    private final List<Integer> pointsPlayer2 = new ArrayList<>();
    private final Question[] questions;
    private int numberOfCompletedQuestion = 0;
    private int numberOfSentQuestions = 0;
    private User winner;
    private boolean completed = false;

    public Match(User player, Database.Question.Category category, int numberOfQuestions, int numberOfPlayers) {
        players = new User[2];
        addPlayer(player);
        this.category = category;
        this.numberOfQuestions = numberOfQuestions;
        this.questions = Set.getQuestions();
        this.numberOfPlayers = numberOfPlayers;
        this.matchID = random.nextInt(500) + random.nextInt(500);
        sendQuestion();
        numberOfSentQuestions += 1;
    }

    public void addPlayer(User user) {
        if (players.length < numberOfPlayers) {
            if (players.length == 0) {
                players[0] = user;
            } else {
                players[1] = user;
            }
        }
    }

    private int getPlayerIndex(Score score) {
        for (int i = 0; i <= players.length; i++) {
            if (players[i].getId().equals(score.getPlayer().getId())) {
                return i;
            }
        }
        return -1;
    }

    public void assignPoints(Score score) {
        int points = score.getPoints();
        int index = getPlayerIndex(score);
        User player = score.getPlayer();
        if (index != -1) {
            if (points == 0) {
                player.addIncorrectAnswers();
            } else {
                player.addCorrectAnswers(points);
            }
            addPointsToList(index, score);
            numberOfCompletedQuestion += 1;
            Set.getMatchScore();
            Game.sendScore(score, players);
        }
    }

    private void addPointsToList(int index, Score score) {
        if (index == 0) {
            pointsPlayer1.add(score.getPoints());
        } else if (index == 1) {
            pointsPlayer2.add(score.getPoints());
        }
    }

    public void sendQuestion() {
        //Adjust method in DataBase, to get specific category?

        Game.sendQuestion(questions[numberOfSentQuestions], players);
        numberOfSentQuestions += 1;
    }

    public void setWinner() {
        if (checkIfComplete()) {
            int points1 = pointsPlayer1.get(numberOfQuestions - 1);
            int points2 = pointsPlayer2.get(numberOfQuestions - 1);
            if (points1 > points2) {
                winner = players[0];
            } else if (points1 < points2)
                winner = players[1];
        } else {
            winner = null;
        }
        Game.sendMatchScore(players, matchID);
    }

    public boolean checkIfComplete() {
        if ((numberOfCompletedQuestion == numberOfQuestions) && (finalQuestion())) {
            completed = true;
                return true;
            }
        else {
            return false;
        }
    }

    public boolean finalQuestion() {
        return pointsPlayer2.size() == numberOfPlayers - 1;
    }

    public Question[] getMatchQuestions() {
        return questions;
    }

    public boolean ifCompleted() {
        return completed;
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

    public int getNumberOfPlayers() {
        return numberOfPlayers;
    }
    public List<Integer> getPointsPlayer1(){
       return pointsPlayer1;
    }
    public List<Integer>getPointsPlayer2(){
        return pointsPlayer2;
    }
}


