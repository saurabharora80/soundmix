package domain;

import java.util.HashMap;
import java.util.Map;

public class UserFactory {
    /**
     * Not Thread safe, but ignoring this for the purpose of this exercise
     */
    private static Map<String, User> users = new HashMap<>();

    /**
     * Not Thread safe, but ignoring this for the purpose of this exercise
     */
    public static User get(String username) {
        if(!users.containsKey(username)) {
            users.put(username, new User(username));
        }

        return users.get(username);
    }

    public static void reset() {
        users.clear();
    }
}
