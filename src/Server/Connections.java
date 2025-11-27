package Server;

import Quizgame.shared.User;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class Connections {
    private ObjectOutputStream out;
    private ObjectInputStream in;
    private User user;


    public Connections(ObjectOutputStream out, ObjectInputStream in) {
        this.out = out;
        this.in = in;
    }

    public ObjectOutputStream getOut() {
        return out;
    }

    public ObjectInputStream getIn() {
        return in;
    }

    public User getUser() {
        return user;
    }
}
