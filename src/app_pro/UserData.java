package app_pro;

import java.util.HashMap;
import java.util.Map;

public class UserData {
    private static Map<String, String> users = new HashMap<>();

    public static void addUser(String username, String password) {
        users.put(username, password);
    }

    public static boolean isUserValid(String username, String password) {
        return users.containsKey(username) && users.get(username).equals(password);
    }
}
