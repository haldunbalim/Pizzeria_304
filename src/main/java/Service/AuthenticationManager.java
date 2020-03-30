package Service;

import Model.User;
import database.DatabaseConnectionHandler;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import static database.DataBaseCredentials.EXCEPTION_TAG;

public class AuthenticationManager {

    private static AuthenticationManager instance = new AuthenticationManager();
    private User currentUser;

    private DatabaseConnectionHandler dbHandler = DatabaseConnectionHandler.getInstance();

    private AuthenticationManager() {
        //dbHandler.connectToDatabase();
    }

    public static AuthenticationManager getInstance() {
        return instance;
    }

    // TODO: create user from User class, for now hard coded
    // TODO: change user_id's to be sequentially starting from
    // returns info about Authentication Status
    // call login after signUp is successfully complete
    public AuthStatus signUp(String username, String password) {
        try {
            Connection c = dbHandler.getConnection();
            Statement stmt = c.createStatement();

            long newID = assignUserIdD();
            String statement = String.format("INSERT INTO Users Values (%d, '%s', '%s', '%s', null, '%s')",
                    newID, "name", "surname", username, password);
            stmt.execute(statement);
            c.commit();
            stmt.close();

            return AuthStatus.NEW_REGISTRATION;
        } catch (SQLException e) {
            System.out.println(EXCEPTION_TAG + " " + e.getMessage());
            return AuthStatus.AUTH_FAILED;
        }
    }

    // TODO: Login function
    // returns info about Authentication Status
    // record currentUser as a property of the class
    public AuthStatus login(String username, String password) {
        try {
            Statement stmt = dbHandler.getConnection().createStatement();

            String statement = "SELECT password FROM USERS WHERE USERNAME='" + username + "'";
            ResultSet rs = stmt.executeQuery(statement);

            String userPassword = "";
            while (rs.next()) {
                userPassword = rs.getString("password");
            }
            rs.close();
            stmt.close();

            if (password.equalsIgnoreCase(userPassword)) {
                //currentUser = new User(username, password);
                return AuthStatus.AUTH_SUCCESSFUL;
            } else {
                return AuthStatus.AUTH_FAILED;
            }
        } catch (SQLException e) {
            System.out.println(EXCEPTION_TAG + " " + e.getMessage());
            return AuthStatus.AUTH_FAILED;
        }
    }


    public User getCurrentUser() {
        return currentUser;
    }

    // auxilary functions
    // finds the largest user id and returns the next value
    // TODO: replace with authomatic incrementing
    public long assignUserIdD() {
        try {
            Statement stmt = dbHandler.getConnection().createStatement();

            String statement = "Select user_id from Users Order By user_id DESC";
            ResultSet rs = stmt.executeQuery(statement);

            long nextID; //= Long.parseLong("10000000000");
            while (rs.next()) {
                nextID = rs.getLong("user_id") + 1;
                return nextID;
            }
            rs.close();
            stmt.close();

        } catch (SQLException e) {
            System.out.println(EXCEPTION_TAG + " " + e.getMessage());
        }
        return -1;
    }

}
