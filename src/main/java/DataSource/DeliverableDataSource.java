package DataSource;

import Model.Deliverable;
import database.DataBaseCredentials;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class DeliverableDataSource extends AbstractDataSource {

    private static DeliverableDataSource instance = new DeliverableDataSource();
    private DeliverableDataSource() {
        primaryTable = "Deliverable";
    }

    public static DeliverableDataSource getInstance() {
        return instance;
    }


    // returns an arrayList of Deliverables for currentUser's affiliated restaurant
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

    // given an updated deliverable model saves the update in db
    public void updateDeliverableData(Deliverable deliverable) {
        int did = deliverable.getDid();
        String name = deliverable.getName();
        double p = deliverable.getPrice();
        float price = (float) p;
        try {
            PreparedStatement ps = connection.prepareStatement("UPDATE DELIVERABLE SET " +
                    "name = ?, price = ? WHERE did = ?");
            ps.setString(1, name);
            ps.setFloat(2, price);
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

    // given an deliverable model removes it from the db
    public void removeDeliverableData(Deliverable deliverable) {
        int did = deliverable.getDid();
        removeFromDb(primaryTable, String.format("did=%d", did));

        // TODO: if db deletion fails don't allow the object to be deleted
//
//        try {
//            PreparedStatement ps = connection.prepareStatement("DELETE FROM DELIVERABLE WHERE " +
//                    "did = ?");
//            ps.setInt(1, did);
//
//            int rowCount = ps.executeUpdate();
//            if (rowCount == 0) {
//                System.out.println(DataBaseCredentials.WARNING_TAG + DataBaseCredentials.deliverableNotFound);
//            }
//            connection.commit();
//            ps.close();
//        } catch (SQLException e) {
//            System.out.println(DataBaseCredentials.EXCEPTION_TAG + DataBaseCredentials.deleteError);
//        }
    }

    // given props create a new deliverable in db and return
    public Deliverable createNewDeliverable(String name, Double price) {
        Deliverable d = null;
        try {
            int newDid = getNextId(primaryTable, "did");
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

}
