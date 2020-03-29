package DataSource;

import Model.User;
import Model.UserType;

import java.util.ArrayList;


// TODO: Discuss how users should be added
public class EmployeesDataSource extends AbstractDataSource {
    private static EmployeesDataSource instance = new EmployeesDataSource();

    private EmployeesDataSource() {
    }

    public static EmployeesDataSource getInstance() {
        return instance;
    }


    public ArrayList<User> getEmployees() {
        ArrayList<User> list = new ArrayList<>();
        list.add(new User(132413242, "kjfndsa", "jnfdsnk", "newname", "surname", "phoneNumber", UserType.EMPLOYEE));
        list.add(new User(132413242, "kjfndsa", "jnfdsnk", "newname", "surname", "phoneNumber", UserType.EMPLOYEE));
        list.add(new User(132413242, "kjfndsa", "jnfdsnk", "newname", "surname", "phoneNumber", UserType.EMPLOYEE));
        list.add(new User(132413242, "kjfndsa", "jnfdsnk", "newname", "surname", "phoneNumber", UserType.EMPLOYEE));
        list.add(new User(132413242, "kjfndsa", "jnfdsnk", "newname", "surname", "phoneNumber", UserType.EMPLOYEE));
        list.add(new User(132413242, "kjfndsa", "jnfdsnk", "newname", "surname", "phoneNumber", UserType.EMPLOYEE));
        list.add(new User(132413242, "kjfndsa", "jnfdsnk", "newname", "surname", "phoneNumber", UserType.EMPLOYEE));
        list.add(new User(132413242, "kjfndsa", "jnfdsnk", "newname", "surname", "phoneNumber", UserType.EMPLOYEE));
        list.add(new User(132413242, "kjfndsa", "jnfdsnk", "newname", "surname", "phoneNumber", UserType.EMPLOYEE));
        list.add(new User(132413242, "kjfndsa", "jnfdsnk", "newname", "surname", "phoneNumber", UserType.EMPLOYEE));
        return list;
    }

    // TODO: Check user with such username does not exist
    public User createNewUser(String username, String password) {
        return new User(132413242, username, password, "NewUserName", "surname", "phoneNumber", UserType.EMPLOYEE);
    }

    public void removeUserData(User user) {

    }
}
