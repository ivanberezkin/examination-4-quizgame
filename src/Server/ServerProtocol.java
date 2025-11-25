package Server;
import Database.*;
import Quizgame.shared.*;

import java.util.ArrayList;

public class ServerProtocol {

    static Database db = new Database();

    public static Message processInput(Message message) {

        MessageType messageType = message.getType();
        if (checkIfValid(message)) {
            switch (messageType){
                case USERNAME_REQUEST -> {
                    if(UserDatabase.checkIfNameIsAvailable(message)) {
                        UserDatabase.saveNewUser(message);
                        return new Message(MessageType.USERNAME_OK, message);
                    }
                    else {
                        return new Message(MessageType.USERNAME_TAKEN, message);
                    }
                }
                case USERNAME -> {}
                case USERNAME_OK -> {}
                case USERNAME_TAKEN -> {}
                case GAME_START -> {}
                case QUESTION -> {}
                case MATCHMAKING -> {
                    ArrayList<Question> questionsForUserList = db.getQuestionsForRound(3);
                    IO.println("Sending Questions to user");
                    return new Message(MessageType.QUESTION, questionsForUserList);

                }
                case ANSWER -> {}
                case RESULT_ROUND -> {}
                case GAME_FINISHED -> {}
            }
        }
        return message;
    }
    private static boolean checkIfValid(Message message){
        if (message == null || message.getType() == null){
            return false;
        }
        return true;
    }

}
