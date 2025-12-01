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
    private int maxNumberOfMatches = SettingsLoader.getRoundsPerGame();
    private int maxPlayers = SettingsLoader.getMaxPlayers();

    private Question.Category category;
    private static List<Game> activeGames = new ArrayList<>();

    public GameManager(){

    }
    public void startGame(User player, Question.Category category) {
        this.category = category;
        if (!activeGames.isEmpty()) {
            for (Game s : activeGames) {
                if (s.getNumberOfPlayers() == 1) {
                    s.addPlayer(player);
                }
            }
        }
        else {
            startNewSet(player);
        }
    }

    private void startNewSet(User player) {
        Game game = new Game(player, category, maxPlayers, maxNumberOfQuestions, maxNumberOfMatches);
        activeGames.add(game);
    }
    public static void continueGame(Answer answer){
        for (Game s : getActiveSets()) {
            for (Rond m : s.getMatches()) {
                List<User> players = m.getPlayersList();
                for (User u : players) {
                    if (u.getUsername().equals(answer.getUser().getUsername())) {
                        m.addPointsToList(answer);
                        m.sendQuestion();
                    }
                }
            }
        }
    }

    public static void sendQuestion(List<User> users, Question question) {
        MatchQuestion matchQuestion = new MatchQuestion(users, question);
        ServerProtocol.processInput(new Message(MessageType.QUESTION, matchQuestion));
    }
    public static void sendMatchScore(Rond rond){
        ServerProtocol.processInput(new Message(MessageType.RESULT_ROUND, rond));
        checkSets();
    }
    private static void removeCompletedSet(Game game){
        activeGames.remove(game);
    }
    private static void checkSets(){
        for (Game s : activeGames){
            if(s.checkIfCompleted()){
                removeCompletedSet(s);
            }
        }
    }
    private static List<Game>getActiveSets(){
        return activeGames;

    }
}
