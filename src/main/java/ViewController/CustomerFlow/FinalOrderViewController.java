package ViewController.CustomerFlow;

import DataSource.AuthenticationManager;
import DataSource.OrdersDataSource;
import Model.Order;
import Model.OrderState;
import ViewController.AbstractViewController;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Date;

public class FinalOrderViewController extends AbstractViewController {
    private static FinalOrderViewController instance = new FinalOrderViewController();
    private JPanel mainPanel;
    private JButton orderButton;
    private JLabel pointsLabel;
    private JLabel priceAfterDiscountLabel;
    private JLabel priceBeforeDiscountLabel;
    private JLabel itemsLabel;
    private JButton cancelButton;

    private FinalOrderViewController() {
        configureUI();
        orderButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Order order = OrdersDataSource.getInstance().createOrder(CustomerTabs.getInstance().getCurrentOrderDeliverables());
                int pointsEarned = (int) (order.getTotalPriceWithoutDiscount() * 0.1);
                AuthenticationManager.getInstance().getCurrentUser().setMembershipPoints(AuthenticationManager.getInstance().getCurrentUser().getMembershipPoints() + pointsEarned);
                CustomerTabs.getInstance().openOrderViewController();
            }
        });

        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                CustomerTabs.getInstance().setCurrentOrderDeliverables(null);
                CustomerTabs.getInstance().openOrderViewController();
            }
        });
    }

    public void configureUI() {
        Order dummyOrder = new Order(-1, AuthenticationManager.getInstance().getCurrentUser(), new Date().getTime(), CustomerTabs.getInstance().getCurrentOrderDeliverables(), OrderState.PENDING);
        itemsLabel.setText("<html>" + dummyOrder.orderedItemsText() + "</html>");
        priceBeforeDiscountLabel.setText("" + dummyOrder.getTotalPriceWithoutDiscount());
        priceAfterDiscountLabel.setText("" + dummyOrder.getTotalPrice());
        int pointsEarned = (int) (dummyOrder.getTotalPriceWithoutDiscount() * 0.1);
        pointsLabel.setText((dummyOrder.getOrderer().getMembershipPoints() + pointsEarned) + " (+" + pointsEarned + ")");
    }

    public static FinalOrderViewController getInstance() {
        return instance;
    }

    public JPanel getMainPanel() {
        return mainPanel;
    }
}
