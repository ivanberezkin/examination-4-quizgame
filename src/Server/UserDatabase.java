package Server;

import Quizgame.shared.*;

import java.util.ArrayList;
import java.util.List;

public class UserDatabase {
    private static final List<User> allUsers = new ArrayList<>();

    public static boolean checkIfNameIsAvailable(String username) {
        for (User user : allUsers){
            if(username.equals(user.getUsername())){
                return false;
            }
        }
        return true;
    }
    public static void saveNewUser(User user){

        allUsers.add(user);
    }
    public static User getUserByUsername(String username) {
        for (User user : allUsers){
            if(username.equals(user.getUsername())){
                return user;
            }
        }
    }

    public List<User>getAllUsers(){
        return allUsers;
    }
}
