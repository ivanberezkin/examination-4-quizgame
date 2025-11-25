package GameComponents;

import Database.*;
import Quizgame.shared.User;

public class Game {
    private User player1;
    private User player2;
    private User[]players;
    private Database.Question.Category[] category;
    private Set set;

    public Game(User[] players, Database.Question.Category[] category){
        this.player1 = players[0];
        this.player2 = players[1];
        this.category = category;
    }
    public Set startSet(){
        if (!set.checkIfCompleted()){
            set = new Set(players,category);
        }
    }
}
