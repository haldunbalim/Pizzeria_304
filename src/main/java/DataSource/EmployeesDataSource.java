package DataSource;

import Model.Address;
import Model.User;

import java.util.ArrayList;


// TODO: Discuss how users should be added
public class EmployeesDataSource extends AbstractDataSource {
    private static EmployeesDataSource instance = new EmployeesDataSource();

    private EmployeesDataSource() {
        primaryTable = "Users";
    }

    public static EmployeesDataSource getInstance() {
        return instance;
    }


    public ArrayList<User> getEmployees() {
        ArrayList<User> list = new ArrayList<>();
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
}
