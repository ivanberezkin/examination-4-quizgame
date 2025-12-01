package GameComponents;

import Database.Question;
import Quizgame.shared.User;

public class Score {
    User player;
    Question question;
    Rond rond;
    int points;

    public Score(User player, Question question, Rond rond, int points){
        this.player = player;
        this.question = question;
        this.rond = rond;
        this.points = points;
    }
    public User getPlayer(){
        return player;
    }
    public Question getQuestion(){
        return question;
    }
    public Rond getMatch (){
        return rond;
    }
    public int getPoints (){
        return points;
    }
}
