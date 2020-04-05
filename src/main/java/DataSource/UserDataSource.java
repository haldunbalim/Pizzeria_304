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
                String userType = rs.getString("user_type");
                UserType type = userTypeHashMap.get(userType);
                Address address = null;
                ;
                long bid;
                RestaurantBranch affiliatedBranch = null;
                if (type == UserType.EMPLOYEE || type == UserType.MANAGER) {
                    bid = EmployeesDataSource.getInstance().getBranchIdOfEmployee(uid);
                    affiliatedBranch = EmployeesDataSource.getInstance().getBranch(bid);
                } else {
                    bid = 455758951;
                    affiliatedBranch = EmployeesDataSource.getInstance().getBranch(bid);
                    address = getUserAddress(uid);
                }
                //bak
                // long bid = getAffiliatedBranchId(uid);
                //RestaurantBranch affiliatedBranch = getBranch(bid);
                int membershipPoints = 0;
                if (type == UserType.CUSTOMER)
                    // work around get this from customer table
                    // membershipPoints = rs.getInt("membershipPoints");
                    u = new User(uid, username, password, name, surname, phoneNumber, address, type,
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
        int membershipPoint = user.getMembershipPoints();

        StringBuffer statement = new StringBuffer(String.format(
                "username = '%s'," +
                        "password = '%s'," +
                        "name ='%s'," +
                        "surname ='%s'," +
                        "phoneNO = '%s'" +
                        "membershipPoins = %d",
                username,
                password,
                name,
                surname,
                phoneNumber,
                membershipPoint));

        statement.append(String.format(" WHERE user_id=%d", id));
        updateColumnValues(primaryTable, statement.toString());
    }

    public void removeUserData(User user) {
        long user_id = user.getUID();
        removeFromDb(primaryTable, String.format("user_id=%d", user_id));
    }

    public Address getAddressFromTable(String tableName, String identifier) {
        Address a = null;
        try {
            Statement stmt = connection.createStatement();
            String query = String.format("SELECT * FROM %s natural join Address WHERE %s", tableName, identifier);
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

    public Address getUserAddress(long uid) {
        return getAddressFromTable("Customer", "user_id=" + uid);
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
                Address a = UserDataSource.getInstance().getAddressFromTable("Branch", String.format("bid=%d", bid));
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
