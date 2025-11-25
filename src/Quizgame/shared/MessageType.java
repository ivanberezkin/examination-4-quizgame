package Quizgame.shared;

import java.io.Serializable;

public enum MessageType implements Serializable {
    USERNAME_REQUEST,
    USERNAME,
    USERNAME_OK,
    USERNAME_TAKEN,
    GAME_START,
    QUESTION,
    ANSWER,
    RESULT_ROUND,
    GAME_FINISHED,

    //for authentication
    LOGIN_REQUEST,
    LOGIN_OK,
    LOGIN_WRONG_PASSWORD,
    LOGIN_USER_NOT_FOUND,
    LOGIN_CREATE_REQUEST,
    LOGIN_CREATE_OK,
    LOGIN_CREATE_FAIL
}
