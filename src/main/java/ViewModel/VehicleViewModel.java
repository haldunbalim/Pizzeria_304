package ViewModel;

import Model.Vehicle;

public class VehicleViewModel extends AbstractViewModel {

    public static String[] columnNames = {"License Plate", "Brand", "Model", ""};

    public VehicleViewModel(Vehicle model) {
        super(model);
    }

    public Object getColumnView(int col) {
        Vehicle model = ((Vehicle) this.model);
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
        Vehicle model = ((Vehicle) this.model);
        switch (col) {
            case 0:
                model.setLicensePlate((String) value);
            case 1:
                model.setBrand((String) value);
            case 2:
                model.setModel((String) value);
        }
    }

    public static Class getColumnClassAt(int col) {
        return col == 3 ? Boolean.class : String.class;
    }
}
