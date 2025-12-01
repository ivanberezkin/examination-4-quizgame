package GameComponents;
import Database.*;
import Quizgame.shared.User;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

public class Game implements Serializable {
    int maxPlayers;
    int numberOfPlayers;
    private final int maxQuestionsRound;
    private final int maxQuestionsGame;
    private final int maxNumberOfRounds;
    private List<User>players = new ArrayList<>();
    private User player1;
    private User player2;
    private int gameScorePlayer1 = 0;
    private int gameScorePlayer2 = 0;
    private User gameWinner;
    private List<int[]> gameScores = new ArrayList<>();
    private List<Round> rounds = new ArrayList<>();
    private Round round;
    private Set<Question> allGameQuestions = new LinkedHashSet<>();
    static Database db = new Database();
    private List <String> categories = new ArrayList<>();

    public Game(User player, Question.Category category, int maxPlayers, int maxQuestionsRound, int maxNumberOfGames) {
        this.maxPlayers = maxPlayers;
        this.maxNumberOfRounds = maxNumberOfGames;
        this.maxQuestionsRound = maxQuestionsRound;
        this.maxQuestionsGame = maxQuestionsRound* maxNumberOfGames;
        this.player1 = player;
        players.add(player);
        this.numberOfPlayers = players.size();
        startRound(player, category);
    }

    public void startRound(User player, Question.Category category) {
        if (rounds.size() < maxNumberOfRounds) {
            round = new Round(this, player, category, maxQuestionsRound, maxPlayers);
            rounds.add(round);
            categories.add(category.name());
        }
        else {
            if (checkIfCompleted()){
                GameManager.sendGameScore(this);
            }
        }
    }

    public void addPlayer(User player, Question.Category category) {
        if (numberOfPlayers == 0){
            this.player1 = player;
            players.add(player1);
            numberOfPlayers += 1;
            round.addPlayer(player1);
        }
        else if (numberOfPlayers == 1) {
            this.player2 = player;
            players.add(player2);
            numberOfPlayers += 1;
            round.addPlayer(player2);
        }
    }

    public void continuePlaying(User player, Question.Category category) {
        if (rounds.size() < maxNumberOfRounds) {
            int numberOfPlayersInRound = rounds.getLast().getPlayersList().size();
            if (numberOfPlayersInRound == 0){
                startRound(player, category);
            }
            else if (numberOfPlayersInRound == 1)
                round.addPlayer(player);
        }
    }

    public void setRoundScore(Round round) {
        if (round.completedRound()) {
            for (int i = 0; i < maxQuestionsRound; i++) {
                gameScores.add(new int[]{round.getPointsPlayer1().get(i), round.getPointsPlayer2().get(i)});
            }
            if (checkIfCompleted()) {
                GameManager.sendGameScore(this);
            } else {
                GameManager.sendRoundScore(this);
            }
        }
    }

    public Question[] getQuestions(Question.Category category) {
        System.out.println("in Game, getQuestions is reached");
        allGameQuestions = db.getQuestionsForRound(maxQuestionsRound, category);
        List<Question> questionsArrayList = new ArrayList<>(allGameQuestions);
        return questionsArrayList.toArray(new Question[0]);
    }

    public boolean checkIfCompleted(){
        System.out.println("in Game, gameScores.size() is " + gameScores.size());
        return gameScores.size() == maxNumberOfRounds - 1;
    }
    public void findGameWinner(){
        int player1Total = 0;
        int player2Total = 0;
        if (checkIfCompleted()){
            for (int [] score : gameScores){
                player1Total += score[0];
                player2Total += score[1];
            }
            if (player1Total > player2Total){
                gameWinner = player1;
            }
            else if (player1Total < player2Total){
                gameWinner = player2;
            }
            else {
                gameWinner = null;
            }
        }
    }
    public List<User> getPlayers(){
        return players;
    }

    public List <Round> getRounds(){
        return rounds;
    }
    public int getNumberOfPlayers(){
        return numberOfPlayers;
    }
    public User getPlayer1(){
        return player1;
    }
    public User getPlayer2(){
        return player2;
    }
    public List<int[]> getGameScores(){
        return gameScores;
    }
    public List <String> getCategories(){
        return categories;
    }
}
