package Server;

import Quizgame.shared.Message;
import Quizgame.shared.User;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

public class Connections implements Serializable {
    private ObjectOutputStream out;
    private ObjectInputStream in;
    private User user;

    public Connections(ObjectOutputStream out, ObjectInputStream in) {
        this.out = out;
        this.in = in;
    }

    public  void send(Message response){
        System.out.println("in Connections, message is: " + response.getType());
        try {
            out.writeObject(response);
            out.flush();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public ObjectOutputStream getOut() {
        return out;
    }

    public void setOut(ObjectOutputStream out) {
        this.out = out;
    }

    public void setIn(ObjectInputStream in) {
        this.in = in;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public ObjectInputStream getIn() {
        return in;
    }

    public User getUser() {
        return user;
    }
}
