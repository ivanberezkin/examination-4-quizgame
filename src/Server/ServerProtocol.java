package Server;

import Database.*;
import Quizgame.shared.*;

import java.util.ArrayList;

public class ServerProtocol {

    static Database db = new Database();

    public static Message processInput(Message message) {
        MessageType messageType = message.getType();
        if (checkIfValid(message)) {
            switch (messageType) {
                case LOGIN_REQUEST -> {
                    User loginUser = (User) message.getData();
                    User existingUser = UserDatabase.getUserByUsername(loginUser.getUsername());

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
                }
                case QUESTION -> {
                }
                case MATCHMAKING -> {
                    ArrayList<Question> questionsForUserList = db.getQuestionsForRound(3);
                    IO.println("Sending Questions to user");
                    return new Message(MessageType.QUESTION, questionsForUserList);

                }
                case ANSWER -> {
                }
                case RESULT_ROUND -> {
                }
                case GAME_FINISHED -> {
                }


            }
        }
        return message;
    }

    private static boolean checkIfValid(Message message) {
        if (message == null || message.getType() == null) {
            return false;
        }
        return true;
    }

}
