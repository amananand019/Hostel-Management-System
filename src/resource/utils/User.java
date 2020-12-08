package resource.utils;

public class User {
    private static String user;

    public static String getUser() {
        return user;
    }

    public static void setUser(String user) {
        User.user = user;
    }
}
