package DataSource;

import Model.Vehicle;

import java.util.ArrayList;

public class VehicleDataSource extends AbstractDataSource {


    private static VehicleDataSource instance = new VehicleDataSource();

    private VehicleDataSource() {
    }

    public static VehicleDataSource getInstance() {
        return instance;
    }

    // TODO: Same as DeliverableDataSource

    // access current user via LoginManager
    public ArrayList<Vehicle> getVehicles() {
        ArrayList<Vehicle> list = new ArrayList<>();
        list.add(new Vehicle("mod1", "Del1", "br1", true));
        list.add(new Vehicle("mod1", "Del1", "br1", true));
        list.add(new Vehicle("mod2", "Del1", "br1", true));
        list.add(new Vehicle("mod1", "Del1", "br1", true));
        list.add(new Vehicle("mod3", "Del1", "br1", true));
        list.add(new Vehicle("mod1", "Del1", "br1", true));
        list.add(new Vehicle("mod4", "Del1", "br1", true));
        list.add(new Vehicle("mod1", "Del1", "br1", true));
        list.add(new Vehicle("mod6", "Del1", "br1", true));
        list.add(new Vehicle("mod1", "Del1", "br1", true));
        list.add(new Vehicle("mod7", "Del1", "br1", true));
        list.add(new Vehicle("mod1", "Del1", "br1", true));

        return list;
    }

    public void updateVehicleData(Vehicle model) {

    }

    public void removeVehicleData(Vehicle model) {

    }

    // TODO: Check if user does not set lic plate with existing entry
    public Vehicle createNewVehicle(String licensePlate, String brand, String model) {
        return new Vehicle("2314321", brand, model, true);
    }

}
