package ViewController;

import DataSource.UserDataSource;
import Model.Address;
import Model.User;
import Service.AuthenticationManager;

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

    private SetProfilePage() {
        //TODO: add cities
        for (String city : cities) {
            cityComboBox.addItem(city);
        }
        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (checkIfAllFieldsAreValid()) {
                    User currentUser = AuthenticationManager.getInstance().getCurrentUser();
                    currentUser.setName(nameTextField.getText());
                    currentUser.setSurname(surnameTextField.getText());
                    currentUser.setAddress(new Address(cityComboBox.getSelectedItem().toString(), streetNameTextField.getText(), houseNumberTextField.getText(), Integer.parseInt(postalCodeTextField.getText())));
                    currentUser.setPhoneNumber(phoneNumberTextField.getText());
                    UserDataSource.getInstance().updateUserFields(currentUser);
                }
            }
        });
    }

    public static SetProfilePage getInstance() {
        return instance;
    }

    public JPanel getMainPanel() {
        return mainPanel;
    }

    //TODO: Implement this
    private boolean checkIfAllFieldsAreValid() {
        return true;
    }

    private void showError(String st) {
        errorLabel.setText(st);
        errorLabel.setVisible(true);
    }


}
