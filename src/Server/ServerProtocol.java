package Server;

import Database.*;
import GameComponents.TestGame;
import Quizgame.shared.*;

import java.util.ArrayList;

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
                    //TODO: Starta spel
                }

                case MATCHMAKING_WAITING_FOR_OPPONENT -> {


                }
                case MATCHMAKING -> {

                    Matchmaking matchmaking = new Matchmaking(ServerListener.findConnectionsByUser(
                            message.getData().toString().trim()));
                    IO.println("MATCHMAKING:" + message.getData().toString() + " added to matchmaking List!");

                    if(matchmaking.getMatchMakingListSize() > 1){
                        Connections OpponentA = matchmaking.getFirstConnectionFromMatchMakingList();
                        Connections OpponentB = matchmaking.getFirstConnectionFromMatchMakingList();
                        IO.println("MATCHMAKING:" + OpponentA.getUser().getUsername() + " entered game against " + OpponentB.getUser().getUsername());

                        TestGame testGame = new TestGame(OpponentA.getUser().getUsername(),
                                OpponentB.getUser().getUsername());
                        return new Message(MessageType.QUESTION, testGame);
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
    }

