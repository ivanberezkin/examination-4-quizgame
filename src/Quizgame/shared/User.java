package Quizgame.shared;

import java.util.UUID;

public class User {

    private final UUID id;
    private String username;

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

    public User(String username){
        this.id = UUID.randomUUID();
        this.username = username;
        resetMatchStats();
        this.state = UserState.WAITING;
        this.connected = true;
    }

    public UUID getId() {return id;}
    public String getUsername() {return username;}
    public int getScore() {return score;}
    public int getCorrectAnswers() {return correctAnswers;}
    public int getIncorrectAnswers() {return incorrectAnswers;}
    public UserState getState() {return state;}
    public boolean isConnected() {return connected;}

    public void setUsername(String username) {this.username = username;}
    public void setState(UserState state) {this.state = state;}
    public void setConnected(boolean connected) {this.connected = connected;}



    public void resetMatchStats() {
        this.score = 0;
        this.correctAnswers = 0;
        this.incorrectAnswers = 0;
    }

    public void addCorrectAnswers(int points) {
        this.correctAnswers++;
        this.score += points;
    }

    public void addIncorrectAnswers() {
        this.incorrectAnswers++;
    }


}