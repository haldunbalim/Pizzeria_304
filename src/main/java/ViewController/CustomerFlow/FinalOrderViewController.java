package ViewController.CustomerFlow;

import ViewController.AbstractViewController;

import javax.swing.*;

public class FinalOrderViewController extends AbstractViewController {
    private static FinalOrderViewController instance = new FinalOrderViewController();
    private JPanel mainPanel;

    private FinalOrderViewController() {
    }

    public static FinalOrderViewController getInstance() {
        return instance;
    }

    public JPanel getMainPanel() {
        return mainPanel;
    }
}
