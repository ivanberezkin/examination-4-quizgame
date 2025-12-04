package Quizgame.shared;

import GameComponents.GameManager;

import javax.swing.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class User implements Serializable {

    private String username;
    private String password;


    public enum UserState {
        CONNECTED,
        WAITING,
        PLAYING,
        FINISHED,
        DISCONNECTED,
    }
    private UserState state;

    private int score;
    private int correctAnswers;
    private int incorrectAnswers;
    private boolean connected;
    private List<GameManager> gameManagers = new ArrayList<>();

    private Icon avatar;

    public User(String username,String password){
        this.username = username;
        this.password = password;
        resetMatchStats();
        this.state = UserState.WAITING;
        this.connected = true;
    }


    public Icon getAvatar() {
        return avatar;
    }

    public void setAvatar(Icon avatar) {
        this.avatar = avatar;
    }

    public String getUsername() {return username;}
    public String getPassword() {return password;}

    public void resetMatchStats() {
        this.score = 0;
        this.correctAnswers = 0;
        this.incorrectAnswers = 0;
    }

    public void addCorrectAnswers(int points) {
        this.correctAnswers++;
        this.score += points;
    }

}