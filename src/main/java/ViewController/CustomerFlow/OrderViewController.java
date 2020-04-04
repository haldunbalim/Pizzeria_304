package ViewController.CustomerFlow;

import DataSource.DeliverableDataSource;
import Model.Deliverable;
import Reusable.ButtonRenderer;
import ViewController.AbstractViewController;
import ViewController.MyAbstractTableModel;
import ViewModel.AbstractViewModel;
import ViewModel.DeliverableCustomerViewModel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.ArrayList;

public class OrderViewController extends AbstractViewController {
    private JPanel mainPanel;
    private JTable table;
    private OrderDeliverablesTableModel tableModel;
    private DeliverableDataSource dataSource = DeliverableDataSource.getInstance();
    private ArrayList<Deliverable> deliverables;

    protected OrderViewController() {
        deliverables = dataSource.getDeliverables();
        configureUI();
        configureOrderButton();
    }


    public void configureUI() {
        configureTable();
    }

    private void configureOrderButton() {
        JButton orderButton = new JButton("Order");
        orderButton.addActionListener((ActionEvent e) -> {
            ArrayList<Deliverable> orderedItems = tableModel.getOrderedItems();
            if (orderedItems.size() > 0) {
                CustomerTabs.getInstance().setCurrentOrderDeliverables(orderedItems);
                CustomerTabs.getInstance().openFinalOrderViewController();
            }
        });
        mainPanel.add(orderButton, BorderLayout.SOUTH);
    }


    private void configureTable() {
        tableModel = new OrderDeliverablesTableModel(deliverables);
        table = new JTable(tableModel);
        table.getColumn("Add").setCellRenderer(new ButtonRenderer("+"));
        table.getColumn("Remove").setCellRenderer(new ButtonRenderer("-"));
        JScrollPane scrollPane = new JScrollPane(table);
        table.setFillsViewportHeight(true);
        mainPanel.add(scrollPane, BorderLayout.CENTER);
    }

    public JPanel getMainPanel() {
        return mainPanel;
    }

    private class OrderDeliverablesTableModel extends MyAbstractTableModel {
        public OrderDeliverablesTableModel(ArrayList<Deliverable> data) {
            data.forEach(deliverable -> this.data.add(new DeliverableCustomerViewModel(deliverable)));
            this.columnNames = DeliverableCustomerViewModel.columnNames;
        }

        public Class getColumnClass(int col) {
            return DeliverableCustomerViewModel.getColumnClassAt(col);
        }


        public boolean isCellEditable(int row, int col) {
            return col >= getColumnCount() - 2;
        }

        public ArrayList<Deliverable> getOrderedItems() {
            ArrayList<Deliverable> list = new ArrayList<>();
            for (AbstractViewModel dcvm : data)
                for (int i = 0; i < ((DeliverableCustomerViewModel) dcvm).getAmount(); i++)
                    list.add((Deliverable) dcvm.getModel());
            return list;
        }

        @Override
        public void setValueAt(Object value, int row, int col) {
            if (col == getColumnCount() - 1) {
                ((DeliverableCustomerViewModel) data.get(row)).decrementAmount();
            } else {
                ((DeliverableCustomerViewModel) data.get(row)).incrementAmount();
            }
            fireTableCellUpdated(row, col);
            fireTableDataChanged();
        }


    }


}
