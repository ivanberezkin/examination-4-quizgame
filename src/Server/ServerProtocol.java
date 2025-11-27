package Server;

import Database.*;
import GameComponents.Game;
import GameComponents.TestGame;
import Quizgame.shared.*;

import java.util.ArrayList;
import java.util.List;

public class ServerProtocol {

    private static Database db = new Database();

    public static Message processInput(Message message) {

        if(message == null || message.getType() == null) {
            return new Message(MessageType.ERROR, "Invalid message");
        }
        MessageType type = message.getType();

        switch (type) {
            case LOGIN_REQUEST -> {
                User loginUser = (User) message.getData();
                User existingUser = AuthenticationDatabase.getUserByUsername(loginUser.getUsername());

                if (existingUser == null) {
                    return new Message(MessageType.LOGIN_USER_NOT_FOUND, null);
                }
                if (!existingUser.getPassword().equals(loginUser.getPassword())) {
                    return new Message(MessageType.LOGIN_WRONG_PASSWORD, null);
                }
                return new Message(MessageType.LOGIN_OK, existingUser);
            }

            case LOGIN_CREATE_REQUEST -> {
                User newUser = (User) message.getData();
                if (AuthenticationDatabase.userExists(newUser.getUsername())) {
                    return new Message(MessageType.LOGIN_CREATE_FAIL, null);
                }

                AuthenticationDatabase.createUser(newUser.getUsername(), newUser.getPassword());
                return new Message(MessageType.LOGIN_CREATE_OK, newUser);
            }

            case GAME_START -> {
                List<Connections> players = new ArrayList<>();
                List<User> users = new ArrayList<>();
                if (message.getData() instanceof User){
                    User user = (User) message.getData();
                    users.add(user);
                }
                else if (message.getData() instanceof List list){
                    if (!list.isEmpty() && list.getFirst() instanceof User){
                        users = list;
                    }
                }
                if (!users.isEmpty()){
                    Matchmaking matchmaking = new Matchmaking(ServerListener.findConnectionsByUser(
                            message.getData().toString().trim()));
                    for (User u : users) {
                        Connections player = matchmaking.getFirstConnectionFromMatchMakingList();
                        player.setUser(u);
                        players.add(player);
                    } //Category will be chosen by user later on
                    return new Message(MessageType.MATCHMAKING, new Game(players, Question.Category.ANIMALS));
                }
            }

            case MATCHMAKING_WAITING_FOR_OPPONENT -> {
                while(Matchmaking.getMatchMakingListSize() < 2) {

                }

            }
            case MATCHMAKING -> {

                Matchmaking matchmaking = new Matchmaking(ServerListener.findConnectionsByUser(
                        message.getData().toString().trim()));
                IO.println("MATCHMAKING:" + message.getData().toString() + " added to matchmaking List!");

                if(Matchmaking.getMatchMakingListSize() > 1){
                    Connections opponentA = matchmaking.getFirstConnectionFromMatchMakingList();
                    Connections opponentB = matchmaking.getFirstConnectionFromMatchMakingList();
                    sendQuestionsToClients(opponentA, opponentB);

                    //TODO här bör ett Game objekt skapas och skickas tillbaka till bägge klienterna.
                }else{
                    return new Message(MessageType.WAITING, null);
                }

//                    ArrayList<Question> questionsForUserList = db.getQuestionsForRound(3);
//                    return new Message(MessageType.QUESTION, questionsForUserList);

            }
            case ANSWER -> {
                //TODO: svarshantering
            }
            case RESULT_ROUND -> {
                //TODO: logik för rundresultatet
            }
            case GAME_FINISHED -> {
                return new Message(MessageType.GAME_FINISHED, null);
            }

            default -> {
                return new Message(MessageType.ERROR, "Invalid message");
            }
        }
        return new Message(MessageType.ERROR, "Unhandled messagetype");
    }

    private static void sendQuestionsToClients(Connections opponentA, Connections opponentB) {
        IO.println("MATCHMAKING:" + opponentA.getUser().getUsername() + " entered game against " + opponentB.getUser().getUsername());
        TestGame testGame = new TestGame(opponentA.getUser().getUsername(),
                opponentB.getUser().getUsername());

        opponentA.send(new Message(MessageType.QUESTION,testGame));
        opponentB.send(new Message(MessageType.QUESTION,testGame));
    }

}

