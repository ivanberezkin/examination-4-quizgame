package Server;
import Quizgame.shared.*;
public class ServerProtocol {

    public static Message processInput(Message message) {
        MessageType messageType = message.getType();
        if (checkIfValid(message)) {
            switch (messageType){
                case USERNAME_REQUEST -> {
                    if(ServerListener.checkIfNameIsAvailable(message)) {
                        ServerListener.saveNewUser(message);
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
