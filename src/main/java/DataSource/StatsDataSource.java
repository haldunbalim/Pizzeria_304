package DataSource;

import Model.User;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

public class StatsDataSource extends AbstractDataSource {
    private static StatsDataSource instance = new StatsDataSource();
    private User currentUser = AuthenticationManager.getInstance().getCurrentUser();

    private StatsDataSource() {
    }

    public static StatsDataSource getInstance() {
        return instance;
    }

    public Double getPriceInfo(Date startDate, Date endDate) {
        try {
            String query = "SELECT AMOUNT*PRICE\n" +
                    "FROM ORDERS NATURAL JOIN ORDERCONTAINSDELIVERABLES NATURAL JOIN DELIVERABLE\n" +
                    "WHERE DELIVERABLE.BID = ? AND ORDER_DATE > ? AND ORDER_DATE< ?";
            PreparedStatement stmt = connection.prepareStatement(query);
            stmt.setLong(1, currentUser.getAffiliatedBranch().getBid());
            stmt.setDate(2, new java.sql.Date(startDate.getTime()));
            stmt.setDate(3, new java.sql.Date(endDate.getTime()));
            ResultSet rs = stmt.executeQuery();
            double totalPrice = 0;
            while (rs.next()) {
                totalPrice += rs.getDouble("AMOUNT*PRICE");
            }
            return totalPrice;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    public int getOrderCount(Date startDate, Date endDate) {
        try {
            String query = "SELECT DISTINCT ORDER_ID\n" +
                    "FROM ORDERS NATURAL JOIN ORDERCONTAINSDELIVERABLES NATURAL JOIN DELIVERABLE\n" +
                    "WHERE DELIVERABLE.BID = ? AND ORDER_DATE > ? AND ORDER_DATE< ?";
            PreparedStatement stmt = connection.prepareStatement(query);
            stmt.setLong(1, currentUser.getAffiliatedBranch().getBid());
            stmt.setDate(2, new java.sql.Date(startDate.getTime()));
            stmt.setDate(3, new java.sql.Date(endDate.getTime()));
            ResultSet rs = stmt.executeQuery();
            int count = 0;
            while (rs.next())
                count++;
            return count;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return 0;
        }
    }

    public HashMap<String, Integer> getDriveCountOfEachVehicle(Date startDate, Date endDate) {
        HashMap<String, Integer> map = new HashMap<>();
        try {
            String query = "SELECT V.LICENSE_PLATE, COUNT(*)\n" +
                    "FROM ((ORDERS O INNER JOIN DRIVERCARRIESORDER DCO ON O.ORDER_ID=DCO.ORDER_ID) INNER JOIN VEHICLE V ON V.LICENSE_PLATE=DCO.LICENSE_PLATE)\n" +
                    "WHERE V.BID = ? AND ORDER_DATE > ? AND ORDER_DATE< ?\n" +
                    "GROUP BY V.LICENSE_PLATE";
            PreparedStatement stmt = connection.prepareStatement(query);
            stmt.setLong(1, currentUser.getAffiliatedBranch().getBid());
            stmt.setDate(2, new java.sql.Date(startDate.getTime()));
            stmt.setDate(3, new java.sql.Date(endDate.getTime()));
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                map.put(rs.getString("LICENSE_PLATE"), rs.getInt("COUNT(*)"));
            }
            return map;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return map;
        }
    }

    public ArrayList<String> getDrivenByAllInfo(Date startDate, Date endDate) {
        ArrayList<String> result = new ArrayList<>();
        try {
            String query = "SELECT *\n" +
                    "FROM VEHICLE V\n" +
                    "WHERE NOT EXISTS (SELECT U.USER_ID\n" +
                    "                  FROM USERS U INNER JOIN EMPLOYEEWORKSATBRANCH E ON U.USER_ID = E.USER_ID\n" +
                    "                  WHERE NOT EXISTS (SELECT DCO.LICENSE_PLATE\n" +
                    "                                    FROM DRIVERCARRIESORDER DCO INNER JOIN ORDERS O ON O.ORDER_ID=DCO.ORDER_ID\n" +
                    "                                    WHERE U.USER_ID=DCO.USER_ID AND DCO.LICENSE_PLATE=V.LICENSE_PLATE\n" +
                    "                                      AND O.ORDER_DATE>? AND O.ORDER_DATE< ?)\n" +
                    "                        AND E.BID=? AND U.USER_TYPE='employee')\n" +
                    "      AND V.BID=?";
            PreparedStatement stmt = connection.prepareStatement(query);
            stmt.setDate(1, new java.sql.Date(startDate.getTime()));
            stmt.setDate(2, new java.sql.Date(endDate.getTime()));
            stmt.setLong(3, currentUser.getAffiliatedBranch().getBid());
            stmt.setLong(4, currentUser.getAffiliatedBranch().getBid());
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                result.add(rs.getString("LICENSE_PLATE"));
            }
            return result;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return result;
        }
    }

    public double getMaxPriceOrder(Date startDate, Date endDate) {
        try {
            String query = "SELECT MAX(AMOUNT*PRICE) AS MAX\n" +
                    "FROM ORDERS NATURAL JOIN ORDERCONTAINSDELIVERABLES NATURAL JOIN DELIVERABLE\n" +
                    "WHERE DELIVERABLE.BID = ? AND ORDER_DATE > ? AND ORDER_DATE< ?";
            PreparedStatement stmt = connection.prepareStatement(query);
            stmt.setLong(1, currentUser.getAffiliatedBranch().getBid());
            stmt.setDate(2, new java.sql.Date(startDate.getTime()));
            stmt.setDate(3, new java.sql.Date(endDate.getTime()));
            ResultSet rs = stmt.executeQuery();
            double totalPrice = 0;
            while (rs.next()) {
                totalPrice += rs.getDouble("MAX");
            }
            return totalPrice;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return 0;
        }
    }
}
