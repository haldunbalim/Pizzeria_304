package ViewController.UserFlow;

import ViewController.ProfileViewController;
import ViewController.SetProfilePage;

import javax.swing.*;

public class UserTabs extends JTabbedPane {

    private static UserTabs instance = new UserTabs();

    private UserTabs() {
        this.addTab("Profile", ProfileViewController.getInstance().getMainPanel());
        this.addTab("Order", OrderViewController.getInstance().getMainPanel());
        this.addTab("View Orders", ViewOrdersViewController.getInstance().getMainPanel());
    }

    public static UserTabs getInstance() {
        return instance;
    }

    public void openProfileEditingMode() {
        this.setTabComponentAt(0, SetProfilePage.getInstance().getMainPanel());
    }
}
