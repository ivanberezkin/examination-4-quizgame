package GameComponents;
import Database.*;
import Quizgame.shared.User;
import java.io.Serializable;
import java.util.*;

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
    private List <Score> gameScores = new ArrayList<>();
    private List<Round> completedRounds = new ArrayList<>();
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
        if (completedRounds.size() < maxNumberOfRounds) {
            this.round = new Round(this, player, category, maxQuestionsRound, maxPlayers);
            categories.add(category.name());
        }
        else {
            if (checkIfCompleted()){
                GameManager.sendGameScore(gameScores, this);
            }
        }
    }

    public void addPlayer(User player) {
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
    public void joinRound(User player){
        for (User user : round.getPlayersList()){
            if (user.getUsername().equals(player.getUsername())){
                round.addPlayer(player);
                round.sendNextQuestion(player);
            }
        }
    }

    public void setUpNewRound(User player) {
        if (getPlayers() != null){
            for (User u : getPlayers()){
            }
        }
        boolean getsToChoose = false;
        if (completedRounds.size() < maxNumberOfRounds) {
            if (player.getUsername().equalsIgnoreCase(player1.getUsername()) && completedRounds.size() % 2 == 0) {
                getsToChoose = true;
            } else if (player.getUsername().equalsIgnoreCase(player2.getUsername()) && completedRounds.size() % 2 != 0) {
                getsToChoose = true;
            }
            if (getsToChoose) {
                GameManager.sendCategoryRequest(player);
            }   else if (!getsToChoose && round.getPlayersList().size() < 2) {
                round.addPlayer(player);
                round.sendNextQuestion(player);
            }
        }
    }

    public void setRoundScore(Round round) {
        List <int[]> roundScores = new ArrayList<>();
        if (round.completedRound()) {
            for (int i = 0; i < maxQuestionsRound; i++) {
                int [] score = new int[]{round.getPointsPlayer1().get(i), round.getPointsPlayer2().get(i)};
                roundScores.add(score);
            }
            gameScores.add(new Score(roundScores, player1, player2, round.getCategory()));
            //Listan gameScores innehåller objekt av klassen Scores, på der här sättet:
            //Score för runda1: [1, 0] [1, 1] [0, 0], User1, User2, Animals
            //Score för runda2: [1, 1] [0, 0] [0, 1], User1, User2, Geography
            //Score för runda3: [0, 0] [1, 1] [1, 0], User1, User2, Science
            // osv.
            //Man kan också från Score få fram poäng för spelare1 och spelare2:
            //Score för runda1: [1, 1, 0] [0, 1, 0]
            //Score för runda2: [1, 0, 0] [1, 0, 1]
            //Score för runda3: [0, 1, 1] [0, 1, 0]
            //osv

            if (checkIfCompleted()) {
                GameManager.sendGameScore(gameScores, this);
            } else {
                completedRounds.add(round);
                GameManager.sendRoundScore(gameScores);
            }
        }
    }

    public Question[] getQuestions(Question.Category category) {
        allGameQuestions = db.getQuestionsForRound(maxQuestionsRound, category);
        List<Question> questionsArrayList = new ArrayList<>(allGameQuestions);
        return questionsArrayList.toArray(new Question[0]);
    }

    public boolean checkIfCompleted(){
        return gameScores.size() == maxNumberOfRounds - 1;
    }
//    public void findGameWinner(){
//        int player1Total = 0;
//        int player2Total = 0;
//        if (checkIfCompleted()){
//            for (int [] score : roundScores){
//                player1Total += score[0];
//                player2Total += score[1];
//            }
//            if (player1Total > player2Total){
//                gameWinner = player1;
//            }
//            else if (player1Total < player2Total){
//                gameWinner = player2;
//            }
//            else {
//                gameWinner = null;
//            }
//        }
//    }
    public List<User> getPlayers(){
        return players;
    }

    public List <Round> getCompletedRounds(){
        return completedRounds;
    }
    public int getNumberOfPlayers(){
        return numberOfPlayers;
    }
    public Round getRound(){
        return round;
    }
    public User getPlayer1(){
        return player1;
    }
    public User getPlayer2(){
        return player2;
    }
    public List<Score> getGameScores(){
        return gameScores;
    }
    public List <String> getCategories(){
        return categories;
    }
}
