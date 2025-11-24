package Client;

import Quizgame.shared.*;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Scanner;

public class ClientBase {

    private ObjectOutputStream out;
    private ObjectInputStream in;
    private boolean running = true;
    private final ClientProtocol protocol;

    public ClientBase(String host, int port) {
        Scanner scanner = new Scanner(System.in);
        protocol = new ClientProtocol(this, scanner);

        try (Socket socket = new Socket(host,port)){
            out = new ObjectOutputStream(socket.getOutputStream());
            out.flush();
            in = new ObjectInputStream(socket.getInputStream());

            System.out.println("Connected to server");

            while (running){
                Object obj = in.readObject();
                if (obj instanceof Message msg){
                    protocol.handleMessage(msg);
                }
            }

            System.out.println("Closing the client");
    } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void sendMessage(Message message) {
        try {
            out.writeObject(message);
            out.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void stopClient() {
        running = false;
    }
}

