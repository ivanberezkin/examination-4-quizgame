package GameComponents;

import Database.*;
import Quizgame.shared.Answer;
import Quizgame.shared.Message;
import Quizgame.shared.MessageType;
import Quizgame.shared.User;
import Server.Connections;
import Server.ServerListener;
import Server.ServerProtocol;
import Server.SettingsLoader;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class GameManager implements Serializable {
    private int maxNumberOfQuestions = SettingsLoader.getQuestionsPerRound();
    private int maxNumberOfRounds = SettingsLoader.getRoundsPerGame();
    private int maxPlayers = SettingsLoader.getMaxPlayers();

    private Question.Category category;
    private static List<Game> activeGames = new ArrayList<>();

    public GameManager(){

    }
    public Game checkAvailableGames(User player){
        System.out.println("in GameManager, maximum numberOfRounds is: " + maxNumberOfRounds);
        if (!activeGames.isEmpty()) {
            for (Game g : activeGames) {
                if (g.getNumberOfPlayers() == 1) {
                    g.addPlayer(player);
                }
                return g;
            }
        }
        return null;
    }

    //Här startas ett nytt spel och den kollar ifall det är en eller två spelare.
    public void startGame(User player, Question.Category category) {
        boolean newGame = false;
        if (findActiveGame(player) == null) {
            newGame = true;
        }
            this.category = category;
            if (newGame) {
                if (!activeGames.isEmpty()) {
                    for (Game g : activeGames) {
                        if (g.getNumberOfPlayers() == 1) {
                            g.addPlayer(player);
                        }
                    }
                } else {
                    createNewGame(player, category);
                }
            }
        else {
            startNextRound(player, category);
        }
    }

    private void createNewGame(User player, Question.Category category) {
        Game game = new Game(player, category, maxPlayers, maxNumberOfQuestions, maxNumberOfRounds);
        activeGames.add(game);
    }
    public void joinStartedGame(User player){
        Game game = findActiveGame(player);
        if (game != null){
            game.joinRound(player);
        }
    }
    public static void registerAnswer(Answer answer) {
        User user = answer.getUser();
        Game game = findActiveGame(user);
        if (game != null) {
            Round r = game.getRound();
            if (r != null && !r.completedRound()) {
                r.addPointsToList(answer);
            }
        }
    }
    public void setUpNextRound(User player){
        Game game = findActiveGame(player);
        if (game != null){
            game.setUpNewRound(player);
        }
    }
    public void startNextRound(User player, Question.Category category){
        Game game = findActiveGame(player);
        if (game != null){
            game.startRound(player, category);
        }
    }
    public static void sendCategoryRequest(User player){
        ServerProtocol.processInput(new Message(MessageType.CATEGORY_REQUEST, player));
    }

    private static Game findActiveGame(User user) {
        for (Game g : getActiveGames()) {
            for (User player : g.getPlayers()) {
                if (player.getUsername().equals(user.getUsername())) {
                    return g;
                }
            }
        }
        return null;
    }

    public static void sendQuestions(List<User> users, Question question) {
        MatchQuestion matchQuestion = new MatchQuestion(users, question);
        ServerProtocol.processInput(new Message(MessageType.QUESTION, matchQuestion));
    }
    public static void sendRoundScore(List<Score> roundScores){
        System.out.println("in GameManager, roundScores.size is: " + roundScores.size());
        ServerProtocol.processInput(new Message(MessageType.RESULT_ROUND, roundScores));
    }
    public static void sendGameScore(List<Score> roundScores, Game game){
        ServerProtocol.processInput(new Message(MessageType.GAME_FINISHED, roundScores));
        removeCompletedGame(game);
    }
    public static void sendWaitingMessage(User user){
        ServerProtocol.processInput(new Message(MessageType.WAITING, user));
        Connections conn = ServerListener.findConnectionsByUser(user.getUsername());
        conn.send(new Message(MessageType.WAITING,user));
    }

    private static void removeCompletedGame(Game game){
        activeGames.remove(game);
    }

//    private static void checkGames(){
//        for (Game g : activeGames){
//            if(g.checkIfCompleted()){
//                removeCompletedGame(g);
//            }
//        }
//    }

    private static List<Game>getActiveGames(){
        return activeGames;
    }
}
