package Server;

import Database.Question;
import GameComponents.Match;
import GameComponents.MatchQuestion;
import Quizgame.shared.*;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

//The old server

public class ClientHandler extends Thread {
    ObjectOutputStream out;
    ObjectInputStream in;
    Connections newConnection;

    public ClientHandler(Socket socket) {
        try {
            this.out = new ObjectOutputStream(socket.getOutputStream());
            this.in = new ObjectInputStream(socket.getInputStream());
            newConnection = new Connections(out,in);
            ServerListener.addNewConnection(newConnection);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void run() {
        try {
            while (true) {
                System.out.println("__In ClientHandler, run() was reached ");
                Message message = (Message) in.readObject();
                System.out.println("In ClientHandler, message is: " + message.getData().getClass());
                Message response = ServerProtocol.processInput(message);

                if (response != null) {
                    System.out.println("XXXX In ClientHandler, response is: " + response.getData().getClass());
                    if (response.getData() instanceof String) {
                        System.out.println("response is: " + message.getData() + ", with messageType: " + response.getType());
                    }
                    //If Login OK then we are assigning a User to the connection.
                    if (response.getType() == MessageType.LOGIN_OK
                            || response.getType() == MessageType.LOGIN_CREATE_OK) {
                        newConnection.setUser((User) response.getData());
                        IO.println(newConnection.getUser().getUsername() + " added to connectionList");
                        IO.println(ServerListener.numberOfConnectionsInAllConnectedClientsList() + " connected users total.");
                    }
                    out.writeObject(response);
                    out.flush();
                }
            }
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Client disconnected");
        }

    }
}
