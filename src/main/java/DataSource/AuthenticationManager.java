package DataSource;

import Model.User;
import database.DataBaseCredentials;

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


    /**
     * @param username
     * @param password
     * @return NEW_REGISTRATION if signup is successful
     */
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

    /**
     * @param username
     * @param password
     * @return authentication status of the current user
     */
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

}
