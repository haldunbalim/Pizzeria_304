package ViewModel;

import Model.Order;

import java.util.Date;

public class OrderEmployeeViewModel {
    public static String[] columnNames = {"Address", "Order", "Price", "Date", ""};
    private Order model;

    public OrderEmployeeViewModel(Order model) {
        this.model = model;
    }

    public Object getColumnView(int col) {
        switch (col) {
            case 0:
                return model.getOrderer().getAddress();
            case 1:
                return model.orderedItemsText();
            case 2:
                return model.getTotalPrice();
            case 3:
                return model.getDate();
            case 4:
                return false;
        }
        return null;
    }

    public static Class getColumnClassAt(int col) {
        switch (col) {
            case 0:
            case 1:
                return String.class;
            case 2:
                return Double.class;
            case 3:
                return Date.class;
            case 4:
                return Boolean.class;
        }
        return null;
    }

    public Order getModel() {
        return model;
    }
}
