package DataSource;

import Model.Vehicle;
import Service.AuthenticationManager;
import database.DataBaseCredentials;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;

public class VehicleDataSource extends AbstractDataSource {


    private static VehicleDataSource instance = new VehicleDataSource();

    private VehicleDataSource() {
        primaryTable = "Vehicle";
    }

    public static VehicleDataSource getInstance() {
        return instance;
    }

    private static HashMap<String, Boolean> availabilityHasMap = new HashMap<String, Boolean>() {
        {
            put("Y", true);
            put("N", false);
        }
    };

    private static HashMap<Boolean, String> availabilityHasMapReverse = new HashMap<Boolean, String>() {
        {
            put(true, "Y");
            put(false, "N");
        }
    };


    // access current user via LoginManager
    public ArrayList<Vehicle> getVehiclesOfCurrentBranch() {
        ArrayList<Vehicle> list = new ArrayList<>();
        try {
            Statement stmt = connection.createStatement();
            long bid = AuthenticationManager.getInstance().getCurrentUser().getAffiliatedBranch().getBid();
            String query = String.format("SELECT * FROM VEHICLE natural join VEHICLEMODELBRAND WHERE BID=%d", bid);
            ResultSet rs = stmt.executeQuery(query);

            while (rs.next()) {
                String licensePlate = rs.getString("license_plate");
                String model = rs.getString("model");
                String brand = rs.getString("brand");
                String availability = rs.getString("availability");
                boolean avail = availabilityHasMap.get(availability);

                // TODO: Available Field added to vehicle
                list.add(new Vehicle(licensePlate, model, brand, avail));
            }
            rs.close();
            stmt.close();
        } catch (SQLException e) {
            System.out.println(DataBaseCredentials.WARNING_TAG + DataBaseCredentials.tableEmpty + "Vehicle");
        }

        return list;
    }

    public void updateVehicleData(Vehicle v) {
        String licensePlate = v.getLicensePlate().toUpperCase();
        String brand = v.getBrand();
        brand = brand.toUpperCase().charAt(0) + brand.toLowerCase().substring(1);
        String model = v.getModel();
        model = model.toUpperCase().charAt(0) + model.toLowerCase().substring(1);
        boolean availability = v.isAvailable();
        String avail = availabilityHasMapReverse.get(availability);

        String brand2 = checkBrandName(brand, model);
        String setValues = String.format("model='%s', availability='%s' WHERE licence_plate='%s'", model, avail, licensePlate);

        updateColumnValues(primaryTable, setValues);
        v.setBrand(brand2);
    }

    public void removeVehicleData(Vehicle v) {
        String licensePlate = v.getLicensePlate();
        removeFromDb(primaryTable, String.format("license_plate='%s'", licensePlate));
    }

    public Vehicle createNewVehicle(String licensePlate, String brand, String model) {
        licensePlate = licensePlate.toUpperCase();
        brand = brand.toUpperCase().charAt(0) + brand.toLowerCase().substring(1);
        model = model.toUpperCase().charAt(0) + model.toLowerCase().substring(1);

        brand = checkBrandName(brand, model);
        Vehicle v = null;
        DataBaseCredentials.OperationResult res = insertIntoDb(primaryTable, String.format("'%s', '%s', '%s'", licensePlate, model, "Y"));
        if (res == DataBaseCredentials.OperationResult.inserted) {
            v = new Vehicle(licensePlate, model, brand, true);
        }
        return v;
    }

    public String checkBrandName(String brand, String model) {
        try {
            PreparedStatement ps = connection.prepareStatement("INSERT INTO VEHICLEMODELBRAND VALUES (?, ?)");
            ps.setString(1, model);
            ps.setString(2, brand);

            ps.executeUpdate();
            ps.close();

            return brand;
        } catch (SQLIntegrityConstraintViolationException e) {
            System.out.println(DataBaseCredentials.WARNING_TAG + DataBaseCredentials.modelExists);
            try {
                // get the correct brand name
                Statement stmt = connection.createStatement();
                String query = String.format("SELECT brand FROM VEHICLEMODELBRAND WHERE model ='%s'",
                        model);
                ResultSet rs = stmt.executeQuery(query);

                while (rs.next()) {
                    brand = rs.getString(1);
                }
                rs.close();
                stmt.close();
                return brand;
            } catch (SQLException s) {
                System.out.println(DataBaseCredentials.EXCEPTION_TAG + s.getMessage());
            }

        } catch (SQLException e) {
            //System.out.println(DataBaseCredentials.EXCEPTION_TAG + DataBaseCredentials);
            return "";
        }
        return brand;
    }

    public void setVehicleAvailability(Vehicle v, boolean b) {
        String licensePlate = v.getLicensePlate();

        try {
            PreparedStatement ps = connection.prepareStatement("UPDATE VEHICLE SET AVAILABILITY = ?" +
                    "WHERE LICENSE_PLATE = ?");

            ps.setString(1, availabilityHasMapReverse.get(b));
            ps.setString(2, licensePlate);

            ps.close();
            connection.commit();
            v.setAvailable(b);
        } catch (SQLException e) {
            System.out.println(DataBaseCredentials.EXCEPTION_TAG + DataBaseCredentials.updateError);
        }
    }
}
