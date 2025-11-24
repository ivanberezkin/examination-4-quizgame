package Server;

import Quizgame.shared.*;

import java.util.ArrayList;
import java.util.List;

public class UserDatabase {
    private static final List<User> allUsers = new ArrayList<>();

    public static boolean checkIfNameIsAvailable(Message message){
        for (User user : allUsers){
            if(message.getData().equals(user.getUsername())){
                return false;
            }
        }
        return true;
    }
    public static void saveNewUser(Message message){
        allUsers.add((User) message.getData());
    }

    public List<User>getAllUsers(){
        return allUsers;
    }
}
