package ViewModel;

import Model.Order;

import java.util.Date;

public class OrderCustomerViewModel {
    public static String[] columnNames = {"Order", "Price", "Date", "Status"};
    private Order model;

    public OrderCustomerViewModel(Order model) {
        this.model = model;
    }

    public static Class getColumnClassAt(int col) {
        switch (col) {
            case 0:
            case 3:
                return String.class;
            case 1:
                return Double.class;
            case 2:
                return Date.class;
        }
        return null;
    }

    public Object getColumnView(int col) {
        switch (col) {
            case 0:
                return model.orderedItemsText();
            case 1:
                return model.getTotalPrice();
            case 2:
                return model.getDate();
            case 3:
                return model.getOrderState().toString();
        }
        return null;
    }

    public Order getModel() {
        return model;
    }
}
