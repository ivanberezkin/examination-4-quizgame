package Server;

import Database.*;
import GameComponents.*;
import Quizgame.shared.*;

import java.util.ArrayList;
import java.util.List;

public class ServerProtocol {
    static GameManager gameManager = new GameManager();
    private static User user;
    private static Database db = new Database();
    private static AuthenticationDatabase ad = new AuthenticationDatabase();

    public static Message processInput(Message message) {
        System.out.println("SERVERPROTOCOL: processInput was reached, message type is:" + message.getType());

        if (message == null || message.getType() == null) {
            return new Message(MessageType.ERROR, "SERVERPROTOCOL: Invalid message");
        }
        MessageType type = message.getType();
        if (message.getData() instanceof User){
            user = (User) message.getData();
        }

        switch (type) {
            case LOGIN_REQUEST -> {
                User loginUser = (User) message.getData();
                User existingUser = ad.getUserByUsername(loginUser.getUsername());

                if (existingUser == null) {
                    return new Message(MessageType.LOGIN_USER_NOT_FOUND, null);
                }
                if (!existingUser.getPassword().equals(loginUser.getPassword())) {
                    return new Message(MessageType.LOGIN_WRONG_PASSWORD, loginUser);
                }
                return new Message(MessageType.LOGIN_OK, existingUser);
            }

            case LOGIN_CREATE_REQUEST -> {
                User newUser = (User) message.getData();
                if (ad.userExists(newUser.getUsername())) {
                    return new Message(MessageType.LOGIN_CREATE_FAIL, null);
                }
                ad.createUser(newUser.getUsername(), newUser.getPassword());
                return new Message(MessageType.LOGIN_CREATE_OK, newUser);
            }
            case QUESTION -> {
                if (message.getData() instanceof MatchQuestion matchQuestion) {
                    for (User u : matchQuestion.getUsers()) {
                        Connections c = ServerListener.findConnectionsByUser(u.getUsername());
                        c.send(new Message(MessageType.QUESTION, matchQuestion.getQuestion()));
                    }
                    return null;
                }

            }
            case START_NEXT_ROUND -> {
                if (message.getData() instanceof User player) {
                    gameManager.setUpNextRound(player);


                }
            }

            case SETTINGS_AVATAR_CHANGED -> {
                ad.updateAvatarForUser(user);
            }

            case GAME_START -> {

            }

            case GIVE_UP -> {
                IO.println("CLIENTPROTOCOL: " + "Received " + message.getType() + " from " + user.getUsername());
                return new Message(MessageType.MOVE_TO_MENU, null);
                //TODO lägga till logik för vad som händer när användare ger upp.
            }

            case CHOOSING_CATEGORIES -> {
                if (message.getData() instanceof Quizgame.shared.UserAndCategory startingParameters) {
                    Question.Category category = startingParameters.getCategory();
                    User user = startingParameters.getUser();
                    Game game = gameManager.checkAvailableGames(user);


                    User opponent;
                    if(game != null && game.getPlayer1() != null && game.getPlayer2() != null){
                        if(user.getUsername().equalsIgnoreCase(game.getPlayer1().getUsername())){
                            opponent = game.getPlayer2();
                        }else{
                            opponent = game.getPlayer1();
                        }
                        gameManager.setUpNextRound(opponent);
                    }


//                    Connections c = ServerListener.findConnectionsByUser(user.getUsername());
//                    c.send(new Message(MessageType.GAME_START, category));
                    gameManager.startGame(user, category);

                }
                return null;
            }


            //Matchmaking från
            case MATCHMAKING -> {
                System.out.println("SERVERPROTOCOL: MATCHMAKING was reached");
                if (message.getData() != null) {
                    User player = (User) message.getData();
                    Game game = gameManager.checkAvailableGames(player);
                    Connections c = ServerListener.findConnectionsByUser(player.getUsername());
                    if (game != null) {
                        c.send(new Message(MessageType.ADDED_TO_GAME, null));
                        gameManager.joinStartedGame(player);

                    }
                    else {
                        c.send(new Message(MessageType.CATEGORY_REQUEST, player));
                    }
                }
                return null;
            }
            case CATEGORY_REQUEST -> {
                User player = (User) message.getData();
                Connections c = ServerListener.findConnectionsByUser(player.getUsername());
                c.send(new Message(MessageType.CATEGORY_REQUEST, player));
            }

            case ANSWER -> {
                if (message.getData() instanceof Answer) {
                    Answer answer = (Answer) message.getData();
                    GameManager.registerAnswer(answer);
                    return null;
                }
            }
            case WAITING -> {
                if (message.getData()instanceof Round round){
                    if (!round.getPlayersList().isEmpty())
                        if (round.getPointsPlayer1().size() > round.getPointsPlayer2().size()){
                            user = round.getPlayersList().getFirst();
                        }
                        else if (round.getPointsPlayer1().size() < round.getPointsPlayer2().size()){
                            user = round.getPlayersList().get(1);
                        }
                    Connections c = ServerListener.findConnectionsByUser(user.getUsername());
                        c.send(new Message(MessageType.WAITING, round));
                }
            }
            case RESULT_ROUND -> {
                if (message.getData() instanceof List list) {
                    if (list.getFirst() instanceof Score score) {
                        List<Score>roundScores = (List<Score>) message.getData();
                        for (User u : score.getPlayers()) {
                            Connections c = ServerListener.findConnectionsByUser(u.getUsername());
                            c.send(new Message(MessageType.RESULT_ROUND, roundScores));
                        }
                    }
                }
                return null;
            }
            case GAME_FINISHED -> {
                if (message.getData() instanceof Game game) {
                    for (User u : game.getPlayers()) {
                        Connections c = ServerListener.findConnectionsByUser(u.getUsername());
                        c.send(new Message(MessageType.GAME_FINISHED, game));
                    }
                }
                return null;
            }

            default -> {
                return new Message(MessageType.ERROR, "SERVERPROTOCOL: Invalid message");
            }
        }
        return new Message(MessageType.ERROR, "SERVERPROTOCOL: Unhandled messagetype");
    }

    protected static void serializeAuthenticationDatabase(){
        ad.saveUsers();
        IO.println("SERVERPROTOCOL: User serialized successfully");
    }
}
