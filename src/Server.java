import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class Server extends Thread {
    ObjectOutputStream outputStream;
    ObjectInputStream inputStream;

    public Server(Socket socket){
        try {
            this.outputStream = new ObjectOutputStream(socket.getOutputStream());
            outputStream.flush();
            this.inputStream = new ObjectInputStream(socket.getInputStream());
            ServerListener.addOutputStream(outputStream);
        }
        catch (IOException e){
            e.printStackTrace();
        }
    }
    public void run (){
        while (true){
            try {
                Object object = inputStream.readObject();

                ServerListener.processInput(object);
            } catch (IOException | ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
