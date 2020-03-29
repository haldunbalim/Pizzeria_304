package ViewController.ManagerFlow;

import javax.swing.*;

public class ManagerTabs extends JTabbedPane {

    private static ManagerTabs instance = new ManagerTabs();

    private ManagerTabs() {
        this.addTab("Deliverables", EditDeliverablesViewController.getInstance().getMainPanel());
        this.addTab("Vehicles", EditVehiclesViewController.getInstance().getMainPanel());
        this.addTab("Employees", EditEmployeesViewController.getInstance().getMainPanel());
    }

    public static ManagerTabs getInstance() {
        return instance;
    }

}
