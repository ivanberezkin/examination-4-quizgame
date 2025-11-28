package Server;

import Quizgame.shared.*;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

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
                Message message = (Message) in.readObject();
                Message response = ServerProtocol.processInput(message);

                //If Login OK then we are assigning a User to the connection.
                if (response.getType() == MessageType.LOGIN_OK
                        || response.getType() == MessageType.LOGIN_CREATE_OK) {
                    newConnection.setUser((User) response.getData());
                    IO.println("CLIENTHANDLER: " + newConnection.getUser().getUsername() + " added to connectionList");
                    IO.println("CLIENTHANDLER: " + ServerListener.numberOfConnectionsInAllConnectedClientsList() + " connected users total.");
                }

                out.writeObject(response);
                out.flush();
            }
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("CLIENTHANDLER: Client disconnected");
        }
    }
}
