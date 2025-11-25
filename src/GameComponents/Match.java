package GameComponents;

import Database.*;
import Quizgame.shared.User;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Match {
    private int matchID;
    private int numberOfQuestions;
    private int numberOfPlayers;
    private User[] players;
    private Database.Question.Category category;
    private int pointsPlayer1 = 0;
    private int pointsPlayer2 = 0;
    private List<int[]> matchPoints = new ArrayList<>();
    private Question[] questions;
    private int numberOfCompletedQuestion = 0;
    private int numberOfSentQuestions = 0;
    Random random = new Random();

    private User winner;
    private boolean completed = false;

    public Match(User player, Database.Question.Category category, int numberOfQuestions) {
        matchPoints.add(new int[]{50, 50});
        players = new User[2];
        addPlayer(player);
        this.category = category;
        this.numberOfQuestions = numberOfQuestions;
        this.questions = Set.getQuestions();
        this.matchID = random.nextInt(500) + random.nextInt(500);
        sendQuestion();
        numberOfSentQuestions+=1;
    }

    public void addPlayer(User user) {
        while (players.length < 3) {
            if (players.length == 0) {
                players[0] = user;
            } else {
                players[1] = user;
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
                if (matchPoints.get(numberOfSentQuestions-1)[0]==50) {
                    matchPoints.get(numberOfSentQuestions-1)[0] = score.getPoints();
                }
                else if (matchPoints.get(numberOfSentQuestions-1)[1]==50) {
                    matchPoints.get(numberOfSentQuestions)[1] = score.getPoints();
                numberOfCompletedQuestion +=1; }
                Set.getMatchScore(score, numberOfCompletedQuestion);
                Game.sendScore(score, players);
            }
        }
    }

    public void sendQuestion () {
        Game.sendQuestion(questions[numberOfSentQuestions], players);
        numberOfSentQuestions+=1;
    }

    public void setWinner() {
        if (checkIfComplete()) {
            if (pointsPlayer1 > pointsPlayer2) {
                winner = players[0];
            } else if (pointsPlayer1 < pointsPlayer2)
                winner = players[1];
        } else {
            winner = null;
        }
        Game.sendMatchScore(players, matchID);
    }

    public boolean checkIfComplete() {
        for (int [] points : matchPoints) {
            if (points [0] == 50 || points[1] == 50) {
                return false;
            }
        }
        completed = true;
        return true;
    }

    public Question[] getMatchQuestions() {
        return questions;
    }

    public int getPointsPlayer1() {
        return pointsPlayer1;
    }

    public int getPointsPlayer2() {
        return pointsPlayer2;
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
    public List<int[]> getMatchPoints(){
        return matchPoints;
    }

}

