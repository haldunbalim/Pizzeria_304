package ViewModel;

import Model.Deliverable;

public class DeliverableEditableViewModel extends AbstractViewModel {


    public static String[] columnNames = {"Name", "Price", ""};
    protected Deliverable model;

    public DeliverableEditableViewModel(Deliverable model) {
        super(model);
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

    @Override
    public void setValueAt(int col, Object value) {

    }

    public static Class getColumnClassAt(int col) {
        switch (col) {
            case 0:
                return String.class;
            case 1:
                return Double.class;
            case 2:
                return Boolean.class;
        }
        return null;
    }


}
