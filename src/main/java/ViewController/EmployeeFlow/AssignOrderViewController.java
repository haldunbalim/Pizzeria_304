package ViewController.EmployeeFlow;

import ViewController.AbstractViewController;

import javax.swing.*;

public class AssignOrderViewController extends AbstractViewController {
    private static AssignOrderViewController instance = new AssignOrderViewController();
    private JPanel mainPanel;

    private AssignOrderViewController() {
    }

    public static AssignOrderViewController getInstance() {
        return instance;
    }

    public JPanel getMainPanel() {
        return mainPanel;
    }
}
