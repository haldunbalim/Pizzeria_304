package ViewController;

import Model.UserType;
import Reusable.PlaceholderFocusListener;
import Service.*;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class LoginViewController extends AbstractViewController {

    private static LoginViewController instance = new LoginViewController();
    public JPanel mainPanel;
    private JTextField usernameTextField;
    private JButton loginButton;
    private JButton registerButton;
    private JLabel errorLabel;
    private JTextField passwordTextField;

    private LoginViewController(){
        errorLabel.setText("");
        mainPanel.setBounds(50, 50, Constants.SCREEN_WIDTH, Constants.SCREEN_HEIGHT);
        usernameTextField.addFocusListener(new PlaceholderFocusListener(usernameTextField, "Enter Username"));
        passwordTextField.addFocusListener(new PlaceholderFocusListener(passwordTextField, "Enter Password"));
        addUIFunctionality();
    }

    public static LoginViewController getInstance() {
        return instance;
    }


    private void addUIFunctionality(){
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = usernameTextField.getText();
                String password = passwordTextField.getText();

                if (!checkFieldValidity(username, password))
                    return;

                AuthStatus authStatus = AuthenticationManager.getInstance().login(username,password);

                switch (authStatus){
                    case AUTH_FAILED:
                        showError("Auth failed wrong username or pwd");
                        break;
                    case AUTH_SUCCESSFUL:
                        UserType userType = AuthenticationManager.getInstance().getCurrentUser().getUserType();
                        switch (userType){
                            case CUSTOMER:
                                Coordinator.getInstance().openScreen(ScreenEnum.ORDER);
                                break;
                            case MANAGER:
                                Coordinator.getInstance().openScreen(ScreenEnum.EMPLOYEE_LIST);
                                break;
                            case EMPLOYEE:
                                Coordinator.getInstance().openScreen(ScreenEnum.ORDER_ASSIGNMENT);
                                break;
                        }
                        break;
                    case CONNECTION_ERROR:
                        showError("Auth Failed connection error");
                        break;
                }
            }
        });

        registerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = usernameTextField.getText();
                String password = passwordTextField.getText();

                if (!checkFieldValidity(username, password))
                    return;

                AuthStatus authStatus = AuthenticationManager.getInstance().signUp(username, password);
                switch (authStatus) {
                    case AUTH_FAILED:
                        showError("Auth failed there is a user with this username");
                        break;
                    case AUTH_SUCCESSFUL:
                        Coordinator.getInstance().openScreen(ScreenEnum.CUSTOMER_PROFILE);
                        break;
                    case CONNECTION_ERROR:
                        showError("Auth Failed connection error");
                        break;
                }
            }
        });

    }


    private void showError(String errorMsg) {
        errorLabel.setText(errorMsg);
        errorLabel.setVisible(true);
    }

    private boolean checkFieldValidity(String username, String password) {
        if (username.length() < 3) {
            showError("Invalid Username");
            return false;
        }

        if (password.length() < 3) {
            showError("Invalid Password");
            return false;
        }

        return true;
    }


}
