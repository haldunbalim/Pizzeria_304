package ViewController.EmployeeFlow;

import DataSource.OrdersDataSource;
import Model.Order;
import Reusable.ButtonRenderer;
import Reusable.WrapTextCellRenderer;
import ViewController.AbstractViewController;
import ViewModel.OrderEmployeeViewModel;

import javax.swing.*;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.AbstractTableModel;
import java.awt.*;
import java.util.ArrayList;

public class AssignOrderViewController extends AbstractViewController implements TableModelListener {
    private static AssignOrderViewController instance = new AssignOrderViewController();
    private JPanel mainPanel;
    private OrdersDataSource dataSource = OrdersDataSource.getInstance();
    private ArrayList<Order> orders;
    private JTable table;
    private AssignOrderTableModel tableModel;
    private AssignOrderViewController() {
        configureUI();
        mainPanel.setFocusable(true);
        table.getModel().addTableModelListener(this);
    }

    public JPanel getMainPanel() {
        return mainPanel;
    }

    public static AssignOrderViewController getInstance() {
        return instance;
    }

    public void configureUI() {
        orders = dataSource.getOrdersOfBranch();
        configureTable();
    }

    private void configureTable() {
        tableModel = new AssignOrderTableModel(orders);
        table = new JTable(tableModel);
        table.getColumn("").setCellRenderer(new ButtonRenderer("Assign"));
        table.getColumn("Order").setCellRenderer(new WrapTextCellRenderer());
        JScrollPane scrollPane = new JScrollPane(table);
        table.setFillsViewportHeight(true);
        mainPanel.add(scrollPane, BorderLayout.CENTER);
        for (int i = 0; i < orders.size(); i++) {
            table.setRowHeight(i, orders.get(i).getUniqueItemCount() * 17);
        }
    }

    @Override
    public void tableChanged(TableModelEvent e) {
        int row = e.getFirstRow();
        Order removed = orders.remove(row);
        EmployeeTabs.getInstance().setSelectedOrder(removed);
        EmployeeTabs.getInstance().openAssignVehicle();
    }

    private class AssignOrderTableModel extends AbstractTableModel {
        private String[] columnNames;
        private ArrayList<OrderEmployeeViewModel> data = new ArrayList<>();

        public AssignOrderTableModel(ArrayList<Order> data) {
            data.forEach(deliverable -> this.data.add(new OrderEmployeeViewModel(deliverable)));
            this.columnNames = OrderEmployeeViewModel.columnNames;
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
            return OrderEmployeeViewModel.getColumnClassAt(col);
        }

        public boolean isCellEditable(int row, int col) {
            return col == getColumnCount() - 1;
        }

        public void setValueAt(Object value, int row, int col) {
            fireTableCellUpdated(row, col);
        }
    }
}
