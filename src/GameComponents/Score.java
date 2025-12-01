package GameComponents;

import Database.Question;
import Quizgame.shared.User;

public class Score {
    User player;
    Question question;
    Round round;
    int points;

    public Score(User player, Question question, Round round, int points){
        this.player = player;
        this.question = question;
        this.round = round;
        this.points = points;
    }
    public User getPlayer(){
        return player;
    }
    public Question getQuestion(){
        return question;
    }
    public Round getMatch (){
        return round;
    }
    public int getPoints (){
        return points;
    }
}
