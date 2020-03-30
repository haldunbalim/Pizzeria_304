package ViewController;

import Model.User;
import Model.UserType;
import Service.AuthenticationManager;
import ViewController.CustomerFlow.CustomerTabs;
import ViewController.EmployeeFlow.EmployeeTabs;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ProfileViewController extends AbstractViewController {
    private static ProfileViewController instance = new ProfileViewController();
    private JPanel mainPanel;
    private JLabel headerLabel;
    private JLabel usernameLabel;
    private JLabel membershipLabel;
    private JLabel addressLabel;
    private JButton editButton;
    private JLabel phoneNumberLabel;

    private User currentUser = AuthenticationManager.getInstance().getCurrentUser();

    private ProfileViewController() {
        configureUI();
    }

    public static ProfileViewController getInstance() {
        return instance;
    }

    public void configureUI() {
        configureLabels();
        configureForUser();
    }

    private void configureForUser() {
        if (currentUser.getUserType() == UserType.EMPLOYEE) {
            membershipLabel.setText("Employee");
        } else {
            membershipLabel.setText(currentUser.getMembershipType() + " (" + currentUser.getMembershipPoints() + ")");
        }
    }

    private void configureLabels() {
        headerLabel.setText("Welcome, " + currentUser.getName() + " " + currentUser.getSurname());
        usernameLabel.setText(currentUser.getUsername());
        membershipLabel.setText(currentUser.getPhoneNumber());
        addressLabel.setText(currentUser.getAddress().toString());
        phoneNumberLabel.setText(currentUser.getPhoneNumber());
        editButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (currentUser.getUserType() == UserType.CUSTOMER) {
                    CustomerTabs.getInstance().openProfileEditingMode();
                } else {
                    EmployeeTabs.getInstance().openProfileEditingMode();
                }
            }
        });
    }

    @Override
    public JPanel getMainPanel() {
        return mainPanel;
    }


}
