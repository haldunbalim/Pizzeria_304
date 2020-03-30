package ViewController.CustomerFlow;

import DataSource.OrdersDataSource;
import Model.Order;
import Reusable.WrapTextCellRenderer;
import ViewController.AbstractViewController;
import ViewModel.OrderCustomerViewModel;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import java.awt.*;
import java.util.ArrayList;

public class ViewOrdersViewController extends AbstractViewController {
    private static ViewOrdersViewController instance = new ViewOrdersViewController();
    private JPanel mainPanel;
    private OrdersDataSource dataSource = OrdersDataSource.getInstance();
    private ArrayList<Order> orders;
    private JTable table;
    private ViewOrdersTableModel tableModel;

    private ViewOrdersViewController() {
        configureUI();
        mainPanel.setFocusable(true);
    }

    public JPanel getMainPanel() {
        return mainPanel;
    }

    public static ViewOrdersViewController getInstance() {
        return instance;
    }

    public void configureUI() {
        orders = dataSource.getOrdersOfUser();
        configureTable();
    }

    private void configureTable() {
        tableModel = new ViewOrdersTableModel(orders);
        table = new JTable(tableModel);
        table.getColumn("Order").setCellRenderer(new WrapTextCellRenderer());
        JScrollPane scrollPane = new JScrollPane(table);
        table.setFillsViewportHeight(true);
        mainPanel.add(scrollPane, BorderLayout.CENTER);
        for (int i = 0; i < orders.size(); i++) {
            table.setRowHeight(i, orders.get(i).getUniqueItemCount() * 17);
        }
    }

    private class ViewOrdersTableModel extends AbstractTableModel {
        private String[] columnNames;
        private ArrayList<OrderCustomerViewModel> data = new ArrayList<>();

        public ViewOrdersTableModel(ArrayList<Order> data) {
            data.forEach(deliverable -> this.data.add(new OrderCustomerViewModel(deliverable)));
            this.columnNames = OrderCustomerViewModel.columnNames;
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
            return OrderCustomerViewModel.getColumnClassAt(col);
        }

        public boolean isCellEditable(int row, int col) {
            return false;
        }
    }
}
