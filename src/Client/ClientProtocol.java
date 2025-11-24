package Client;

import Quizgame.shared.*;

import java.util.Scanner;

public class ClientProtocol {

    private final ClientBase client;
    private final Scanner scanner;

    public ClientProtocol(ClientBase client, Scanner scanner) {
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
        }
    }
}
