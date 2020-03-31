package DataSource;

import Model.User;
import Model.UserType;
import database.DataBaseCredentials;

import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.sql.Statement;

public class UserDataSource extends AbstractDataSource{
    private static UserDataSource instance = new UserDataSource();

    private UserDataSource() {
        primaryTable = "Users";
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
                        "WHERE user_id=%d",
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
            stmt.close();

        } catch (SQLIntegrityConstraintViolationException e) {
            System.out.println(DataBaseCredentials.EXCEPTION_TAG + DataBaseCredentials.userNameTaken);
        } catch (SQLException e) {
            System.out.println(DataBaseCredentials.EXCEPTION_TAG + DataBaseCredentials.profileUpdateError);
        }

    }

    public void removeUserData(User user) {
        long user_id = user.getUID();
        removeFromDb(primaryTable, String.format("user_id=%d", user_id));
    }
}
