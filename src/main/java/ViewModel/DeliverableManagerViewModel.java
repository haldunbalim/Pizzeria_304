package ViewModel;

import Model.Deliverable;

public class DeliverableManagerViewModel extends AbstractViewModel {


    public static String[] columnNames = {"Name", "Price", ""};

    public DeliverableManagerViewModel(Deliverable model) {
        super(model);
    }

    public Object getColumnView(int col) {
        Deliverable model = ((Deliverable) this.model);
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
        Deliverable model = ((Deliverable) this.model);
        switch (col) {
            case 0:
                model.setName((String) value);
            case 1:
                model.setPrice((Double) value);
        }
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
