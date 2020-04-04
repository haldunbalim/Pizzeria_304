package ViewController.EmployeeFlow;

import DataSource.OrdersDataSource;
import Model.Order;
import Model.OrderState;
import Reusable.ButtonRenderer;
import Reusable.WrapTextCellRenderer;
import ViewController.AbstractTableViewController;
import ViewController.MyAbstractTableModel;
import ViewModel.OrderEmployeeViewModel;

import javax.swing.*;
import java.util.ArrayList;
import java.util.stream.Collectors;

public class AssignOrderViewController extends AbstractTableViewController {
    private static AssignOrderViewController instance = new AssignOrderViewController();
    private JPanel mainPanel;
    private OrdersDataSource dataSource = OrdersDataSource.getInstance();
    private ArrayList<Order> orders;
    private AssignOrderTableModel tableModel;

    private AssignOrderViewController() {
        configureUI();
    }

    public static AssignOrderViewController getInstance() {
        return instance;
    }

    public void configureUI() {
        orders = new ArrayList<>(dataSource.getOrdersOfBranch().stream().filter(order -> order.getOrderState() != OrderState.PENDING)
                .collect(Collectors.toList()));
        configureTable();
    }

    private void configureTable() {
        tableModel = new AssignOrderTableModel(orders);
        table = new JTable(tableModel);
        table.getColumn("").setCellRenderer(new ButtonRenderer("Assign"));
        table.getColumn("Order").setCellRenderer(new WrapTextCellRenderer());
        addTable();
        for (int i = 0; i < orders.size(); i++) {
            table.setRowHeight(i, orders.get(i).getUniqueItemCount() * 17);
        }
    }

    public JPanel getMainPanel() {
        return mainPanel;
    }

    private class AssignOrderTableModel extends MyAbstractTableModel {
        public AssignOrderTableModel(ArrayList<Order> data) {
            data.forEach(deliverable -> this.data.add(new OrderEmployeeViewModel(deliverable)));
            this.columnNames = OrderEmployeeViewModel.columnNames;
        }

        public boolean isCellEditable(int row, int col) {
            return col == getColumnCount() - 1;
        }

        public Class getColumnClass(int col) {
            return OrderEmployeeViewModel.getColumnClassAt(col);
        }

        @Override
        public void setValueAt(Object value, int row, int col) {
            Order removed = orders.remove(row);
            EmployeeTabs.getInstance().setSelectedOrder(removed);
            EmployeeTabs.getInstance().openAssignVehicle();
            fireTableRowsUpdated(row, col);
        }

    }
}
