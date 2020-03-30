package ViewModel;

import Model.Vehicle;

public class VehicleViewModel extends AbstractViewModel {

    public static String[] columnNames = {"License Plate", "Brand", "Model", ""};
    protected Vehicle model;

    public VehicleViewModel(Vehicle model) {
        super(model);
    }

    public Object getColumnView(int col) {
        switch (col) {
            case 0:
                return model.getLicensePlate();
            case 1:
                return model.getBrand();
            case 2:
                return model.getModel();
            case 3:
                return false;
        }
        return null;
    }

    @Override
    public void setValueAt(int col, Object value) {

    }
    public static Class getColumnClassAt(int col) {
        return col == 3 ? Boolean.class : String.class;
    }
}
