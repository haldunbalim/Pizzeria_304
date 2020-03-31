package ViewController.CustomerFlow;

import DataSource.OrdersDataSource;
import Model.Order;
import Reusable.WrapTextCellRenderer;
import ViewController.AbstractViewController;
import ViewController.MyAbstractTableModel;
import ViewModel.OrderCustomerViewModel;

import javax.swing.*;
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

    private class ViewOrdersTableModel extends MyAbstractTableModel {
        public ViewOrdersTableModel(ArrayList<Order> data) {
            data.forEach(deliverable -> this.data.add(new OrderCustomerViewModel(deliverable)));
            this.columnNames = OrderCustomerViewModel.columnNames;
        }

        public Class getColumnClass(int col) {
            return OrderCustomerViewModel.getColumnClassAt(col);
        }

        public boolean isCellEditable(int row, int col) {
            return false;
        }
    }

    public JPanel getMainPanel() {
        return mainPanel;
    }
}
