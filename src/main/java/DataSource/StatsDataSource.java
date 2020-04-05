package DataSource;

import Model.User;
import oracle.ucp.common.waitfreepool.Tuple;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;

public class StatsDataSource extends AbstractDataSource {
    private static StatsDataSource instance = new StatsDataSource();
    private User currentUser = AuthenticationManager.getInstance().getCurrentUser();

    private StatsDataSource() {
    }

    public static StatsDataSource getInstance() {
        return instance;
    }

    public Tuple<Double, Integer> getPriceInfo(String startDate, String endDate) {
        try {
            String query = "SELECT *" +
                    "FROM ORDERS NATURAL JOIN ORDERCONTAINSDELIVERABLES NATURAL JOIN DELIVERABLE" +
                    "WHERE DELIVERABLE.BID = '%d' AND ORDER_DATE > '%s' AND ORDER_DATE< '%s'";
            query = String.format(query, currentUser.getAffiliatedBranch().getBid(), startDate, endDate);
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            double totalPrice = 0;
            int totalAmount = 0;
            while (rs.next()) {
                int amount = rs.getInt("amount");
                totalAmount += amount;
                totalPrice += amount * rs.getDouble("price");
            }
            return new Tuple(totalPrice, totalAmount);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    public int getOrderCount() {
        return 0;
    }

    public HashMap<String, Integer> getDriveCountOfEachVehicle() {
        HashMap<String, Integer> map = new HashMap<String, Integer>();
        return map;
    }

    public ArrayList<String> getOrderedByAllInfo(String startDate, String endDate) {
        ArrayList<String> result = new ArrayList<>();
        return result;
    }

    public ArrayList<String> getDrivenByAllInfo(String startDate, String endDate) {
        ArrayList<String> result = new ArrayList<>();
        return result;
    }
}
