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
//                case USERNAME_REQUEST -> {
//                    if(UserDatabase.checkIfNameIsAvailable(message)) {
//                        UserDatabase.saveNewUser(message);
//                        return new Message(MessageType.USERNAME_OK, message);
//                    }
//                    else {
//                        return new Message(MessageType.USERNAME_TAKEN, message);
//                    }
//                }
                case USERNAME -> {
                }
                case USERNAME_OK -> {
                }
                case USERNAME_TAKEN -> {
                }
                case GAME_START -> {
                }
                case QUESTION -> {
                }
                case MATCHMAKING -> {
                    IO.println("User requesting Questions");
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

                case LOGIN_REQUEST -> {
                    User user = (User) message.getData();
                    User existingUser = UserDatabase.getUserByUsername(user.getUsername());

                    if (existingUser == null) {
                        return new Message(MessageType.LOGIN_USER_NOT_FOUND, null);
                    } else if (!existingUser.checkPassword(user.getPassword())) {
                        return new Message(MessageType.LOGIN_WRONG_PASSWORD, null);
                    } else {
                        return new Message(MessageType.LOGIN_OK, existingUser);
                    }
                }

                case LOGIN_CREATE_REQUEST -> {
                    User user = (User) message.getData();
                    if (UserDatabase.checkIfNameIsAvailable(user.getUsername())) {
                        UserDatabase.saveNewUser(user);
                        return new Message(MessageType.LOGIN_CREATE_OK, user);
                    } else {
                        return new Message(MessageType.LOGIN_CREATE_FAIL, null);
                    }
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
