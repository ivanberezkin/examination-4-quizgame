package Server;

import Quizgame.shared.User;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class AuthenticationDatabase {

    private static final String FILE_NAME = "user.dat";
    private static List<User> users = loadUsers();


    public static User getUserByUsername(String username) {
        for (User u : users) {
            if (u.getUsername().equals(username)) {
                return u;
            }
        }
        return null;
    }

    public static boolean userExists(String username) {
        return getUserByUsername(username) != null;
    }

    public static void createUser(String username, String password) {
        User newUser = new User(username, password);
        users.add(newUser);
        saveUsers();
    }

    //Intern lagring
    @SuppressWarnings("unchecked") //Används för att slippa kompilatorvarning vid filläsningen.
    private static List<User> loadUsers() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(FILE_NAME))) {
            return (List<User>) ois.readObject();
        } catch (Exception e) {
            return new ArrayList<>();
        }
    }


    private static void saveUsers() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(FILE_NAME))) {
            oos.writeObject(users);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


   /* public static boolean validateLogin(String username, String password) {
        for (User u : users) {
            if (u.getUsername().equals(username) &&
                    u.getPassword().equals(password)) {
                return true;
            }
        }
        return false;
    }




    public static List<User> getAllUsers() {
        return users;
    } */

}
