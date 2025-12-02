package Quizgame.shared;

import Database.Question;
import java.io.Serializable;

public class UserAndCategory implements Serializable {
    private final User user;
    private final Question.Category category;
    public UserAndCategory(User user, Question.Category category){
        this.user = user;
        this.category = category;
    }
    public User getUser(){
        return user;
    }
    public Question.Category getCategory(){
        return category;
    }
}
