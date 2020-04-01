package ViewController.ManagerFlow;

import DataSource.DeliverableDataSource;
import Model.Deliverable;
import Reusable.ButtonRenderer;
import Reusable.PlaceholderFocusListener;
import ViewController.AbstractTableViewController;
import ViewController.MyAbstractTableModel;
import ViewModel.DeliverableManagerViewModel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class EditDeliverablesViewController extends AbstractTableViewController {
    private static EditDeliverablesViewController instance = new EditDeliverablesViewController();
    private DeliverableDataSource dataSource = DeliverableDataSource.getInstance();
    private ArrayList<Deliverable> deliverables;
    protected JPanel mainPanel;

    private EditDeliverablesViewController() {
        deliverables = dataSource.getDeliverables();
        configureUI();
    }

    public static EditDeliverablesViewController getInstance() {
        return instance;
    }

    private void configureUI() {
        configureTable();
        configureAddPanel();
    }

    private void configureAddPanel() {
        JPanel addNewPanel = new JPanel();
        addNewPanel.setLayout(new BoxLayout(addNewPanel, BoxLayout.X_AXIS));

        JTextField nameField = new JTextField();
        nameField.addFocusListener(new PlaceholderFocusListener(nameField, "Name"));

        JTextField priceField = new JTextField();
        priceField.addFocusListener(new PlaceholderFocusListener(priceField, "Price"));

        JButton addButton = new JButton("Add");
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String name = nameField.getText();
                if (name.equals("")) {
                    return;
                }
                try {
                    Double price = Double.parseDouble(priceField.getText());
                    Deliverable deliverable = dataSource.createNewDeliverable(name, price);
                    deliverables.add(deliverable);
                    ((EditDeliverablesTableModel) tableModel).add(deliverable);
                } catch (NumberFormatException exception) {
                    priceField.setText("");
                    return;
                }
            }
        });

        addNewPanel.add(nameField);
        addNewPanel.add(priceField);
        addNewPanel.add(addButton);

        mainPanel.add(addNewPanel, BorderLayout.SOUTH);
    }


    private void configureTable() {
        tableModel = new EditDeliverablesTableModel(deliverables);
        table = new JTable(tableModel);
        table.getColumn("").setCellRenderer(new ButtonRenderer("Remove"));
        addTable();
    }

    public JPanel getMainPanel() {
        return mainPanel;
    }

    private class EditDeliverablesTableModel extends MyAbstractTableModel {
        public EditDeliverablesTableModel(ArrayList<Deliverable> data) {
            data.forEach(deliverable -> this.data.add(new DeliverableManagerViewModel(deliverable)));
            this.columnNames = DeliverableManagerViewModel.columnNames;
        }

        public boolean isCellEditable(int row, int col) {
            return true;
        }

        public void add(Deliverable deliverable) {
            this.data.add(new DeliverableManagerViewModel(deliverable));
            this.fireTableDataChanged();
        }

        public Class getColumnClass(int col) {
            return DeliverableManagerViewModel.getColumnClassAt(col);
        }

        @Override
        public void setValueAt(Object value, int row, int col) {
            if (col == getColumnCount() - 1) {
                Deliverable removed = deliverables.remove(row);
                tableModel.remove(row);
                dataSource.removeDeliverableData(removed);
            } else {
                this.data.get(row).setValueAt(col, value);
                dataSource.updateDeliverableData(deliverables.get(row));
            }
            fireTableCellUpdated(row, col);
        }
    }

}


