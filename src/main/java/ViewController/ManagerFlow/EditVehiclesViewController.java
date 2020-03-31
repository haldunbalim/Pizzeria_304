package ViewController.ManagerFlow;

import DataSource.VehicleDataSource;
import Model.Vehicle;
import Reusable.ButtonRenderer;
import Reusable.PlaceholderFocusListener;
import ViewController.AbstractTableViewController;
import ViewController.MyAbstractTableModel;
import ViewModel.VehicleViewModel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class EditVehiclesViewController extends AbstractTableViewController {
    private static EditVehiclesViewController instance = new EditVehiclesViewController();
    protected JPanel mainPanel;
    private ArrayList<Vehicle> vehicles;
    private VehicleDataSource dataSource = VehicleDataSource.getInstance();
    JLabel errorLabel;

    private EditVehiclesViewController() {
        vehicles = dataSource.getVehicles();
        configureUI();
    }

    public static EditVehiclesViewController getInstance() {
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

        JTextField licensePlateField = new JTextField();
        licensePlateField.addFocusListener(new PlaceholderFocusListener(licensePlateField, "License Plate"));

        JTextField brandField = new JTextField();
        brandField.addFocusListener(new PlaceholderFocusListener(brandField, "Brand"));

        JTextField modelField = new JTextField();
        modelField.addFocusListener(new PlaceholderFocusListener(modelField, "Model"));

        JButton addButton = new JButton("Add");
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String licensePlate = licensePlateField.getText();
                String brand = brandField.getText();
                String model = modelField.getText();
                if (licensePlate.equals("License Plate") || brand.equals("Brand") || model.equals("Model")) {
                    return;
                }
                Vehicle vehicle = dataSource.createNewVehicle(licensePlate, brand, model);
                if (vehicle == null) {
                    showError("License Plate already exists");
                } else {
                    vehicles.add(vehicle);
                    ((EditVehiclesTableModel) tableModel).add(vehicle);
                }
            }
        });


        addNewPanel.add(licensePlateField);
        addNewPanel.add(brandField);
        addNewPanel.add(modelField);
        addNewPanel.add(addButton);

        mainPanel.add(addNewPanel, BorderLayout.SOUTH);
    }


    private void configureTable() {
        tableModel = new EditVehiclesTableModel(vehicles);
        table = new JTable(tableModel);
        table.getColumn("").setCellRenderer(new ButtonRenderer("Remove"));

        addTable();
    }

    public JPanel getMainPanel() {
        return mainPanel;
    }

    private class EditVehiclesTableModel extends MyAbstractTableModel {
        public EditVehiclesTableModel(ArrayList<Vehicle> data) {
            data.forEach(vehicle -> this.data.add(new VehicleViewModel(vehicle)));
            this.columnNames = VehicleViewModel.columnNames;
        }

        public Class getColumnClass(int col) {
            return VehicleViewModel.getColumnClassAt(col);
        }

        @Override
        public boolean isCellEditable(int rowIndex, int columnIndex) {
            return columnIndex != 0;
        }

        public void add(Vehicle vehicle) {
            this.data.add(new VehicleViewModel(vehicle));
            this.fireTableDataChanged();
        }

        @Override
        public void setValueAt(Object value, int row, int col) {
            if (col == getColumnCount() - 1) {
                Vehicle removed = vehicles.remove(row);
                tableModel.remove(row);
                dataSource.removeVehicleData(removed);
            } else {
                this.data.get(row).setValueAt(col, value);
                dataSource.updateVehicleData(vehicles.get(row));
            }
            fireTableCellUpdated(row, col);
        }
    }

}


