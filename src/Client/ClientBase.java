package Client;

import GUI.*;
import Quizgame.shared.*;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Scanner;

public class ClientBase {

    private ObjectOutputStream out;
    private static ObjectInputStream in;
    private static boolean running = true;
    private static ClientProtocol protocol;
    private final static String host = "127.0.0.1";
    private final static int port = 12345;

    public ClientBase(String host, int port) {
        Scanner scanner = new Scanner(System.in);
        protocol = new ClientProtocol(this, scanner);

        try {
            Socket socket = new Socket(host,port);
            out = new ObjectOutputStream(socket.getOutputStream());
            out.flush();
            in = new ObjectInputStream(socket.getInputStream());

            System.out.println("Connected to server");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    static void main(String [] args){
        LoginPanel loginPanel = new LoginPanel();
        ClientBase clientBase = new ClientBase(host, port);
        run();
    }

    public static void run(){
        while (running){
            try  {
                    Object obj = in.readObject();
                    if (obj instanceof Message msg){
                        protocol.handleMessage(msg);
                    }
            } catch (IOException | ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
        }
        System.out.println("Closing the client");
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

