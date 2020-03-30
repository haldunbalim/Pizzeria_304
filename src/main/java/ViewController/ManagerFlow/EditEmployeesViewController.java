package ViewController.ManagerFlow;

import DataSource.EmployeesDataSource;
import Model.User;
import Reusable.ButtonRenderer;
import Reusable.PlaceholderFocusListener;
import ViewController.AbstractTableViewController;
import ViewController.MyAbstractTableModel;
import ViewModel.UserEditableViewModel;

import javax.swing.*;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class EditEmployeesViewController extends AbstractTableViewController implements TableModelListener {
    private static EditEmployeesViewController instance = new EditEmployeesViewController();
    private JPanel mainPanel;
    private EmployeesDataSource dataSource = EmployeesDataSource.getInstance();
    private ArrayList<User> employees;

    private EditEmployeesViewController() {
        employees = dataSource.getEmployees();
        table.getModel().addTableModelListener(this);
    }

    public static EditEmployeesViewController getInstance() {
        return instance;
    }

    public void configureUI() {
        configureTable();
        configureAddPanel();
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
                dataSource.createNewUser(username, password);
            }
        });


        addNewPanel.add(usernameField);
        addNewPanel.add(passwordField);
        addNewPanel.add(addButton);

        mainPanel.add(addNewPanel, BorderLayout.SOUTH);
    }


    @Override
    public void tableChanged(TableModelEvent e) {
        int row = e.getFirstRow();
        int column = e.getColumn();
        //  Remove row calls e.getColumn() = -1 ??
        if (column < 0)
            return;

        User removed = employees.remove(row);
        tableModel.remove(row);
        dataSource.removeUserData(removed);
    }

    private void configureTable() {
        tableModel = new EditEmployeesTableModel(employees);
        table = new JTable(tableModel);
        table.getColumn("").setCellRenderer(new ButtonRenderer("Remove"));

        addTable();
    }

    private class EditEmployeesTableModel extends MyAbstractTableModel {
        EditEmployeesTableModel(ArrayList<User> data) {
            data.forEach(user -> this.data.add(new UserEditableViewModel(user)));
            this.columnNames = UserEditableViewModel.columnNames;
        }
        public boolean isCellEditable(int row, int col) {
            return col == getColumnCount() - 1;
        }
        public void setValueAt(Object value, int row, int col) {
            fireTableCellUpdated(row, col);
        }
    }

}



