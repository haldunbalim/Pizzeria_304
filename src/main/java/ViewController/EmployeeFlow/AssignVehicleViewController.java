package ViewController.EmployeeFlow;

import DataSource.OrdersDataSource;
import DataSource.VehicleDataSource;
import Model.OrderState;
import Model.Vehicle;
import Reusable.ButtonRenderer;
import ViewController.AbstractViewController;
import ViewModel.VehicleViewModel;

import javax.swing.*;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.AbstractTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class AssignVehicleViewController extends AbstractViewController implements TableModelListener {
    private static AssignVehicleViewController instance = new AssignVehicleViewController();
    private JPanel mainPanel;
    JTable table;
    AssignVehiclesTableModel tableModel;
    VehicleDataSource dataSource = VehicleDataSource.getInstance();
    private ArrayList<Vehicle> vehicles;

    private AssignVehicleViewController() {
        configureUI();
        configureCancelButton();
        mainPanel.setFocusable(true);
        table.getModel().addTableModelListener(this);
    }

    public static AssignVehicleViewController getInstance() {
        return instance;
    }

    public void configureUI() {
        vehicles = dataSource.getVehicles();
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

        JScrollPane scrollPane = new JScrollPane(table);
        table.setFillsViewportHeight(true);
        mainPanel.add(scrollPane, BorderLayout.CENTER);
        table.setRowHeight(50);
    }

    @Override
    public void tableChanged(TableModelEvent e) {
        int row = e.getFirstRow();
        Vehicle vehicle = vehicles.remove(row);
        EmployeeTabs.getInstance().setSelectedVehicle(vehicle);
        OrdersDataSource.getInstance().changeOrderState(EmployeeTabs.getInstance().getSelectedOrder(), vehicle, OrderState.IN_DELIVERY);
        EmployeeTabs.getInstance().openFinishDelivery();
    }

    public JPanel getMainPanel() {
        return mainPanel;
    }

    private class AssignVehiclesTableModel extends AbstractTableModel {
        private String[] columnNames;
        private ArrayList<VehicleViewModel> data = new ArrayList<>();

        public AssignVehiclesTableModel(ArrayList<Vehicle> data) {
            data.forEach(vehicle -> this.data.add(new VehicleViewModel(vehicle)));
            this.columnNames = VehicleViewModel.columnNames;
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
            return col == 3 ? Boolean.class : String.class;
        }

        public boolean isCellEditable(int row, int col) {
            return true;
        }

        public void setValueAt(Object value, int row, int col) {
            // this works here?
            AssignVehicleViewController.getInstance().tableChanged(new TableModelEvent(this, row, col));
        }

    }

}