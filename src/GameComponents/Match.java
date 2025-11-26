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
    private final int maxPlayers;
    private User[] players;
    private Database.Question.Category category;
    private final List<Integer> pointsPlayer1 = new ArrayList<>();
    private final List<Integer> pointsPlayer2 = new ArrayList<>();
    private Question[] questions;
    private int numberOfCompletedQuestion = 0;
    private int numberOfSentQuestions = 0;
    private User winner;
    private Question firstQuestion;

    public Match(User player, Database.Question.Category category, int numberOfQuestions, int maxPlayers) {

        this.category = category;
        this.numberOfQuestions = numberOfQuestions;
        this.maxPlayers = maxPlayers;
        this.matchID = random.nextInt(500) + random.nextInt(500);
        players = new User[maxPlayers];
        addPlayer(player);
        loadMatchQuestions();
    }

    public void addPlayer(User user) {
            if (players[0] == null) {
                players[0] = user;
            } else if (players[1] == null){
                players[1] = user;
        }
        sendFirstQuestion(user);
    }

    private int getPlayerIndex(Score score) {
        for (int i = 0; i < players.length; i++) {
            if (players[i].getUsername().equals(score.getPlayer().getUsername())) {
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

    public void sendFirstQuestion(User player) {
        //Adjust method in DataBase, to get specific category?
        if (players[0] != null && pointsPlayer1.isEmpty()){
            Game.sendFirstQuestion(questions[0], player);
        }
        else if (!pointsPlayer1.isEmpty() && players[1] != null && pointsPlayer2.isEmpty()) {
            Game.sendFirstQuestion(questions[0], player);
        }
    }
    public void sendQuestion() {
        if((pointsPlayer1.size() == pointsPlayer2.size()) && !completedMatch()){
            Game.sendQuestion(questions[numberOfSentQuestions], players);
            numberOfSentQuestions += 1;
        }
    }

    public void setWinner() {
        if (completedMatch()) {
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

    public boolean completedMatch() {
        return pointsPlayer1.size() == numberOfQuestions  && pointsPlayer2.size() == numberOfQuestions;
    }

    public void loadMatchQuestions() {
        this.questions = Set.getQuestions();
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


