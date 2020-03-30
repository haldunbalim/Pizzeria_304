package ViewModel;

import Model.Order;

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

    public Order getModel() {
        return model;
    }
}
