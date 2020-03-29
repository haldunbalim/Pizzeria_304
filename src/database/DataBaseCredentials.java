package database;

public class DataBaseCredentials {
    public static final String EXCEPTION_TAG = "[EXCEPTION]";
    public static final String WARNING_TAG = "[WARNING]";
    // NOTE:
    private static final String username = "ora_YOURCWL";
    private static final String password = "aSTUDENTNUMBER";

    public static String getUsername() {
        return username;
    }

    public static String getPassword() {
        return password;
    }
}
