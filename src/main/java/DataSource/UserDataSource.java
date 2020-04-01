package DataSource;

import Model.Address;
import Model.User;
import Model.UserType;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Locale;

public class UserDataSource extends AbstractDataSource {
    private static UserDataSource instance = new UserDataSource();
    private HashMap<String, UserType> userTypeHashMap = new HashMap<String, UserType>() {
        {
            put("CUSTOMER", UserType.CUSTOMER);
            put("MANAGER", UserType.MANAGER);
            put("EMPLOYEE", UserType.EMPLOYEE);
        }
    };

    private HashMap<UserType, String> userTypeHashMapReversed = new HashMap<UserType, String>() {
        {
            put(UserType.CUSTOMER, "CUSTOMER");
            put(UserType.MANAGER, "MANAGER");
            put(UserType.EMPLOYEE, "EMPLOYEE");
        }
    };

    private UserDataSource() {
        primaryTable = "Users";
    }

    public static UserDataSource getInstance() {
        return instance;
    }

    public User getUser(long uid) {
        User u = null;
        try {
            Statement stmt = connection.createStatement();
            String query = String.format("SELECT * FROM %s WHERE user_id=%d", primaryTable, uid);
            ResultSet rs = stmt.executeQuery(query);

            while (rs.next()) {
                String username = rs.getString("username");
                String password = rs.getString("password");
                String name = rs.getString("name");
                String surname = rs.getString("surname");
                String phoneNumber = rs.getString("phoneno");
                String userType = rs.getString("user_type");
                UserType type = userTypeHashMap.get(userType);
                Address a = getUserAddress(uid);
                // TODO: add membership points to db, discuss necessity of affiliated branch
                u = new User(uid, username, password, name, surname, phoneNumber, a, type,
                        0, null);
            }
            rs.close();
            stmt.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return u;
    }

    //TODO: check user type and update address
    public void updateUserFields(User user) {
        long id = user.getUID();
        String username = user.getUsername();
        String password = user.getPassword();
        String name = user.getName();
        String surname = user.getSurname();
        String phoneNumber = user.getPhoneNumber();
        int membershipPoint = user.getMembershipPoints();

        StringBuffer statement = new StringBuffer(String.format(
                "username = '%s'," +
                        "password = '%s'," +
                        "name ='%s'," +
                        "surname ='%s'," +
                        "phoneNumber = '%s'",
                username,
                password,
                name,
                surname,
                phoneNumber));


        if (user.getUserType() == UserType.CUSTOMER) {
            statement.append(String.format(Locale.CANADA, ", membership_point=%d", membershipPoint));
        }
        statement.append(String.format(" WHERE user_id=%d", id));
        updateColumnValues(primaryTable, statement.toString());

    }

    public void removeUserData(User user) {
        long user_id = user.getUID();
        removeFromDb(primaryTable, String.format("user_id=%d", user_id));
    }

    public Address getUserAddress(long uid) {
        Address a = null;
        try {
            Statement stmt = connection.createStatement();
            String query = String.format("SELECT * FROM %s natural join Address WHERE user_id=%d", primaryTable, uid);
            ResultSet rs = stmt.executeQuery(query);

            while (rs.next()) {
                String street = rs.getString("streetname");
                int houseNr = rs.getInt("housenr");
                String postalCode = rs.getString("postalcode");
                String city = getCityFromPostalCode(postalCode);
                a = new Address(city, street, postalCode, houseNr);

            }
            rs.close();
            stmt.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return a;
    }
}
