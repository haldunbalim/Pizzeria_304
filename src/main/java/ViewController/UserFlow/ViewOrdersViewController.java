package ViewController.UserFlow;

import ViewController.AbstractViewController;

import javax.swing.*;

public class ViewOrdersViewController extends AbstractViewController {
    private static ViewOrdersViewController instance = new ViewOrdersViewController();
    private JPanel mainPanel;

    private ViewOrdersViewController() {
    }

    public static ViewOrdersViewController getInstance() {
        return instance;
    }

    public JPanel getMainPanel() {
        return mainPanel;
    }
}
