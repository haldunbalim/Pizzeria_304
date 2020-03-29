package Service;

import Model.User;
import Model.UserType;

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
    public AuthStatus signUp(String email, String password){
        return AuthStatus.AUTH_FAILED;
    }

    // TODO: Login function
    // returns info about Authentication Status
    // record currentUser as a property of the class
    public AuthStatus login(String username, String password) {
        currentUser = new User(14132, username, password, "", "", "", UserType.MANAGER);
        return AuthStatus.AUTH_SUCCESSFUL;
    }


    public User getCurrentUser() {
        return currentUser;
    }



}
