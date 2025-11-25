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
        IO.println("Message type to process" + message.getType());
        switch (message.getType()) {

            case USERNAME_REQUEST -> {
                System.out.print("Enter username: ");
                String username = scanner.nextLine();
                client.sendMessage(new Message(MessageType.USERNAME, username));
            }

            case USERNAME_OK -> System.out.println("Username accepted");

            case USERNAME_TAKEN -> {
                System.out.println("Username already taken, choose another username: ");
                String username = scanner.nextLine();
                client.sendMessage(new Message(MessageType.USERNAME, username));
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
