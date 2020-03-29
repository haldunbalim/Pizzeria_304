package ViewController.ManagerFlow;

import javax.swing.*;

public class ManagerTabs extends JTabbedPane {

    private static ManagerTabs instance = new ManagerTabs();

    private ManagerTabs() {
        this.addTab("Deliverables", EditDeliverablesViewController.getInstance().mainPanel);
        this.addTab("Vehicles", EditVehiclesViewController.getInstance().mainPanel);
        this.addTab("Employees", EditEmployeesViewController.getInstance().mainPanel);
    }

    public static ManagerTabs getInstance() {
        return instance;
    }

}
