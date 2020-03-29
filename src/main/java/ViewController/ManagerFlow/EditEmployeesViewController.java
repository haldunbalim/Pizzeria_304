package ViewController.ManagerFlow;

import DataSource.EmployeesDataSource;
import Model.User;
import Reusable.ButtonRenderer;
import Reusable.PlaceholderFocusListener;
import ViewController.AbstractViewController;
import ViewModel.UserEditableViewModel;

import javax.swing.*;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.AbstractTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class EditEmployeesViewController extends AbstractViewController implements TableModelListener {
    private static EditEmployeesViewController instance = new EditEmployeesViewController();
    private JPanel mainPanel;
    private JTable table;
    private EditEmployeesTableModel tableModel;
    private EmployeesDataSource dataSource = EmployeesDataSource.getInstance();
    private ArrayList<User> employees;

    private EditEmployeesViewController() {
        employees = dataSource.getEmployees();
        configureTable();
        configureUI();
        mainPanel.setFocusable(true);
        table.getModel().addTableModelListener(this);
    }

    public static EditEmployeesViewController getInstance() {
        return instance;
    }

    private void configureUI() {
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


    private void configureTable() {
        tableModel = new EditEmployeesTableModel(employees);
        table = new JTable(tableModel);
        table.getColumn("").setCellRenderer(new ButtonRenderer());

        JScrollPane scrollPane = new JScrollPane(table);
        table.setFillsViewportHeight(true);
        mainPanel.add(scrollPane, BorderLayout.CENTER);
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

    public JPanel getMainPanel() {
        return mainPanel;
    }

    private class EditEmployeesTableModel extends AbstractTableModel {
        private String[] columnNames;
        private ArrayList<UserEditableViewModel> data = new ArrayList<>();

        EditEmployeesTableModel(ArrayList<User> data) {
            data.forEach(user -> this.data.add(new UserEditableViewModel(user)));
            this.columnNames = UserEditableViewModel.columnNames;
        }

        public int getColumnCount() {
            return columnNames.length;
        }

        public int getRowCount() {
            if (data.size() <= 0) {
                return 0;
            } else {
                return data.size();
            }
        }

        public String getColumnName(int col) {
            return columnNames[col];
        }

        public Object getValueAt(int row, int col) {
            return data.get(row).getColumnView(col);
        }

        public Class getColumnClass(int col) {
            return col != getColumnCount() - 1 ? String.class : Boolean.class;
        }

        public boolean isCellEditable(int row, int col) {
            return col == getColumnCount() - 1;
        }

        public void remove(int row) {
            this.data.remove(row);
            this.fireTableRowsDeleted(row, row);
        }

        public void setValueAt(Object value, int row, int col) {
            fireTableCellUpdated(row, col);
        }
    }

}



