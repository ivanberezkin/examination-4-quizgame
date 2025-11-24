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
}
