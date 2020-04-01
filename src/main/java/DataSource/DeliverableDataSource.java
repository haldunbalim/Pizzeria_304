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
        String values = String.format(Locale.CANADA, "name='%s', price=%.2f WHERE did=%d", name, price, did);
        updateColumnValues(primaryTable, values);
    }

    // given an deliverable model removes it from the db
    public void removeDeliverableData(Deliverable deliverable) {
        int did = deliverable.getDid();
        removeFromDb(primaryTable, String.format("did=%d", did));

        // TODO: if db deletion fails don't allow the object to be deleted
        // We get all the deliverable info from the db this shouldn't be a problem -gs
    }

    // given props create a new deliverable in db and return
    public Deliverable createNewDeliverable(String name, Double price) {
        Deliverable d = null;
        int newDid = getNextId(primaryTable, "did");

        DataBaseCredentials.OperationResult res = insertIntoDb(primaryTable,
                String.format(Locale.CANADA, "%d, '%s', %.2f", newDid, name, price)
        );
        if (res == DataBaseCredentials.OperationResult.inserted) {
            d = new Deliverable(newDid, name, price);
        }
        return d;
    }

}
