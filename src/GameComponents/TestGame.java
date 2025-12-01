package GameComponents;

import Database.Question;

import java.io.Serializable;
import java.util.ArrayList;

import static GameComponents.Game.db;

public class TestGame implements Serializable {

    private String user;
    private String opponent;
    ArrayList<Question> getQuestionsForRound;

    public TestGame(String user, String opponent) {
        this.user = user;
        this.opponent = opponent;
        getQuestionsForRound = db.getQuestionsForRound(3);
    }

    public String getUser() {
        return user;
    }

    public String getOpponent() {
        return opponent;
    }

    public ArrayList<Question> getQuestionsForRound() {
        return getQuestionsForRound;
    }
}
