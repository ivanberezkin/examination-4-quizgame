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

}