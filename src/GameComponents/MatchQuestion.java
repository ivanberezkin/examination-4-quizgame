package GameComponents;

import Database.Question;
import Quizgame.shared.User;
import java.io.Serializable;
import java.util.List;

public class MatchQuestion implements Serializable {
    private List <User> users;
    private Question question;

    public MatchQuestion(List <User> users, Question question){
        this.users = users;
        this.question = question;
    }
    public List <User> getUsers(){
        return users;
    }
    public Question getQuestions(){
        return question;
    }
}
