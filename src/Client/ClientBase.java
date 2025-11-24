package Client;

import Quizgame.shared.*;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Scanner;

public class ClientBase {

    public ClientBase() {
        Scanner scanner = new Scanner(System.in);

        try(Socket socket = new Socket("127.0.0.1", 12345);
            ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
            ObjectInputStream in = new ObjectInputStream(socket.getInputStream())) {

            System.out.println("Connected to server");

            boolean running = true;

            while(running) {  //Waiting for message from server.
                Object obj = in.readObject();
                if (obj instanceof Message msg) {

                    switch(msg.getType()) {

                        case USERNAME

                    }
                }
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}
