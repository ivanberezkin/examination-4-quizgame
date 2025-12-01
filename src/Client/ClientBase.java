package Client;

import GUI.*;
import Quizgame.shared.*;

import javax.swing.*;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Scanner;

public class ClientBase extends Thread {

    private ObjectOutputStream out;
    private static ObjectInputStream in;
    private static boolean running = true;
    private final ClientProtocol protocol;
    private final static String host = "127.0.0.1";
    private final static int port = 12345;
    private final MainFrame mainframe;

    public ClientBase(String host, int port, MainFrame frame) {
        this.mainframe = frame;
        this.protocol = new ClientProtocol(this, frame);

        try {
            Socket socket = new Socket(host,port);
            out = new ObjectOutputStream(socket.getOutputStream());
            out.flush();
            in = new ObjectInputStream(socket.getInputStream());

            System.out.println("CLIENTBASE: Connected to server succesfully.");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void run(){
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
        System.out.println("CLIENTBASE: sendMessage was reached. MessageType is: " + message.getType());
        try {
            out.writeObject(message);
            out.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public MainFrame getMainframe() {
        return mainframe;
    }

    public void stopClient() {
        running = false;
    }
}

