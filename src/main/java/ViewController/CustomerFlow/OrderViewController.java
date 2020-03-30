package ViewController.CustomerFlow;

import ViewController.AbstractViewController;

import javax.swing.*;

public class OrderViewController extends AbstractViewController {
    private static OrderViewController instance = new OrderViewController();
    private JPanel mainPanel;

    private OrderViewController() {
    }

    public static OrderViewController getInstance() {
        return instance;
    }

    public JPanel getMainPanel() {
        return mainPanel;
    }
}
