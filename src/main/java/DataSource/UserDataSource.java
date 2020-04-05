package DataSource;

import Model.Address;
import Model.RestaurantBranch;
import Model.User;
import Model.UserType;
import database.DataBaseCredentials;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collections;
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
                    address = getUserAddress(uid);
                    bid = getAffiliatedBranchId(address.getCity());
                    affiliatedBranch = EmployeesDataSource.getInstance().getBranch(bid);
                }
                int membershipPoints = 0;
                if (type == UserType.CUSTOMER)
                    membershipPoints = getCustomerPoints(uid);
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


        String statementForUser = String.format(
                "username = '%s'," +
                        "password = '%s'," +
                        "name ='%s'," +
                        "surname ='%s'," +
                        "phoneNO = '%s', " +
                        "WHERE user_id= %d",
                username,
                password,
                name,
                surname,
                phoneNumber,
                id
        );
        DataBaseCredentials.OperationResult res1 = updateColumnValues(primaryTable, statementForUser);

        // DataBaseCredentials.OperationResult resAddress = DataBaseCredentials.OperationResult.updated;
        if (user.getUserType() == UserType.CUSTOMER) {
            Address a = user.getAddress();
            String statementForCity = String.format(
                    "City='%s'," +
                            "POSTALCODE='%s'",
                    a.getCity(),
                    a.getPostalCode()
            );

            DataBaseCredentials.OperationResult resCity = updateColumnValues("CityPostalCode", statementForCity);
            // if the new postal code is not in db insert it
            if (resCity == DataBaseCredentials.OperationResult.noElementUpdated) {
                resCity = insertIntoDb("CityPostalCode", String.format("'%s', '%s'", a.getCity(), a.getPostalCode()));
            }
            if (resCity == DataBaseCredentials.OperationResult.updated || resCity == DataBaseCredentials.OperationResult.inserted) {
                String statementForAddress = String.format(
                        "ADDRESS_ID=%d," +
                                "STREETNAME='%s'," +
                                "HOUSENR=%d," +
                                "POSTALCODE=%d",
                        // TODO: get this when Address model is updated
                        1,//a.getId(),
                        a.getStreetName(),
                        a.getHouseNumber(),
                        a.getPostalCode()
                );

                DataBaseCredentials.OperationResult resAddress =
                        updateColumnValues("Address", statementForAddress);
            }
        }
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


    private long getAffiliatedBranchId(String city) {
        ArrayList<Long> availableBranches = getBranchIdsInCity(city);
        // select a branch randomly
        Collections.shuffle(availableBranches);
        Long bid = availableBranches.get(0);
        return bid;
    }


    /**
     * @param city
     * @return branch ids in the given city
     */
    protected ArrayList<Long> getBranchIdsInCity(String city) {
        ArrayList<Long> branchIds = new ArrayList<>();
        try {
            Statement stmt = connection.createStatement();
            String query = String.format("select bid, c from BRANCH natural join (select CITY c " +
                    "from ADDRESS natural join CITYPOSTALCODE) where c='%s' group by bid, c", city);
            ResultSet rs = stmt.executeQuery(query);

            while (rs.next()) {
                long bid = rs.getLong("bid");
                branchIds.add(bid);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return branchIds;
    }

    /**
     * @param uid
     * @return points of a customer
     */
    protected int getCustomerPoints(long uid) {
        int points = 0;
        try {
            Statement stmt = connection.createStatement();
            String query = String.format("SELECT points from Customer where user_id=%d", uid);
            ResultSet rs = stmt.executeQuery(query);

            while (rs.next()) {
                points = rs.getInt("points");
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return points;
    }
}
