package ViewController.EmployeeFlow;

import ViewController.AbstractViewController;

import javax.swing.*;

public class AssignVehicleViewController extends AbstractViewController {
    private static AssignVehicleViewController instance = new AssignVehicleViewController();
    private JPanel mainPanel;

    private AssignVehicleViewController() {
    }

    public static AssignVehicleViewController getInstance() {
        return instance;
    }

    public JPanel getMainPanel() {
        return mainPanel;
    }
}
