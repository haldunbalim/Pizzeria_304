package ViewModel;

import Model.Order;

import java.util.Date;

public class OrderEmployeeViewModel extends AbstractViewModel {
    public static String[] columnNames = {"Address", "Order", "Price", "Date", ""};
    protected Order model;

    public OrderEmployeeViewModel(Order model) {
        super(model);
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

    @Override
    public void setValueAt(int col, Object value) {

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
}
