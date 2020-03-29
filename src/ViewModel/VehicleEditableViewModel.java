package ViewModel;

import Model.Vehicle;

public class VehicleEditableViewModel {

    public static String[] columnNames = {"License Plate", "Brand", "Model", ""};
    private Vehicle model;

    public VehicleEditableViewModel(Vehicle model) {
        this.model = model;
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

    public Vehicle getModel() {
        return model;
    }
}
