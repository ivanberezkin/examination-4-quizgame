package Server;

import Client.ClientProtocol;
import Database.*;
import GameComponents.Game;
import GameComponents.Match;
import GameComponents.MatchQuestion;
import GameComponents.TestGame;
import Quizgame.shared.*;

import java.util.ArrayList;
import java.util.List;

public class ServerProtocol {
    static Game game = new Game();

    private static Database db = new Database();

    public static Message processInput(Message message) {
        System.out.println("In ServerProtocol, processInput was reached, message type is:" + message.getType());

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
                    return new Message(MessageType.LOGIN_WRONG_PASSWORD, loginUser);
                }
                return new Message(MessageType.LOGIN_OK, existingUser);
            }

            case LOGIN_CREATE_REQUEST -> {
                User newUser = (User) message.getData();
                if (AuthenticationDatabase.userExists(newUser.getUsername())) {
                    return new Message(MessageType.LOGIN_CREATE_FAIL, newUser);
                }

                AuthenticationDatabase.createUser(newUser.getUsername(), newUser.getPassword());
                return new Message(MessageType.LOGIN_CREATE_OK, newUser);
            }
            case QUESTION -> {
                System.out.println("---In ServerProtocol, case QUESTION was reached, message is: " + message.getData().getClass());
                if (message.getData() instanceof MatchQuestion matchQuestion) {
                    for (User u : matchQuestion.getUsers()) {
                        Connections c = ServerListener.findConnectionsByUser(u.getUsername());
                        c.send(new Message(MessageType.QUESTION, matchQuestion.getQuestion()));
                    }
                }
            }

            case GAME_START -> {
                System.out.println("In ServerProtocol, GAME_START was reached, message is: " + message.getData().getClass());
                List<Connections> players = new ArrayList<>();
                List<User> users = new ArrayList<>();
                if (message.getData() instanceof MatchQuestion matchQuestion) {
                    if (!matchQuestion.getUsers().isEmpty()) {
                        Matchmaking matchmaking = new Matchmaking(ServerListener.findConnectionsByUser(
                                message.getData().toString().trim()));
                        for (User u : users) {
                            Connections player = matchmaking.getFirstConnectionFromMatchMakingList();
                            player.setUser(u);
                            players.add(player);
                            player.send(new Message(MessageType.DUMMY, users.getFirst()));
                        }

                    }
                }
                else if (message.getData() instanceof User user) {
                    game.startGame(user, Question.Category.ANIMALS);//Category will be chosen by user later on
                return null;
                }
            }
            case MATCHMAKING_WAITING_FOR_OPPONENT -> {
                while(Matchmaking.getMatchMakingListSize() < 2) {

                }
            }
            case MATCHMAKING -> {
                System.out.println("In ServerProtocol, GAME_START was reached, message type is:" + message.getType() + " Class is: " + message.getData().getClass());
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
                           users.getFirst().getUsername()));
                    for (User u : users) {
                        Connections player = matchmaking.getFirstConnectionFromMatchMakingList();
                        player.setUser(u);
                        players.add(player);
                   //     player.send(new Message(MessageType.DUMMY, users.getFirst()));
                    }
                    game.startGame(users.getFirst(), Question.Category.ANIMALS);//Category will be chosen by user later on

                    return new Message(MessageType.GAME_START, users.getFirst());
                }
            }
//
//                Matchmaking matchmaking = new Matchmaking(ServerListener.findConnectionsByUser(
//                        message.getData().toString().trim()));
//                IO.println("MATCHMAKING:" + message.getData().toString() + " added to matchmaking List!");
//
//                if(Matchmaking.getMatchMakingListSize() > 1){
//                    Connections opponentA = matchmaking.getFirstConnectionFromMatchMakingList();
//                    Connections opponentB = matchmaking.getFirstConnectionFromMatchMakingList();
//                    sendQuestionsToClients(opponentA, opponentB);
//
//                    //TODO här bör ett Game objekt skapas och skickas tillbaka till bägge klienterna.
//                }else{
//                    return new Message(MessageType.WAITING, null);
//                }
//
////                    ArrayList<Question> questionsForUserList = db.getQuestionsForRound(3);
////                    return new Message(MessageType.QUESTION, questionsForUserList);

//            }
            case ANSWER -> {
                System.out.println("--- case Answer was reached, message type is: " + message.getData().getClass());
                if (message.getData() instanceof Answer) {
                    Answer answer = (Answer) message.getData();
                    Game.continueGame(answer);
                    return null;
                }
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

