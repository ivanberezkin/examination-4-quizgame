package Server;

import Quizgame.shared.*;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class ServerListener {
    private static final List<Connections> allConnectedClientsList = new ArrayList<>();

    private int port = 12344;

    public void start() {
        try (ServerSocket serverSocket = new ServerSocket(port)) {
            System.out.println("Server listening on port " + port);

            while (true) {
                Socket socket = serverSocket.accept();
                System.out.println("Client connected!");

                ClientHandler handler = new ClientHandler(socket);
                handler.start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void addNewConnection(Connections conn) {
        allConnectedClientsList.add(conn);
    }

    public static int numberOfConnectionsInAllConnectedClientsList() {
        return allConnectedClientsList.size();
    }

    public static Connections findConnectionsByUser(String username) {
        for (Connections conn : allConnectedClientsList) {
            if(conn.getUser().getUsername().equals(username)) {
                return conn;
            }
        }
        return null;
    }

    public static void main(String[] args) {
        new ServerListener().start();
    }
}

 /*
    public static void sendOutputToAll(Object object) {
        try {
            for (ObjectOutputStream outputStream : allServers) {
                outputStream.writeObject(object);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void processInput(Message message) {
        if (message != null) {
            Message messageFromServer = ServerProtocol.processInput(message);

            IO.println("Message sent from processInput " + messageFromServer.getType());

            sendOutputToAll(messageFromServer);
        }
    }
} */
