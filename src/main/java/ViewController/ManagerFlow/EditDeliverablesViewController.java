package ViewController.ManagerFlow;

import DataSource.DeliverableDataSource;
import Model.Deliverable;
import Reusable.ButtonRenderer;
import Reusable.PlaceholderFocusListener;
import ViewController.AbstractViewController;
import ViewModel.DeliverableEditableViewModel;

import javax.swing.*;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.AbstractTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class EditDeliverablesViewController extends AbstractViewController implements TableModelListener {
    private static EditDeliverablesViewController instance = new EditDeliverablesViewController();
    private JPanel mainPanel;
    JTable table;
    EditDeliverablesTableModel tableModel;
    DeliverableDataSource dataSource = DeliverableDataSource.getInstance();
    private ArrayList<Deliverable> deliverables;

    private EditDeliverablesViewController() {
        deliverables = dataSource.getDeliverables();
        configureUI();
        mainPanel.setFocusable(true);
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
                    tableModel.add(deliverable);
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

    private class EditDeliverablesTableModel extends AbstractTableModel {
        private String[] columnNames;
        private ArrayList<DeliverableEditableViewModel> data = new ArrayList<>();

        public EditDeliverablesTableModel(ArrayList<Deliverable> data) {
            data.forEach(deliverable -> this.data.add(new DeliverableEditableViewModel(deliverable)));
            this.columnNames = DeliverableEditableViewModel.columnNames;
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
            switch (col) {
                case 0:
                    return String.class;
                case 1:
                    return Double.class;
                case 2:
                    return Boolean.class;
            }
            return null;
        }

        public boolean isCellEditable(int row, int col) {
            return true;
        }

        public void setValueAt(Object value, int row, int col) {
            switch (col) {
                case 0:
                    this.data.get(row).getModel().setName((String) value);
                    break;
                case 1:
                    this.data.get(row).getModel().setPrice((Double) value);
                    break;
            }
            fireTableCellUpdated(row, col);
        }

        public void remove(int row) {
            this.data.remove(row);
            this.fireTableRowsDeleted(row, row);
        }

        public void add(Deliverable deliverable) {
            this.data.add(new DeliverableEditableViewModel(deliverable));
            this.fireTableDataChanged();
        }
    }

}


