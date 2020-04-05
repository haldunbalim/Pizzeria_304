package ViewController.EmployeeFlow;

import DataSource.OrdersDataSource;
import DataSource.VehicleDataSource;
import Model.OrderState;
import Model.Vehicle;
import Reusable.ButtonRenderer;
import ViewController.AbstractTableViewController;
import ViewController.MyAbstractTableModel;
import ViewModel.VehicleViewModel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class AssignVehicleViewController extends AbstractTableViewController {
    private static AssignVehicleViewController instance = new AssignVehicleViewController();
    private JPanel mainPanel;
    private AssignVehiclesTableModel tableModel;
    private VehicleDataSource dataSource = VehicleDataSource.getInstance();
    private ArrayList<Vehicle> vehicles;

    private AssignVehicleViewController() {
        configureUI();
        configureCancelButton();
    }

    public static AssignVehicleViewController getInstance() {
        return instance;
    }

    public void configureUI() {
        vehicles = dataSource.getVehiclesOfCurrentBranch();
        configureTable();
    }

    private void configureCancelButton() {
        JButton cancelButton = new JButton("Cancel");
        mainPanel.add(cancelButton, BorderLayout.SOUTH);
        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                EmployeeTabs.getInstance().setSelectedOrder(null);
                EmployeeTabs.getInstance().openAssignOrder();
            }
        });
    }

    private void configureTable() {
        tableModel = new AssignVehiclesTableModel(vehicles);
        table = new JTable(tableModel);
        table.getColumn("").setCellRenderer(new ButtonRenderer("Assign"));

        addTable();
        table.setRowHeight(50);
    }

    public JPanel getMainPanel() {
        return mainPanel;
    }

    private class AssignVehiclesTableModel extends MyAbstractTableModel {
        public AssignVehiclesTableModel(ArrayList<Vehicle> data) {
            data.forEach(vehicle -> this.data.add(new VehicleViewModel(vehicle)));
            this.columnNames = VehicleViewModel.columnNames;
        }

        public boolean isCellEditable(int row, int col) {
            return true;
        }

        @Override
        public void setValueAt(Object value, int row, int col) {
            Vehicle vehicle = vehicles.remove(row);
            EmployeeTabs.getInstance().setSelectedVehicle(vehicle);
            OrdersDataSource.getInstance().changeOrderState(EmployeeTabs.getInstance().getSelectedOrder(), vehicle, OrderState.IN_DELIVERY);
            EmployeeTabs.getInstance().openFinishDelivery();
            fireTableRowsUpdated(row, col);
        }

        public Class getColumnClass(int col) {
            return VehicleViewModel.getColumnClassAt(col);
        }

    }

}
