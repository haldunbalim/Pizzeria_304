package ViewController.EmployeeFlow;

import ViewController.ProfileViewController;
import ViewController.SetProfilePage;

import javax.swing.*;

public class EmployeeTabs extends JTabbedPane {

    private static EmployeeTabs instance = new EmployeeTabs();

    private EmployeeTabs() {
        this.addTab("Profile", ProfileViewController.getInstance().getMainPanel());
        this.addTab("Assign Order", AssignOrderViewController.getInstance().getMainPanel());
    }

    public static EmployeeTabs getInstance() {
        return instance;
    }

    public void openProfileEditingMode() {
        this.setTabComponentAt(0, SetProfilePage.getInstance().getMainPanel());
    }
}

