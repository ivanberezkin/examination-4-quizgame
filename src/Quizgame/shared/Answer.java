package Quizgame.shared;

import Database.Question;

import java.io.Serializable;

public class Answer implements Serializable {
    private User user;
    private Question question;
    private boolean isAnswerCorrect;

    public Answer (User user, Question question, boolean isAnswerCorrect){
        this.user = user;
        this.question = question;
        this.isAnswerCorrect = isAnswerCorrect;
    }
    public User getUser (){
        return user;
    }
    public Question getQuestion(){
        return question;
    }
    public boolean getIsAnswerCorrect(){
        return isAnswerCorrect;
    }
}
