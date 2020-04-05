package ViewController.CustomerFlow;

import Model.Deliverable;
import ViewController.CommonFlow.ProfileViewController;
import ViewController.CommonFlow.SetProfilePage;

import javax.swing.*;
import java.util.ArrayList;

public class CustomerTabs extends JTabbedPane {

    private static CustomerTabs instance = new CustomerTabs();
    private ArrayList<Deliverable> currentOrderDeliverables;

    private CustomerTabs() {
        this.addTab("Profile", ProfileViewController.getInstance().getMainPanel());
        this.addTab("Order", new OrderViewController().getMainPanel());
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

    public void openFinalOrderViewController() {
        this.setComponentAt(1, FinalOrderViewController.getInstance().getMainPanel());
        FinalOrderViewController.getInstance().configureUI();
        repaint();
    }

    //TODO: Refresh View orders??
    //TODO: Refresh Profile?
    public void openOrderViewController() {
        setComponentAt(1, new OrderViewController().getMainPanel());
        repaint();
    }

    public ArrayList<Deliverable> getCurrentOrderDeliverables() {
        return currentOrderDeliverables;
    }

    public void setCurrentOrderDeliverables(ArrayList<Deliverable> currentOrderDeliverables) {
        this.currentOrderDeliverables = currentOrderDeliverables;
    }

}
