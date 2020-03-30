package ViewController;

import DataSource.UserDataSource;
import Model.Address;
import Model.User;
import Model.UserType;
import Service.AuthenticationManager;
import ViewController.CustomerFlow.CustomerTabs;
import ViewController.EmployeeFlow.EmployeeTabs;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SetProfilePage extends AbstractViewController {
    private static SetProfilePage instance = new SetProfilePage();
    private JPanel mainPanel;
    private JTextField nameTextField;
    private JTextField surnameTextField;
    private JTextField houseNumberTextField;
    private JTextField streetNameTextField;
    private JTextField phoneNumberTextField;
    private JTextField postalCodeTextField;
    private JButton saveButton;
    private JLabel errorLabel;
    private JComboBox cityComboBox;
    private String[] cities = {"Vancouver", "Toronto", "Montreal", "Istanbul"};
    User currentUser = AuthenticationManager.getInstance().getCurrentUser();

    private SetProfilePage() {
        for (String city : cities) {
            cityComboBox.addItem(city);
        }
        configureUI();
        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (checkIfAllFieldsAreValid()) {
                    currentUser.setName(nameTextField.getText());
                    currentUser.setSurname(surnameTextField.getText());
                    currentUser.setAddress(new Address(cityComboBox.getSelectedItem().toString(), streetNameTextField.getText(), postalCodeTextField.getText(), Integer.parseInt(houseNumberTextField.getText())));
                    currentUser.setPhoneNumber(phoneNumberTextField.getText());
                    UserDataSource.getInstance().updateUserFields(currentUser);
                    // TODO: Additional case if it was open for the first time to show tab
                    if (currentUser.getUserType() == UserType.CUSTOMER) {
                        CustomerTabs.getInstance().closeProfileEditingMode();
                    } else {
                        EmployeeTabs.getInstance().closeProfileEditingMode();
                    }
                }
            }
        });
    }

    public void configureUI() {
        configureTextFields();
        cityComboBox.setSelectedItem(currentUser.getAddress().getCity());
    }

    private void configureTextFields() {
        nameTextField.setText(currentUser.getName());
        surnameTextField.setText(currentUser.getSurname());
        streetNameTextField.setText(currentUser.getAddress().getStreetName());
        houseNumberTextField.setText("" + currentUser.getAddress().getHouseNumber());
        postalCodeTextField.setText(currentUser.getAddress().getPostalCode());
        phoneNumberTextField.setText(currentUser.getPhoneNumber());
    }

    public static SetProfilePage getInstance() {
        return instance;
    }

    public JPanel getMainPanel() {
        return mainPanel;
    }

    //TODO: Implement this or use formattedTextFields
    private boolean checkIfAllFieldsAreValid() {
        return true;
    }

    private void showError(String st) {
        errorLabel.setText(st);
        errorLabel.setVisible(true);
    }


}
