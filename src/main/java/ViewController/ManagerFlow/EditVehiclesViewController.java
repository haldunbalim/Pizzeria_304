package ViewController.ManagerFlow;

import DataSource.VehicleDataSource;
import Model.Vehicle;
import Reusable.ButtonRenderer;
import Reusable.PlaceholderFocusListener;
import ViewController.AbstractTableViewController;
import ViewController.MyAbstractTableModel;
import ViewModel.VehicleViewModel;

import javax.swing.*;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class EditVehiclesViewController extends AbstractTableViewController implements TableModelListener {
    private static EditVehiclesViewController instance = new EditVehiclesViewController();
    protected JPanel mainPanel;
    private ArrayList<Vehicle> vehicles;
    private VehicleDataSource dataSource = VehicleDataSource.getInstance();

    private EditVehiclesViewController() {
        vehicles = dataSource.getVehicles();
        table.getModel().addTableModelListener(this);
    }

    public static EditVehiclesViewController getInstance() {
        return instance;
    }

    public void configureUI() {
        configureTable();
        configureAddPanel();
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
                vehicles.add(vehicle);
                ((EditVehiclesTableModel) tableModel).add(vehicle);
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

    @Override
    public void tableChanged(TableModelEvent e) {
        int row = e.getFirstRow();
        int column = e.getColumn();
        //  Remove row calls e.getColumn() = -1 ??
        if (column < 0)
            return;
        String columnName = tableModel.getColumnName(column);

        if (columnName.equals("")) {
            Vehicle removed = vehicles.remove(row);
            tableModel.remove(row);
            dataSource.removeVehicleData(removed);
            return;
        }
        dataSource.updateVehicleData(vehicles.get(row));
    }

    private class EditVehiclesTableModel extends MyAbstractTableModel {
        public EditVehiclesTableModel(ArrayList<Vehicle> data) {
            data.forEach(vehicle -> this.data.add(new VehicleViewModel(vehicle)));
            this.columnNames = VehicleViewModel.columnNames;
        }

        public Class getColumnClass(int col) {
            return VehicleViewModel.getColumnClassAt(col);
        }
        public void add(Vehicle vehicle) {
            this.data.add(new VehicleViewModel(vehicle));
            this.fireTableDataChanged();
        }
    }

}


