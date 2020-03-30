package ViewController.EmployeeFlow;

import Model.Order;
import Model.Vehicle;
import ViewController.ProfileViewController;
import ViewController.SetProfilePage;

import javax.swing.*;

public class EmployeeTabs extends JTabbedPane {

    private static EmployeeTabs instance = new EmployeeTabs();
    private Order selectedOrder;
    private Vehicle selectedVehicle;

    private EmployeeTabs() {
        this.addTab("Profile", ProfileViewController.getInstance().getMainPanel());
        this.addTab("Assign Order", AssignOrderViewController.getInstance().getMainPanel());
    }

    public static EmployeeTabs getInstance() {
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

    public void openAssignVehicle() {
        this.setComponentAt(1, AssignVehicleViewController.getInstance().getMainPanel());
        AssignVehicleViewController.getInstance().configureUI();
        repaint();
    }

    public void openAssignOrder() {
        this.setComponentAt(1, AssignOrderViewController.getInstance().getMainPanel());
        AssignOrderViewController.getInstance().configureUI();
        repaint();
    }

    public void openFinishDelivery() {
        this.setComponentAt(1, FinishDeliveryViewController.getInstance().getMainPanel());
        FinishDeliveryViewController.getInstance().configureUI();
        repaint();
    }

    public Order getSelectedOrder() {
        return selectedOrder;
    }

    public void setSelectedOrder(Order selectedOrder) {
        this.selectedOrder = selectedOrder;
    }

    public Vehicle getSelectedVehicle() {
        return selectedVehicle;
    }

    public void setSelectedVehicle(Vehicle selectedVehicle) {
        this.selectedVehicle = selectedVehicle;
    }
}

