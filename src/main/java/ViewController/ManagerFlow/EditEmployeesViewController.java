package ViewController.ManagerFlow;

import DataSource.EmployeesDataSource;
import Model.User;
import Reusable.ButtonRenderer;
import Reusable.PlaceholderFocusListener;
import ViewController.AbstractTableViewController;
import ViewController.MyAbstractTableModel;
import ViewModel.UserEditableViewModel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class EditEmployeesViewController extends AbstractTableViewController {
    private static EditEmployeesViewController instance = new EditEmployeesViewController();
    private JPanel mainPanel;
    private EmployeesDataSource dataSource = EmployeesDataSource.getInstance();
    private ArrayList<User> employees;
    private JLabel errorLabel;

    private EditEmployeesViewController() {
        employees = dataSource.getEmployees();
        configureUI();
    }

    public static EditEmployeesViewController getInstance() {
        return instance;
    }

    private void configureUI() {
        configureTable();
        configureAddPanel();
        configureErrorLabel();
    }

    private void configureErrorLabel() {
        errorLabel = new JLabel();
        mainPanel.add(errorLabel, BorderLayout.NORTH);
    }

    private void showError(String st) {
        errorLabel.setText(st);
        errorLabel.setVisible(true);
    }

    private void configureAddPanel() {
        JPanel addNewPanel = new JPanel();
        addNewPanel.setLayout(new BoxLayout(addNewPanel, BoxLayout.X_AXIS));

        JTextField usernameField = new JTextField();
        usernameField.addFocusListener(new PlaceholderFocusListener(usernameField, "Username"));

        JTextField passwordField = new JTextField();
        passwordField.addFocusListener(new PlaceholderFocusListener(passwordField, "Password"));

        JButton addButton = new JButton("Add");
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = usernameField.getText();
                String password = passwordField.getText();
                if (username.equals("Username") || password.equals("Password")) {
                    return;
                }
                if (dataSource.createNewUser(username, password) == null) {
                    showError("User with " + username + " already exists");
                }
            }
        });


        addNewPanel.add(usernameField);
        addNewPanel.add(passwordField);
        addNewPanel.add(addButton);

        mainPanel.add(addNewPanel, BorderLayout.SOUTH);
    }


    private void configureTable() {
        tableModel = new EditEmployeesTableModel(employees);
        table = new JTable(tableModel);
        table.getColumn("").setCellRenderer(new ButtonRenderer("Remove"));

        addTable();
    }

    public JPanel getMainPanel() {
        return mainPanel;
    }

    private class EditEmployeesTableModel extends MyAbstractTableModel {
        EditEmployeesTableModel(ArrayList<User> data) {
            data.forEach(user -> this.data.add(new UserEditableViewModel(user)));
            this.columnNames = UserEditableViewModel.columnNames;
        }
        public boolean isCellEditable(int row, int col) {
            return col == getColumnCount() - 1;
        }

        @Override
        public void setValueAt(Object value, int row, int col) {
            if (col == getColumnCount() - 1) {
                User removed = employees.remove(row);
                tableModel.remove(row);
                dataSource.removeUserData(removed);
                fireTableCellUpdated(row, col);
            }
        }
    }

}



