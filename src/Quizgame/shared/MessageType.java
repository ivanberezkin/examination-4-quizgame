package Quizgame.shared;

import java.io.Serializable;

public enum MessageType implements Serializable {
    ERROR,
    GAME_START,
    ADDED_TO_GAME,
    CATEGORY_REQUEST,
    QUESTION,
    ANSWER,
    RESULT_ROUND,
    START_NEXT_ROUND,
    GAME_FINISHED,
    MATCHMAKING,
    WAITING,
    GIVE_UP,
    MOVE_TO_MENU,
    SETTINGS_AVATAR_CHANGED,
    CHOOSING_CATEGORIES,


    //for authentication
    LOGIN_REQUEST,
    LOGIN_OK,
    LOGIN_WRONG_PASSWORD,
    LOGIN_USER_NOT_FOUND,
    LOGIN_CREATE_REQUEST,
    LOGIN_CREATE_OK,
    LOGIN_CREATE_FAIL
}
