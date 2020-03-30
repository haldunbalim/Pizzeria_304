package DataSource;

import Model.User;
import Model.UserType;
import database.DataBaseCredentials;
import database.DatabaseConnectionHandler;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.sql.Statement;

public class UserDataSource {
    private static UserDataSource instance = new UserDataSource();
    private static DatabaseConnectionHandler dbHandler = DatabaseConnectionHandler.getInstance();
    private static Connection connection = dbHandler.getConnection();

    private UserDataSource() {
    }

    public static UserDataSource getInstance() {
        return instance;
    }

    //TODO: check user type and update address
    public void updateUserFields(User user) {
        try {;
            Statement stmt = connection.createStatement();

            long id = user.getUID();
            String username = user.getUsername();
            String password = user.getPassword();
            String name = user.getName();
            String surname = user.getSurname();
            String phoneNumber = user.getPhoneNumber();

            String statement = String.format("UPDATE TABLE Users" +
                    "SET username = '%s'," +
                        "password = '%s'," +
                        "name ='%s'," +
                        "surname ='%s'," +
                        "phoneNumber = '%s'" +
                        "WHERE user_id=%d;",
                        username,
                        password,
                        name,
                        surname,
                        phoneNumber,
                        id);

            stmt.execute(statement);

            if (user.getUserType() == UserType.CUSTOMER) {

            }
            connection.commit();

        } catch (SQLIntegrityConstraintViolationException e) {
            System.out.println(DataBaseCredentials.EXCEPTION_TAG + DataBaseCredentials.userNameTaken);
        } catch (SQLException e) {
            System.out.println(DataBaseCredentials.EXCEPTION_TAG + DataBaseCredentials.profileUpdateError);
        }

    }
}
