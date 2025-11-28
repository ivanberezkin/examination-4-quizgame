package Quizgame.shared;

import GameComponents.Game;
import GameComponents.Match;

import javax.swing.*;
import java.awt.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

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
    private List<Game>games = new ArrayList<>();
    private final String defaultAvatarFilename = "resources/Avatars/default_avatar.png";
    private final int defaultAvatarSize = 75;
    private Icon avatar;


    public User(String username,String password){
        this.username = username;
        this.password = password;
        resetMatchStats();
        this.state = UserState.WAITING;
        this.connected = true;
        avatar = createDefaultAvatar();
    }

    private ImageIcon createDefaultAvatar(){
        ImageIcon avatar = new ImageIcon(defaultAvatarFilename);
        Image scaledAvatar = avatar.getImage().getScaledInstance(defaultAvatarSize, defaultAvatarSize, Image.SCALE_SMOOTH);
        return new ImageIcon(scaledAvatar);
    }

    public Icon getAvatar() {
        return avatar;
    }

    public void setAvatar(Icon avatar) {
        this.avatar = avatar;
    }

    public String getUsername() {return username;}
    public String getPassword() {return password;}
    public int getScore() {return score;}
    public int getCorrectAnswers() {return correctAnswers;}
    public int getIncorrectAnswers() {return incorrectAnswers;}
    public UserState getState() {return state;}
    public boolean isConnected() {return connected;}

    public void setUsername(String username) {this.username = username;}
    public void setPassword(String password) {this.password = password;}
    public void setState(UserState state) {this.state = state;}
    public void setConnected(boolean connected) {this.connected = connected;}

    public boolean checkPassword(String password) {
        return this.password.equals(password);
    }


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

    public List<Game>getGames(){
        return games;
    }
    public void addGame(Game game){
        games.add(game);
    }

}