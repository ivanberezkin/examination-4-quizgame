package Server;

import Quizgame.shared.User;

import java.util.ArrayList;
import java.util.List;

public class AuthenticationDatabase {

    private static final List<User> users = new ArrayList<User>();

    public static boolean userExists(String username) {
        for (User u : users) {
            if (u.getUsername().equals(username)) {
                return true;
            }
        }
        return false;
    }

    public static boolean validateLogin(String username, String password) {
        for (User u : users) {
            if (u.getUsername().equals(username) &&
                    u.getPassword().equals(password)) {
                return true;
            }
        }
        return false;
    }
    public static boolean createUser(String username, String password) {
        if (userExists(username)) {
            return false;
        }
        users.add(new User(username, password));
        return true;
    }

    public static User getUserByUsername(String username) {
        for (User u : users) {
            if (u.getUsername().equals(username)) {
                return u;
            }
        }
        return null;
    }

    public static List<User> getAllUsers() {
        return users;
    }

}
