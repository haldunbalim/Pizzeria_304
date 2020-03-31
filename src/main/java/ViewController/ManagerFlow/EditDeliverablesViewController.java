package ViewController.ManagerFlow;

import DataSource.DeliverableDataSource;
import Model.Deliverable;
import Reusable.ButtonRenderer;
import Reusable.PlaceholderFocusListener;
import ViewController.AbstractTableViewController;
import ViewController.MyAbstractTableModel;
import ViewModel.DeliverableEditableViewModel;

import javax.swing.*;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class EditDeliverablesViewController extends AbstractTableViewController implements TableModelListener {
    private static EditDeliverablesViewController instance = new EditDeliverablesViewController();
    private DeliverableDataSource dataSource = DeliverableDataSource.getInstance();
    private ArrayList<Deliverable> deliverables;
    protected JPanel mainPanel;

    private EditDeliverablesViewController() {
        deliverables = dataSource.getDeliverables();
        configureUI();
        table.getModel().addTableModelListener(this);
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

    @Override
    public void tableChanged(TableModelEvent e) {
        int row = e.getFirstRow();
        int column = e.getColumn();
        //  Remove row calls e.getColumn() = -1 ??
        if (column < 0)
            return;
        String columnName = tableModel.getColumnName(column);
        // Object data = tableModel.getValueAt(row, column); may be needed
        if (columnName.equals("")) {
            Deliverable removed = deliverables.remove(row);
            tableModel.remove(row);
            dataSource.removeDeliverableData(removed);
            return;
        }
        dataSource.updateDeliverableData(deliverables.get(row));
    }

    public JPanel getMainPanel() {
        return mainPanel;
    }

    private class EditDeliverablesTableModel extends MyAbstractTableModel {
        public EditDeliverablesTableModel(ArrayList<Deliverable> data) {
            data.forEach(deliverable -> this.data.add(new DeliverableEditableViewModel(deliverable)));
            this.columnNames = DeliverableEditableViewModel.columnNames;
        }
        public boolean isCellEditable(int row, int col) {
            return true;
        }

        public void add(Deliverable deliverable) {
            this.data.add(new DeliverableEditableViewModel(deliverable));
            this.fireTableDataChanged();
        }
    }

}


