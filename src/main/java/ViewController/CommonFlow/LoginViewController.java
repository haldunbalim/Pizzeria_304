package ViewController.CommonFlow;

import Model.UserType;
import Reusable.PlaceholderFocusListener;
import Service.AuthStatus;
import Service.AuthenticationManager;
import Service.Coordinator;
import Service.ScreenEnum;
import ViewController.AbstractViewController;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class LoginViewController extends AbstractViewController {

    private static LoginViewController instance = new LoginViewController();
    protected JPanel mainPanel;
    private JTextField usernameTextField;
    private JButton loginButton;
    private JButton registerButton;
    private JLabel errorLabel;
    private JTextField passwordTextField;

    private LoginViewController() {
        configureUI();
        addUIFunctionality();
    }

    public void configureUI() {
        errorLabel.setText("");
        usernameTextField.addFocusListener(new PlaceholderFocusListener(usernameTextField, "Enter Username"));
        passwordTextField.addFocusListener(new PlaceholderFocusListener(passwordTextField, "Enter Password"));
    }

    public static LoginViewController getInstance() {
        return instance;
    }


    private void addUIFunctionality() {
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = usernameTextField.getText();
                String password = passwordTextField.getText();

                if (checkIfFieldsInvalid(username, password))
                    return;

                AuthStatus authStatus = AuthenticationManager.getInstance().login(username, password);

                switch (authStatus) {
                    case AUTH_FAILED:
                        showError("Auth failed wrong username or pwd");
                        break;
                    case AUTH_SUCCESSFUL:
                        UserType userType = AuthenticationManager.getInstance().getCurrentUser().getUserType();
                        switch (userType) {
                            case CUSTOMER:
                                Coordinator.getInstance().openScreen(ScreenEnum.CUSTOMER_TABS);
                                break;
                            case MANAGER:
                                Coordinator.getInstance().openScreen(ScreenEnum.MANAGER_TABS);
                                break;
                            case EMPLOYEE:
                                Coordinator.getInstance().openScreen(ScreenEnum.EMPLOYEE_TABS);
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

                if (checkIfFieldsInvalid(username, password))
                    return;

                AuthStatus authStatus = AuthenticationManager.getInstance().signUp(username, password);
                switch (authStatus) {
                    case AUTH_FAILED:
                        showError("Auth failed there is a user with this username");
                        break;
                    case AUTH_SUCCESSFUL:
                        Coordinator.getInstance().openScreen(ScreenEnum.SET_PROFILE_PAGE);
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

    private boolean checkIfFieldsInvalid(String username, String password) {
        if (username.length() < 3) {
            showError("Invalid Username");
            return true;
        }

        if (password.length() < 3) {
            showError("Invalid Password");
            return true;
        }

        return false;
    }


    public JPanel getMainPanel() {
        return mainPanel;
    }
}
