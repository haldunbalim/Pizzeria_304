package ViewController.EmployeeFlow;

import DataSource.OrdersDataSource;
import Model.Order;
import Model.OrderState;
import Model.Vehicle;
import ViewController.AbstractViewController;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class FinishDeliveryViewController extends AbstractViewController {
    private static FinishDeliveryViewController instance = new FinishDeliveryViewController();
    private JPanel mainPanel;
    private JLabel addressLabel;
    private JLabel itemsLabel;
    private JLabel priceLabel;
    private JLabel vehicleLicensePlateLabel;
    private JButton deliverButton;

    private FinishDeliveryViewController() {
        configureUI();
        deliverButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Order selectedOrder = EmployeeTabs.getInstance().getSelectedOrder();
                Vehicle selectedVehicle = EmployeeTabs.getInstance().getSelectedVehicle();
                OrdersDataSource.getInstance().changeOrderState(selectedOrder, selectedVehicle, OrderState.DELIVERED);
                EmployeeTabs.getInstance().openAssignOrder();
            }
        });
    }

    public static FinishDeliveryViewController getInstance() {
        return instance;
    }

    public void configureUI() {
        Order selectedOrder = EmployeeTabs.getInstance().getSelectedOrder();
        Vehicle selectedVehicle = EmployeeTabs.getInstance().getSelectedVehicle();
        addressLabel.setText(selectedOrder.getOrderer().getAddress().toString());
        itemsLabel.setText("<html>" + selectedOrder.orderedItemsText() + "</html>");
        priceLabel.setText("" + selectedOrder.getTotalPrice());
        vehicleLicensePlateLabel.setText(selectedVehicle.getLicensePlate());
    }

    public JPanel getMainPanel() {
        return mainPanel;
    }
}
