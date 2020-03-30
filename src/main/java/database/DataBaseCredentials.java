package database;

public class DataBaseCredentials {
    public static final String EXCEPTION_TAG = "[EXCEPTION] ";
    public static final String WARNING_TAG = "[WARNING] ";
    // NOTE
    private static final String username = "ora_gulsena";
    private static final String password = "a97968358";

    //private static final String username = "ora_hbalim";
    //private static final String password = "a48908560";

    public static String getUsername() {
        return username;
    }

    public static String getPassword() {
        return password;
    }

    // Error messages
    public static final String profileUpdateError = "User update failed.";
    public static final String userNameTaken = "Username already taken, update unsuccessful.";
    public static final String tableEmpty = "Current table is empty.";
    public static final String vehicleNotFound = "Vehicle not found.";
    public static final String licenseExists = "License already taken.";
    public static final String modelExists = "Model already inserted.";
    public static final String insertionError = "Insertion unsuccessfull.";


}
