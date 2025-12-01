package GameComponents;

import Database.*;
import Quizgame.shared.Answer;
import Quizgame.shared.Message;
import Quizgame.shared.MessageType;
import Quizgame.shared.User;
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
    public void startGame(User player, Question.Category category){
        System.out.println("startGame in GameManager was reached");
        this.category = category;
        if (!activeGames.isEmpty()) {
            for (Game g : activeGames) {
                if (g.getNumberOfPlayers() == 1) {
                    g.addPlayer(player, category);
                }
            }
        }
        else {
            createNewGame(player, category);
        }
    }

    private void createNewGame(User player, Question.Category category) {
        Game game = new Game(player, category, maxPlayers, maxNumberOfQuestions, maxNumberOfRounds);
        activeGames.add(game);
    }
    public static void registerAnswer(Answer answer) {
        User user = answer.getUser();
        Game game = findActiveGame(user);
        if (game != null) {
            Round r = game.getRounds().getLast();
            if (!r.completedRound()) {
                r.addPointsToList(answer);
                r.sendNextQuestion(user);
            }
        }
    }
    public void startNextRound(User player, Question.Category category){
        Game game = findActiveGame(player);
        if (game != null){
            game.continuePlaying(player, category);
        }
    }
    private static Game findActiveGame(User user){
        for (Game g : getActiveGames()) {
            for (Round r : g.getRounds()) {
                List<User> players = r.getPlayersList();
                for (User u : players) {
                    if (u.getUsername().equals(user.getUsername())) {
                        return g;
                    }
                }
            }
        }
        return null;
    }

    public static void sendQuestions(List<User> users, Question question) {
        MatchQuestion matchQuestion = new MatchQuestion(users, question);
        ServerProtocol.processInput(new Message(MessageType.QUESTION, matchQuestion));
    }
    public static void sendRoundScore(Round round){
        ServerProtocol.processInput(new Message(MessageType.RESULT_ROUND, round));
    }
    public static void sendGameScore(Game game){
        ServerProtocol.processInput(new Message(MessageType.GAME_FINISHED, game));
        removeCompletedGame(game);
    }

    private static void removeCompletedGame(Game game){
        activeGames.remove(game);
    }
    private static List<Game>getActiveGames(){
        return activeGames;

    }
}
