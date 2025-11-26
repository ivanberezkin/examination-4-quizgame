package Server;

import Quizgame.shared.*;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class ClientHandler extends Thread {
    ObjectOutputStream out;
    ObjectInputStream in;

    public ClientHandler(Socket socket){
        try {
            this.out = new ObjectOutputStream(socket.getOutputStream());
            this.in = new ObjectInputStream(socket.getInputStream());
        }
        catch (IOException e){
            throw new RuntimeException(e);
        }
    }
    @Override
    public void run (){
        try {
            while (true) {
                Message message = (Message) in.readObject();
                Message response = ServerProtocol.processInput(message);

                out.writeObject(response);
                out.flush();
            }
        } catch (IOException | ClassNotFoundException e) {
                System.out.println("Client disconnected");
            }
        }
    }
