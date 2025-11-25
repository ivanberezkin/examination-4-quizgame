package Client;

import Database.Question;
import GUI.GamePanel;
import Quizgame.shared.*;

import javax.swing.*;
//import java.io.IO;
import java.util.ArrayList;
import java.util.Scanner;

public class ClientProtocol {

    private final ClientBase client;
    private final Scanner scanner;
    private JFrame frame;

    public ClientProtocol(ClientBase client, Scanner scanner, JFrame frame) {
        this.frame = frame;
        this.client = client;
        this.scanner = scanner;
    }

    public void handleMessage(Message message) {
        IO.println("Message type to process " + message.getType());
        switch (message.getType()) {

            case LOGIN_OK -> {
                User loggedInUser = (User) message.getData();
                JOptionPane.showMessageDialog(frame, "Welcome " + loggedInUser.getUsername());

                //Continue with matchmaking or game
            }

            case LOGIN_WRONG_PASSWORD -> {
                JOptionPane.showMessageDialog(null,
                        "Wrong password. Try again",
                        "Login failed",
                        JOptionPane.ERROR_MESSAGE);
            }



            case GAME_START -> {
                // Add game logic here
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
}
