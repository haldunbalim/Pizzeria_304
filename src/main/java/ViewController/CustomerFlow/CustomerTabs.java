package ViewController.CustomerFlow;

import ViewController.ProfileViewController;
import ViewController.SetProfilePage;

import javax.swing.*;

public class CustomerTabs extends JTabbedPane {

    private static CustomerTabs instance = new CustomerTabs();

    private CustomerTabs() {
        this.addTab("Profile", ProfileViewController.getInstance().getMainPanel());
        this.addTab("Order", OrderViewController.getInstance().getMainPanel());
        this.addTab("View Orders", ViewOrdersViewController.getInstance().getMainPanel());
    }

    public static CustomerTabs getInstance() {
        return instance;
    }

    public void openProfileEditingMode() {
        this.setComponentAt(0, SetProfilePage.getInstance().getMainPanel());
        SetProfilePage.getInstance().configureUI();
        repaint();
    }

    public void closeProfileEditingMode() {
        this.setComponentAt(0, ProfileViewController.getInstance().getMainPanel());
        ProfileViewController.getInstance().configureUI();
        repaint();
    }
}
