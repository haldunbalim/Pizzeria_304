package DataSource;

import Model.Deliverable;
import database.DataBaseCredentials;
import database.DatabaseConnectionHandler;

import java.sql.*;
import java.util.ArrayList;
import java.util.Random;

public class DeliverableDataSource extends AbstractDataSource {

    private static DeliverableDataSource instance = new DeliverableDataSource();
    private static DatabaseConnectionHandler dbHandler = DatabaseConnectionHandler.getInstance();
    private static Connection connection = dbHandler.getConnection();

    private DeliverableDataSource() {
    }

    public static DeliverableDataSource getInstance() {
        return instance;
    }


    // TODO: returns an arrayList of Deliverables for currentUser's affiliated restaurant
    // access current user via LoginManager
    public ArrayList<Deliverable> getDeliverables() {
        ArrayList<Deliverable> list = new ArrayList<>();
        try {
            Statement stmt = connection.createStatement();
            String query = "SELECT * FROM DELIVERABLE";
            ResultSet rs = stmt.executeQuery(query);

            while (rs.next()) {
                int did = rs.getInt("did");
                String name = rs.getString("name");
                double price = rs.getDouble("price");
                list.add(new Deliverable(did, name, price));
            }
            rs.close();
            stmt.close();
        } catch (SQLException e) {
            System.out.println(DataBaseCredentials.WARNING_TAG + DataBaseCredentials.tableEmpty + "-Deliverable");
        }
        return list;
    }

    // TODO: given an updated deliverable model saves the update in db
    public void updateDeliverableData(Deliverable deliverable) {
        int did = deliverable.getDid();
        String name = deliverable.getName();
        double price = deliverable.getPrice();
        try {
            PreparedStatement ps = connection.prepareStatement("UPDATE DELIVERABLE SET " +
                    "name = ?, price = ? WHERE did = ?");
            ps.setString(1, name);
            ps.setDouble(2, price);
            ps.setInt(3, did);

            int rowCount = ps.executeUpdate();
            if (rowCount == 0) {
                System.out.println(DataBaseCredentials.WARNING_TAG + DataBaseCredentials.deliverableNotFound);
            }
            connection.commit();
            ps.close();
        } catch (SQLException e) {
            System.out.println(DataBaseCredentials.EXCEPTION_TAG + DataBaseCredentials.updateError);
        }
    }

    // TODO: given an deliverable model removes it from the db
    public void removeDeliverableData(Deliverable deliverable) {
        int did = deliverable.getDid();
        try {
            PreparedStatement ps = connection.prepareStatement("DELETE FROM DELIVERABLE WHERE " +
                    "did = ?");
            ps.setInt(1, did);

            int rowCount = ps.executeUpdate();
            if (rowCount == 0) {
                System.out.println(DataBaseCredentials.WARNING_TAG + DataBaseCredentials.deliverableNotFound);
            }
            connection.commit();
            ps.close();
        } catch (SQLException e) {
            System.out.println(DataBaseCredentials.EXCEPTION_TAG + DataBaseCredentials.deleteError);
        }
    }

    // TODO: given props create a new deliverable in db and return
    public Deliverable createNewDeliverable(String name, Double price) {
        Deliverable d = null;

        try {
            int newDid = getNextDID();
            PreparedStatement ps = connection.prepareStatement("INSERT INTO DELIVERABLE VALUES (?, ?, ?)");
            ps.setString(2, name);
            ps.setDouble(3, price);
            ps.setInt(1, newDid);

            ps.executeUpdate();
            ps.close();
            connection.commit();

            d = new Deliverable(newDid, name, price);

        } catch (SQLException e) {
            System.out.println(DataBaseCredentials.EXCEPTION_TAG + DataBaseCredentials.insertionError);
        }
        return d;
    }

    private int getNextDID() {
        int nextDid = 1;
        ArrayList<Integer> takenDids = new ArrayList<Integer>();
        try {
            Statement stmt = connection.createStatement();
            // Oracle DB uses FETCH NEXT row_number ROWS ONLY
            ResultSet rs = stmt.executeQuery("SELECT did FROM DELIVERABLE ORDER BY did");

            while (rs.next()) {
                takenDids.add(rs.getInt("did"));
            }
            nextDid = takenDids.get(0);

            while (takenDids.contains(nextDid)) {
                int high = (int) (1e7 - 1);
                int low = (int) (1e6);
                nextDid = new Random().nextInt(high-low) + low;
            }
            rs.close();
            stmt.close();

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return nextDid;
    }

}
