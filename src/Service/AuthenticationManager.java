package Service;

import Model.User;

public class AuthenticationManager {

    private static AuthenticationManager instance = new AuthenticationManager();
    private User currentUser;

    public static AuthenticationManager getInstance() {
        return instance;
    }

    private AuthenticationManager() {
    }


    // TODO: SignUp function
    // returns info about Authentication Status
    // call login after signUp is successfully complete
    public AuthStatus signUp(String username, String password) {

        return AuthStatus.AUTH_FAILED;
    }

    // TODO: Login function
    // returns info about Authentication Status
    // record currentUser as a property of the class
    public AuthStatus login(String username, String password) {
        currentUser = new User(username, password);
        return AuthStatus.AUTH_FAILED;
    }


    public User getCurrentUser() {
        return currentUser;
    }



}
