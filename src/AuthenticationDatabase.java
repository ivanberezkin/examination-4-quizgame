import Quizgame.shared.User;

import java.util.ArrayList;
import java.util.List;

public class AuthenticationDatabase {

    private static final List<User> users = new ArrayList<User>();

    public static boolean userExists(String username) {
        for (User u : users) {
            if (u.getUsername().equals(username) {
                return true;
            }
        }
        return false;
    }


}
