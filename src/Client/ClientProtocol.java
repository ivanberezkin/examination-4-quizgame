package Client;

import Database.Question;
import GUI.GamePanel;
import GUI.MenuPanel;
import Quizgame.shared.*;

import javax.swing.*;
import java.util.ArrayList;
import java.util.Scanner;

public class ClientProtocol {

    private final ClientBase client;
    private JFrame frame;
    private User loggedInUser;


    public ClientProtocol(ClientBase client, JFrame frame) {
        this.frame = frame;
        this.client = client;
    }

    public void handleMessage(Message message) {
        IO.println("Message type to process " + message.getType());

        switch (message.getType()) {

            case LOGIN_OK -> {
                loggedInUser = (User) message.getData();
                JOptionPane.showMessageDialog(frame, "Welcome " + loggedInUser.getUsername());
                //Continue with matchmaking or game
                moveUserToMenuPanel();
            }

            case LOGIN_WRONG_PASSWORD -> {
                JOptionPane.showMessageDialog(null,
                        "Wrong password. Try again",
                        "Login failed",
                        JOptionPane.ERROR_MESSAGE);
            }

            case LOGIN_USER_NOT_FOUND -> {
                int choice = JOptionPane.showConfirmDialog(null,
                        "User does not exist. Create a new user?",
                        "Create user",
                        JOptionPane.YES_NO_OPTION);

                if (choice == JOptionPane.YES_OPTION) {
                    String username = JOptionPane.showInputDialog(frame, "Enter your username:");
                    if (username == null || username.isEmpty()) {
                        JOptionPane.showMessageDialog(frame, "Username cannot be empty.");
                        return;
                    }

                    String password = JOptionPane.showInputDialog(frame, "Enter your password:");
                    if (password == null || password.isEmpty()) {
                        JOptionPane.showMessageDialog(frame, "Password cannot be empty.");
                        return;
                    }

                    User newUser = new User(username, password);
                    client.sendMessage(new Message(MessageType.LOGIN_CREATE_REQUEST, newUser));
                } else {
                    JOptionPane.showMessageDialog(frame, "Please try again.");
                }
            }

            case LOGIN_CREATE_OK -> {
                loggedInUser = (User) message.getData();
                JOptionPane.showMessageDialog(null,
                        "User created! Logged in as " + loggedInUser.getUsername());
                moveUserToMenuPanel();
            }

            case LOGIN_CREATE_FAIL -> {
                JOptionPane.showMessageDialog(frame,
                        "Username already taken.",
                        "Error",
                        JOptionPane.ERROR_MESSAGE);
            }

            case WAITING -> {
                IO.println("MATCHMAKING:" + loggedInUser.getUsername() + " waiting for opponent.");
            }

            case MATCHMAKING -> {
                //Server skickar frågor i en Message_type question
            }

            case QUESTION -> {
                IO.println("Questions Received by User");
                ArrayList<Question> questionsForRound = (ArrayList<Question>) message.getData();
                GamePanel gamePanel = new GamePanel(questionsForRound);
                frame.setContentPane(gamePanel);
                frame.revalidate();
                frame.repaint();

            }

            case RESULT_ROUND -> {
                // Add game logic here
            }

            case GAME_FINISHED -> {
                //add game logic here
            }


        }
    }

    private void moveUserToMenuPanel() {
        MenuPanel menuPanel = new MenuPanel(loggedInUser.getUsername(), frame, client);
        frame.setContentPane(menuPanel);
        frame.revalidate();
        frame.repaint();
    }
}
