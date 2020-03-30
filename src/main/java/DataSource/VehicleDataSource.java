package DataSource;

import Model.Vehicle;
import database.DataBaseCredentials;
import database.DatabaseConnectionHandler;

import java.sql.*;
import java.util.ArrayList;

public class VehicleDataSource extends AbstractDataSource {


    private static VehicleDataSource instance = new VehicleDataSource();
    private static DatabaseConnectionHandler dbHandler = DatabaseConnectionHandler.getInstance();
    private static Connection connection = DatabaseConnectionHandler.getConnection();


    private VehicleDataSource() {
    }

    public static VehicleDataSource getInstance() {
        return instance;
    }



    // access current user via LoginManager
    public ArrayList<Vehicle> getVehicles() {
        ArrayList<Vehicle> list = new ArrayList<>();
        try {
            Statement stmt = connection.createStatement();
            String query = "SELECT * FROM VEHICLE natural join VEHICLEMODELBRAND";
            ResultSet rs = stmt.executeQuery(query);

            while (rs.next()) {
                String licensePlate = rs.getString("license_plate");
                String model = rs.getString("model");
                String brand = rs.getString("brand");
                // TODO: Available Field added to vehicle
                list.add(new Vehicle(licensePlate, model, brand, true));
            }
            rs.close();
            stmt.close();
        } catch (SQLException e) {
            System.out.println(DataBaseCredentials.WARNING_TAG + DataBaseCredentials.tableEmpty + "Vehicle");
        }
//        ArrayList<Vehicle> list = new ArrayList<>();
//        list.add(new Vehicle("mod1", "Del1", "br1"));
//        list.add(new Vehicle("mod1", "Del1", "br1"));
//        list.add(new Vehicle("mod2", "Del1", "br1"));
//        list.add(new Vehicle("mod1", "Del1", "br1"));
//        list.add(new Vehicle("mod3", "Del1", "br1"));
//        list.add(new Vehicle("mod1", "Del1", "br1"));
//        list.add(new Vehicle("mod4", "Del1", "br1"));
//        list.add(new Vehicle("mod1", "Del1", "br1"));
//        list.add(new Vehicle("mod6", "Del1", "br1"));
//        list.add(new Vehicle("mod1", "Del1", "br1"));
//        list.add(new Vehicle("mod7", "Del1", "br1"));
//        list.add(new Vehicle("mod1", "Del1", "br1"));

        return list;
    }

    public void updateVehicleData(Vehicle v) {
        //String license
    }

    public void removeVehicleData(Vehicle v) {
        String licensePlate = v.getLicensePlate();
        String model = v.getModel();
        String brand = v.getBrand();

        try {
            PreparedStatement ps = connection.prepareStatement("DELETE FROM Vehicle WHERE LICENSE_PLATE = ?");
            ps.setString(1, licensePlate);

            int rowCount = ps.executeUpdate();
            if (rowCount == 0) {
                System.out.println(DataBaseCredentials.WARNING_TAG + DataBaseCredentials.vehicleNotFound);
            }
            connection.commit();
            ps.close();
        } catch (SQLException e) {
            System.out.println(DataBaseCredentials.EXCEPTION_TAG + DataBaseCredentials.vehicleNotFound);
        }
    }

    public Vehicle createNewVehicle(String licensePlate, String brand, String model) {
        licensePlate = licensePlate.toUpperCase();
        brand = brand.toUpperCase().charAt(0) + brand.toLowerCase().substring(1);
        model = model.toUpperCase().charAt(0) + model.toLowerCase().substring(1);

        brand = checkBrandName(brand, model);
        Vehicle v = null;

        try {
            PreparedStatement ps = connection.prepareStatement("INSERT INTO VEHICLE VALUES (?, ?)");
            ps.setString(1, licensePlate);
            ps.setString(2, model);

            ps.executeUpdate();
            connection.commit();
            ps.close();
            // TODO: Available Field added to vehicle
            v = new Vehicle(licensePlate, model, brand, true);
        } catch (SQLIntegrityConstraintViolationException e) {
            System.out.println(DataBaseCredentials.EXCEPTION_TAG + DataBaseCredentials.licenseExists);
        } catch (SQLException e) {
            System.out.println(DataBaseCredentials.EXCEPTION_TAG + DataBaseCredentials.insertionError);
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
                // TODO: after addition brand name is still incorrect although added correctly
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

}
