package DataSource;

import Model.Deliverable;
import database.DataBaseCredentials;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Locale;

public class DeliverableDataSource extends AbstractDataSource {

    private static DeliverableDataSource instance = new DeliverableDataSource();

    private DeliverableDataSource() {
        primaryTable = "Deliverable";
    }

    public static DeliverableDataSource getInstance() {
        return instance;
    }


    /**
     * returns an arrayList of Deliverables for currentUser's affiliated restaurant
     * access current user via AuthenticationManager
     *
     * @return
     */
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

    /**
     * @param did
     * @return
     */
    public Deliverable getDeliverable(int did) {
        Deliverable d = null;
        try {
            Statement stmt = connection.createStatement();
            String query = "SELECT * FROM DELIVERABLE WHERE did=" + did;
            ResultSet rs = stmt.executeQuery(query);

            while (rs.next()) {
                String name = rs.getString("name");
                double price = rs.getDouble("price");
                d = new Deliverable(did, name, price);
            }
            rs.close();
            stmt.close();
        } catch (SQLException e) {
            System.out.println(DataBaseCredentials.WARNING_TAG + DataBaseCredentials.tableEmpty + "-Deliverable");
        }
        return d;
    }


    /**
     * @return an arrayList of Deliverables for the specific order
     */
    public ArrayList<Deliverable> getDeliverablesInOrder(long oid) {
        ArrayList<Deliverable> list = new ArrayList<>();
        try {
            Statement stmt = connection.createStatement();
            String query = "SELECT * FROM ORDERS natural join ORDERCONTAINSDELIVERABLES";
            ResultSet rs = stmt.executeQuery(query);

            while (rs.next()) {
                int did = rs.getInt("did");
                list.add(getDeliverable(did));
            }
            rs.close();
            stmt.close();
        } catch (SQLException e) {
            System.out.println(DataBaseCredentials.WARNING_TAG + DataBaseCredentials.tableEmpty + "-Deliverable");
        }
        return list;
    }


    /**
     * given an updated deliverable model saves the update in db
     *
     * @param deliverable Deliverable instance
     */
    public void updateDeliverableData(Deliverable deliverable) {
        int did = deliverable.getDid();
        String name = deliverable.getName();
        double p = deliverable.getPrice();
        float price = (float) p;
        String values = String.format(Locale.CANADA, "name='%s', price=%.2f WHERE did=%d", name, price, did);
        updateColumnValues(primaryTable, values);
    }

    /**
     * given an deliverable model removes it from the db
     *
     * @param deliverable Deliverable instance
     */
    public void removeDeliverableData(Deliverable deliverable) {
        int did = deliverable.getDid();
        removeFromDb(primaryTable, String.format("did=%d", did));
    }

    /**
     * @param name
     * @param price
     * @return new Deliverable if insertion is successfull
     */
    public Deliverable createNewDeliverable(String name, Double price) {
        Deliverable d = null;
        int newDid = getNextIdInt(primaryTable, "did");

        DataBaseCredentials.OperationResult res = insertIntoDb(primaryTable,
                String.format(Locale.CANADA, "%d, '%s', %.2f", newDid, name, price)
        );
        // TODO: new item is added to the db but doesn't appear automatically
        if (res == DataBaseCredentials.OperationResult.inserted) {
            double priceWithTwoDecimals = Double.parseDouble(String.format("%.2f", price));
            d = new Deliverable(newDid, name, priceWithTwoDecimals);
        }
        return d;
    }

}
