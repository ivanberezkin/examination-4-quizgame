package Client;

import GUI.GamePanel;
import Quizgame.shared.*;

import java.util.Scanner;

public class ClientProtocol {

    private final ClientBase client;
    private final Scanner scanner;

    public ClientProtocol(ClientBase client, Scanner scanner, GamePanel gamePanel) {
        this.client = client;
        this.scanner = scanner;
    }

    public void handleMessage (Message message) {
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

            case RESULT_ROUND -> {
                // Add game logic here
            }

            case GAME_FINISHED -> {
                //add game logic here
            }
        }
    }
}
