package DataSource;

import Model.User;
import database.DataBaseCredentials;
import database.DatabaseConnectionHandler;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import static database.DataBaseCredentials.EXCEPTION_TAG;

public class AuthenticationManager extends AbstractDataSource {

    private static AuthenticationManager instance = new AuthenticationManager();
    private User currentUser;


    private AuthenticationManager() {
        primaryTable = "Users";
    }

    public static AuthenticationManager getInstance() {
        return instance;
    }


    // returns info about Authentication Status
    // call login after signUp is successfully complete
    public AuthStatus signUp(String username, String password) {
        long newId = getNextIdLong(primaryTable, "user_id");
        String statement = String.format("%d, '%s', '%s', '%s', null, '%s', '%s'",
                newId, "name", "surname", username, password, "customer");
        DataBaseCredentials.OperationResult res = insertIntoDb(primaryTable, statement);
        if (res == DataBaseCredentials.OperationResult.inserted) {
            return AuthStatus.NEW_REGISTRATION;
        } else if (res == DataBaseCredentials.OperationResult.integrityConstraintError) {
            return AuthStatus.AUTH_FAILED;
        } else {
            return AuthStatus.CONNECTION_ERROR;
        }
    }

    // returns info about Authentication Status
    // record currentUser as a property of the class
    public AuthStatus login(String username, String password) {
        try {
            Statement stmt = connection.createStatement();

            String statement = "SELECT user_id, password FROM USERS WHERE USERNAME='" + username + "'";
            ResultSet rs = stmt.executeQuery(statement);

            String userPassword = "";
            long userId = 0;
            while (rs.next()) {
                userId = rs.getLong("user_id");
                userPassword = rs.getString("password");
            }
            rs.close();
            stmt.close();

            if (password.equals(userPassword)) {
                //currentUser = new User(username, password);
                currentUser = UserDataSource.getInstance().getUser(userId);
                return AuthStatus.AUTH_SUCCESSFUL;
            } else {
                return AuthStatus.AUTH_FAILED;
            }
        } catch (SQLException e) {
            System.out.println(EXCEPTION_TAG + " " + e.getMessage());
            return AuthStatus.AUTH_FAILED;
        } catch (NullPointerException e) {
            System.out.println(EXCEPTION_TAG + " " + e.getMessage());
            return AuthStatus.CONNECTION_ERROR;
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
            Statement stmt = DatabaseConnectionHandler.getConnection().createStatement();

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
