package DataSource;

import Model.Address;
import Model.RestaurantBranch;
import Model.User;
import Service.AuthenticationManager;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;


// TODO: Discuss how users should be added
public class EmployeesDataSource extends AbstractDataSource {
    private static EmployeesDataSource instance = new EmployeesDataSource();
    private static UserDataSource userDataSource = UserDataSource.getInstance();

    private EmployeesDataSource() {
        primaryTable = "Users";
    }

    public static EmployeesDataSource getInstance() {
        return instance;
    }


    public ArrayList<User> getEmployees() {

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
        Address address = new Address();
        return list;
    }

    // TODO: Check user with such username does not exist
    public User createNewUser(String username, String password) {
        return null;
    }

    public void removeUserData(User user) {
        long user_id = user.getUID();
        removeFromDb(primaryTable, String.format("user_id=%d", user_id));
    }

    public RestaurantBranch getBranch(long bid) {
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
            System.out.println(e.getMessage());
        }
        return bid;
    }

    public long getCurrentBranchId() {
        return getBranchIdOfEmployee(AuthenticationManager.getInstance().getCurrentUser().getUID());
    }
}
