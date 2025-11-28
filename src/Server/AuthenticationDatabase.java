package Server;

import Quizgame.shared.User;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class AuthenticationDatabase {

    private static final String FILE_NAME = "user.dat";
    private List<User> users;


    public AuthenticationDatabase() {
        this.users = loadUsers();
        IO.println("AD: Created succesfully");
    }

    public User getUserByUsername(String username) {
        System.out.println("getUserByUserName in A D was reached");
        for (User u : users) {
            if (u.getUsername().equals(username)) {
                System.out.println("User name in LOGIN_REQUEST is: " + u.getUsername());
                return u;
            }
        }
        return null;
    }

    public  boolean userExists(String username) {
        return getUserByUsername(username) != null;
    }

    public void createUser(String username, String password) {
        User newUser = new User(username, password);
        users.add(newUser);
       saveUsers();
    }

    //Intern lagring
    @SuppressWarnings("unchecked") //Används för att slippa kompilatorvarning vid filläsningen.
    protected List<User> loadUsers() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(FILE_NAME))) {
            return (List<User>) ois.readObject();
        } catch (Exception e) {
            return new ArrayList<>();
        }
    }

    protected void updateAvatarForUser(User user) {
        for(User u: users){
            if(u.getUsername().equals(user.getUsername())){
                u.setAvatar(user.getAvatar());
                System.out.println("AD: Avatar updated for user " + user.getUsername());
            }
        }
    }

    protected void printUsers(){
        for(User u : users){
            System.out.println("AD: " + u.getUsername() + " Avatar: "+u.getAvatar());
        }
    }

    protected void saveUsers() {
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
