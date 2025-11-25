package GameComponents;

import Database.Question;
import Quizgame.shared.User;

public class Score {
    User player;
    Question question;
    Match match;
    int points;

    public Score(User player, Question question, Match match, int points){
        this.player = player;
        this.question = question;
        this.match = match;
        this.points = points;
    }
    public User getPlayer(){
        return player;
    }
    public Question getQuestion(){
        return question;
    }
    public Match getMatch (){
        return match;
    }
    public int getPoints (){
        return points;
    }
}
