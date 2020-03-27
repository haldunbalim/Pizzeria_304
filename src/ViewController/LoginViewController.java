package ViewController;


import Model.UserType;
import Service.AuthStatus;
import Service.AuthenticationManager;
import Service.Coordinator;
import Service.ScreenEnum;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoginViewController extends AbstractViewController {

    private static LoginViewController instance = new LoginViewController();;
    private JLabel labelUsername = new JLabel("Enter username: ");
    private JLabel labelPassword = new JLabel("Enter password: ");
    private JTextField textUsername = new JTextField(20);
    private JPasswordField fieldPassword = new JPasswordField(20);
    private JButton buttonLogin = new JButton("Login");
    private JButton buttonRegister = new JButton("Register");


    private LoginViewController(){
        initializeView();
        addUIFunctionality();
    }

    public static LoginViewController getInstance() {
        return instance;
    }

    private void addUIFunctionality(){
        buttonLogin.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = labelUsername.getText();
                String password = labelPassword.getText();

                //TODO: Show error to user if username or pwd is invalid
                if (username.length()<3){
                    System.out.println("Invalid Username");
                }

                if (password.length()<3){
                    System.out.println("Invalid Password");
                }

                AuthStatus authStatus = AuthenticationManager.getInstance().login(username,password);

                //TODO: Show error to user if sthg went wrong
                switch (authStatus){
                    case AUTH_FAILED:
                        System.out.println("Auth failed wrong username or pwd");
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
                        System.out.println("Auth failed wrong username or pwd");
                        break;
                }
            }
        });


        buttonRegister.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Coordinator.getInstance().openScreen(ScreenEnum.CUSTOMER_PROFILE);
            }
        });
    }

    private void initializeView(){
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.anchor = GridBagConstraints.WEST;
        constraints.insets = new Insets(10, 10, 10, 10);

        // add components to the panel
        constraints.gridx = 0;
        constraints.gridy = 0;
        add(labelUsername, constraints);

        constraints.gridx = 1;
        add(textUsername, constraints);

        constraints.gridx = 0;
        constraints.gridy = 1;
        add(labelPassword, constraints);

        constraints.gridx = 1;
        add(fieldPassword, constraints);

        constraints.gridx = 0;
        constraints.gridy = 2;
        constraints.gridwidth = 2;
        constraints.anchor = GridBagConstraints.CENTER;
        add(buttonLogin, constraints);

        // set border for the panel
        setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createEtchedBorder(), "Login Panel"));
    }


}
