package DataSource;

import Model.User;
import Model.UserType;
import Service.AuthStatus;
import Service.AuthenticationManager;

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


    public ArrayList<User> getEmployeesInCurrentBranch() {

        ArrayList<User> list = new ArrayList<>();
        try {
            Statement stmt = connection.createStatement();
            String query = String.format("SELECT user_id FROM USERS natural join " +
                            "EMPLOYEEWORKSATBRANCH WHERE user_type='%s' and bid=%d", "employee",
                    AuthenticationManager.getInstance().getCurrentUser().getAffiliatedBranch().getBid());
            ResultSet rs = stmt.executeQuery(query);

            while (rs.next()) {
                list.add(userDataSource.getUser(rs.getLong("user_id")));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return list;
    }


    public User createNewUser(String username, String password) {
        if (AuthenticationManager.getInstance().signUp(username, password, UserType.EMPLOYEE) == AuthStatus.NEW_REGISTRATION) {
            return AuthenticationManager.getInstance().getCurrentUser();
        }
        return null;
    }
}
