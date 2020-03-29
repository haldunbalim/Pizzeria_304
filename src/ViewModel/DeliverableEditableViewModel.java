package ViewModel;

import Model.Deliverable;

public class DeliverableEditableViewModel {

    public static String[] columnNames = {"Name", "Price", ""};
    private Deliverable model;

    public DeliverableEditableViewModel(Deliverable model) {
        this.model = model;
    }

    public Object getColumnView(int col) {
        switch (col) {
            case 0:
                return model.getName();
            case 1:
                return model.getPrice();
            case 2:
                return false;
        }
        return null;
    }

    public Deliverable getModel() {
        return model;
    }
}
