package DataSource;

import Model.Address;
import Model.RestaurantBranch;
import Model.User;
import database.DataBaseCredentials;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;


public class EmployeesDataSource extends AbstractDataSource {
    private static EmployeesDataSource instance = new EmployeesDataSource();
    private static UserDataSource userDataSource = UserDataSource.getInstance();

    private EmployeesDataSource() {
        primaryTable = "Users";
    }

    public static EmployeesDataSource getInstance() {
        return instance;
    }


    /**
     * @return list of employee User instances who work at the current user's branch
     */
    public ArrayList<User> getEmployeesInCurrentBranch() {

        ArrayList<User> list = new ArrayList<>();
        try {
            Statement stmt = connection.createStatement();
            String query = String.format("SELECT user_id FROM USERS natural join " +
                            "EMPLOYEEWORKSATBRANCH WHERE user_type='%s' and bid=%d", "employee",
                    getCurrentBranchId());
            ResultSet rs = stmt.executeQuery(query);

            while (rs.next()) {
                list.add(userDataSource.getUser(rs.getLong("user_id")));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return list;
    }

    /**
     * adds a new employee to the db
     *
     * @param username
     * @param password
     * @return User instance if the user can be added to the both Users and EmployeeWorksAtBranch tables
     */
    public User createNewUser(String username, String password) {
        User u = null;
        long newId = getNextIdLong(primaryTable, "user_id");
        String statement = String.format("%d, '%s', '%s', '%s', null, '%s', '%s'",
                newId, "name", "surname", username, password, "employee");
        String statementForBranch = String.format("%d, %d", AuthenticationManager.getInstance().getCurrentUser().getAffiliatedBranch().getBid(),
                newId);
        DataBaseCredentials.OperationResult res = insertIntoDb(primaryTable, statement);
        DataBaseCredentials.OperationResult res2 = insertIntoDb("EmployeeWorksAtBranch", statementForBranch);

        if (res == DataBaseCredentials.OperationResult.inserted && res2 == DataBaseCredentials.OperationResult.inserted) {
            u = UserDataSource.getInstance().getUser(newId);
        } else if (res2 == DataBaseCredentials.OperationResult.insertionFailed) {
            System.out.println("Employee addition to branch failed");
        } else {

        }
        return u;
    }

    public void removeUserData(User user) {
        long user_id = user.getUID();
        removeFromDb(primaryTable, String.format("user_id=%d", user_id));
    }

    // TODO link UI edit employee data to this method
    public void editUserData(User user) {
        String updateStatement = String.format("name='%s', surname='%s', phoneno='%s' WHERE user_id=%d",
                user.getName(),
                user.getSurname(),
                user.getPhoneNumber(),
                user.getUID());
        updateColumnValues(primaryTable, updateStatement);
    }

    /**
     * @param bid
     * @return RestaurantBranch instance
     */
    public RestaurantBranch getBranch(long bid) {
        RestaurantBranch branch = null;
        try {
            Statement stmt = connection.createStatement();
            String query = String.format("SELECT * FROM BRANCH natural join (select * from ADDRESS " +
                    "natural join CityPostalCode) WHERE bid=%d", bid);
            ResultSet rs = stmt.executeQuery(query);

            while (rs.next()) {
                String name = rs.getString("name");
                Address a = UserDataSource.getInstance().getAddressFromTable("Branch", "bid=" + bid);
                branch = new RestaurantBranch(bid, name, a);
            }

            stmt.close();
            rs.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return branch;
    }

    /**
     * @param userId
     * @return affiliated bid of the Employee
     */
    public long getBranchIdOfEmployee(long userId) {
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
            System.out.println(e.getMessage() + "problem in finding bid");
        }
        return bid;
    }

    /**
     * @return currentUser's branchId
     */
    public long getCurrentBranchId() {
        return getBranchIdOfEmployee(AuthenticationManager.getInstance().getCurrentUser().getUID());
    }
}
