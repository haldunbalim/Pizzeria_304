package DataSource;

import Model.User;

public class UserDataSource {
    private static UserDataSource instance = new UserDataSource();

    private UserDataSource() {
    }

    public static UserDataSource getInstance() {
        return instance;
    }

    //TODO: complete
    public void updateUserFields(User user) {
    }
}
