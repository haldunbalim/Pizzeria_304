package DataSource;

import Model.Address;
import Model.RestaurantBranch;
import Model.User;
import Model.UserType;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;

public class UserDataSource extends AbstractDataSource {
    private static UserDataSource instance = new UserDataSource();
    private HashMap<String, UserType> userTypeHashMap = new HashMap<String, UserType>() {
        {
            put("customer", UserType.CUSTOMER);
            put("manager", UserType.MANAGER);
            put("employee", UserType.EMPLOYEE);
        }
    };

    private HashMap<UserType, String> userTypeHashMapReversed = new HashMap<UserType, String>() {
        {
            put(UserType.CUSTOMER, "customer");
            put(UserType.MANAGER, "manager");
            put(UserType.EMPLOYEE, "employee");
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
                UserType userType = userTypeHashMap.get(rs.getString("user_type"));
                Address address = getUserAddress(uid);
                long bid = getAffiliatedBranchId(uid);
                RestaurantBranch affiliatedBranch = getBranch(bid);
                int membershipPoints = 0;
                if (userType == UserType.CUSTOMER)
                    membershipPoints = rs.getInt("membershipPoints");
                u = new User(uid, username, password, name, surname, phoneNumber, address, userType,
                        membershipPoints, affiliatedBranch);
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

        StringBuffer statement = new StringBuffer(String.format(
                "username = '%s'," +
                        "password = '%s'," +
                        "name ='%s'," +
                        "surname ='%s'," +
                        "phoneNO = '%s'",
                username,
                password,
                name,
                surname,
                phoneNumber));

        statement.append(String.format(" WHERE user_id=%d", id));
        updateColumnValues(primaryTable, statement.toString());

    }

    public void removeUserData(User user) {
        long user_id = user.getUID();
        removeFromDb(primaryTable, String.format("user_id=%d", user_id));
    }

    private Address getAddressFromTable(String tableName, long id) {
        Address a = null;
        try {
            Statement stmt = connection.createStatement();
            String query = String.format("SELECT * FROM %s natural join Address WHERE user_id=%d", tableName, id);
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

    private Address getUserAddress(long uid) {
        return getAddressFromTable(primaryTable, uid);
    }


    private long getAffiliatedBranchId(long userId) {
        long bid = -1;
        try {
            Statement stmt = connection.createStatement();
            String query = String.format("SELECT bid FROM USERS natural join " +
                            "EMPLOYEEWORKSATBRANCH WHERE user_id=%d",
                    userId);
            ResultSet rs = stmt.executeQuery(query);

            while (rs.next()) {
                bid = rs.getInt("bid");
                break;
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return bid;
    }


    private RestaurantBranch getBranch(long bid) {
        RestaurantBranch branch = null;
        try {
            Statement stmt = connection.createStatement();
            String query = String.format("SELECT * FROM BRANCH natural join CityPostalCode WHERE bid=%d", bid);
            ResultSet rs = stmt.executeQuery(query);

            while (rs.next()) {
                String name = rs.getString("name");
                int mid = rs.getInt("mid");
                Address a = UserDataSource.getInstance().getAddressFromTable("Branch", bid);
                branch = new RestaurantBranch(bid, name, a);
            }

            stmt.close();
            rs.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return branch;
    }
}
