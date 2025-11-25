package Server;

import Quizgame.shared.*;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class ServerListener {
    private static final List<ObjectOutputStream> allServers = new ArrayList<>();

    private int port = 12345;

    public ServerListener() {
        try
                (ServerSocket serverSocket = new ServerSocket(port)) {
            while (true) {
                Socket socket = serverSocket.accept();
                Server server = new Server(socket);
                server.start();
            }
        }
        catch (IOException e){
            e.printStackTrace();
        }
    }
    public static void main(String[] args){
        ServerListener serverListener = new ServerListener();
    }
    public static void addOutputStream(ObjectOutputStream outputStream){
        allServers.add(outputStream);
    }
    public static void sendOutputToAll(Object object){
        try {
            for (ObjectOutputStream outputStream : allServers) {
                outputStream.writeObject(object);
            }
        }
        catch (IOException e){
            e.printStackTrace();
        }
    }
    public static void processInput(Message message){
        if (ServerProtocol.processInput(message) != null);
        sendOutputToAll(message);
    }


}
