package ViewModel;

import Model.Deliverable;

public class DeliverableCustomerViewModel extends AbstractViewModel {
    public static String[] columnNames = {"Name", "Price", "Amount", "Add", "Remove"};
    private Deliverable model;
    private int amount = 0;

    public DeliverableCustomerViewModel(Deliverable model) {
        super(model);
    }

    public static Class getColumnClassAt(int col) {
        switch (col) {
            case 0:
            case 1:
                return String.class;
            case 2:
                return Integer.class;
            case 3:
            case 4:
                return Boolean.class;
        }
        return null;
    }

    public Object getColumnView(int col) {
        switch (col) {
            case 0:
                return model.getName();
            case 1:
                return model.getPrice();
            case 2:
                return amount;
            case 3:
            case 4:
                return false;
        }
        return null;
    }

    @Override
    public void setValueAt(int col, Object value) {

    }

    public Deliverable getModel() {
        return model;
    }

    public void incrementAmount() {
        amount += 1;
    }

    public void decrementAmount() {
        if (amount > 0)
            amount -= 1;
    }

    public Integer getAmount() {
        return amount;
    }
}
