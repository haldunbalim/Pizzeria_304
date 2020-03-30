package ViewController.ManagerFlow;

import DataSource.VehicleDataSource;
import Model.Vehicle;
import Reusable.ButtonRenderer;
import Reusable.PlaceholderFocusListener;
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

public class EditVehiclesViewController extends AbstractViewController implements TableModelListener {
    private static EditVehiclesViewController instance = new EditVehiclesViewController();
    private JPanel mainPanel;
    JTable table;
    EditVehiclesTableModel tableModel;
    VehicleDataSource dataSource = VehicleDataSource.getInstance();
    private ArrayList<Vehicle> vehicles;

    private EditVehiclesViewController() {
        vehicles = dataSource.getVehicles();
        configureUI();
        mainPanel.setFocusable(true);
        table.getModel().addTableModelListener(this);
    }

    public static EditVehiclesViewController getInstance() {
        return instance;
    }

    private void configureUI() {
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
                tableModel.add(vehicle);
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
        String columnName = tableModel.getColumnName(column);

        if (columnName.equals("")) {
            Vehicle removed = vehicles.remove(row);
            tableModel.remove(row);
            dataSource.removeVehicleData(removed);
            return;
        }
        dataSource.updateVehicleData(vehicles.get(row));
    }

    public JPanel getMainPanel() {
        return mainPanel;
    }

    private class EditVehiclesTableModel extends AbstractTableModel {
        private String[] columnNames;
        private ArrayList<VehicleViewModel> data = new ArrayList<>();

        public EditVehiclesTableModel(ArrayList<Vehicle> data) {
            data.forEach(vehicle -> this.data.add(new VehicleViewModel(vehicle)));
            this.columnNames = VehicleViewModel.columnNames;
        }

        public int getColumnCount() {
            return columnNames.length;
        }

        public int getRowCount() {
            return data.size();
        }

        public String getColumnName(int col) {
            return columnNames[col];
        }

        public Object getValueAt(int row, int col) {
            return data.get(row).getColumnView(col);
        }

        public Class getColumnClass(int col) {
            return VehicleViewModel.getColumnClassAt(col);
        }

        public boolean isCellEditable(int row, int col) {
            return true;
        }

        public void setValueAt(Object value, int row, int col) {
            switch (col) {
                case 0:
                    this.data.get(row).getModel().setLicensePlate((String) value);
                    break;
                case 1:
                    this.data.get(row).getModel().setBrand((String) value);
                    break;
                case 2:
                    this.data.get(row).getModel().setModel((String) value);
                    break;
            }
            fireTableCellUpdated(row, col);
        }

        public void remove(int row) {
            this.data.remove(row);
            this.fireTableRowsDeleted(row, row);
        }

        public void add(Vehicle vehicle) {
            this.data.add(new VehicleViewModel(vehicle));
            this.fireTableDataChanged();
        }
    }

}


