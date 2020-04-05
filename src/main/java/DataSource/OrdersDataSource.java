package DataSource;

import Model.*;
import database.DataBaseCredentials;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;

import static java.time.LocalDate.now;

public class OrdersDataSource extends AbstractDataSource {
    private static OrdersDataSource ourInstance = new OrdersDataSource();
    private static HashMap<String, OrderState> orderStateHashMap = new HashMap<String, OrderState>() {
        {
            put("pending", OrderState.PENDING);
            put("delivered", OrderState.DELIVERED);
            put("in_delivery", OrderState.IN_DELIVERY);
        }
    };

    private static HashMap<OrderState, String> orderStateHashMapReverse = new HashMap<OrderState, String>() {
        {
            put(OrderState.PENDING, "pending");
            put(OrderState.DELIVERED, "delivered");
            put(OrderState.IN_DELIVERY, "in_delivery");
        }
    };

    private OrdersDataSource() {
        primaryTable = "Orders";
    }

    public static OrdersDataSource getInstance() {
        return ourInstance;
    }

    /**
     * @return orders of current user
     */
    public ArrayList<Order> getOrdersOfUser() {
        ArrayList<Order> list = new ArrayList<>();
        User currentUser = AuthenticationManager.getInstance().getCurrentUser();
        try {
            Statement stmt = connection.createStatement();
            String query = String.format("SELECT * FROM %s natural join Users WHERE user_id=%d", primaryTable, currentUser.getUID());
            ResultSet rs = stmt.executeQuery(query);

            while (rs.next()) {
                long oid = rs.getLong("order_id");
                Date date = rs.getDate("order_date");
                String state = rs.getString("order_state");
                OrderState os = orderStateHashMap.get(state);
                ArrayList<Deliverable> deliverables = DeliverableDataSource.getInstance().getDeliverablesInOrder(oid);
                list.add(new Order(oid, currentUser, date.getTime(), deliverables, os));
            }
            rs.close();
            stmt.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return list;
    }

    /**
     * auxilary function for getOrdersOfBranch
     *
     * @param oid order_id
     * @return the user of the order
     */
    protected User getUserFromOrder(Long oid) {
        User u = null;
        try {
            Statement stmt = connection.createStatement();
            String query = String.format("SELECT user_id FROM %s WHERE order_id=%d", primaryTable, oid);
            ResultSet rs = stmt.executeQuery(query);

            while (rs.next()) {
                long uid = rs.getLong("user_id");
                u = UserDataSource.getInstance().getUser(uid);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return u;
    }

    /**
     * @return the orders associated with current branch
     */
    public ArrayList<Order> getOrdersOfBranch() {
        ArrayList<Order> list = new ArrayList<>();
        User currentUser = AuthenticationManager.getInstance().getCurrentUser();
        Long bid = currentUser.getAffiliatedBranch().getBid();
        try {
            Statement stmt = connection.createStatement();
            String query = String.format("SELECT * FROM %s natural join Branch WHERE bid=%d", primaryTable, bid);
            ResultSet rs = stmt.executeQuery(query);

            while (rs.next()) {
                long oid = rs.getLong("order_id");
                Date date = rs.getDate("order_date");
                String state = rs.getString("order_state");
                OrderState os = orderStateHashMap.get(state);
                ArrayList<Deliverable> deliverables = DeliverableDataSource.getInstance().getDeliverablesInOrder(oid);
                list.add(new Order(oid, getUserFromOrder(oid), date.getTime(), deliverables, os));
            }
            rs.close();
            stmt.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return list;
    }

    protected HashMap<Deliverable, Integer> groupDeliverables(ArrayList<Deliverable> deliverables) {
        HashMap<Deliverable, Integer> hm = new HashMap<>();
        for (Deliverable d : deliverables) {
            if (hm.containsKey(d))
                hm.put(d, hm.get(d) + 1);
            else
                hm.put(d, 1);
        }
        return hm;
    }

    public Order createOrder(ArrayList<Deliverable> deliverables) {
        LocalDate date = now();
        long milis = System.currentTimeMillis();
        User user = AuthenticationManager.getInstance().getCurrentUser();
        long oid = getNextIdLong("Orders", "order_id");
        String orderInsert = String.format(
                "%d, %d, TO_DATE('%s'), '%s'",
                oid,
                user.getUID(),
                parseDate(date),
                orderStateHashMapReverse.get(OrderState.PENDING)
        );
        DataBaseCredentials.OperationResult res1 = insertIntoDb("Orders", orderInsert);

        HashMap<Deliverable, Integer> deliverableAmounts = groupDeliverables(deliverables);
        for (Deliverable d : deliverableAmounts.keySet()) {
            String deliverablesInsert = String.format("%d, %d, %d", oid, d.getDid(), deliverableAmounts.get(d));
            DataBaseCredentials.OperationResult res2 = insertIntoDb("OrderContainsDeliverables", deliverablesInsert);
        }

        return new Order(oid, AuthenticationManager.getInstance().getCurrentUser(),
                milis, deliverables, OrderState.PENDING);
    }


    // Set vehicle unavailable as well if newState == IN_DELIVERY
    // Set vehicle available as well if newState == DELIVERED
    public void changeOrderState(Order selectedOrder, Vehicle vehicle, OrderState newState) {
        long oid = selectedOrder.getOid();
        boolean vehicleAvail = newState == OrderState.DELIVERED || newState == OrderState.PENDING;
        try {
            PreparedStatement ps = connection.prepareStatement("UPDATE Orders SET ORDER_STATE = ? " +
                    "WHERE order_id = ?");

            ps.setString(1, orderStateHashMapReverse.get(newState));
            ps.setLong(2, oid);
            VehicleDataSource.getInstance().setVehicleAvailability(vehicle, vehicleAvail);

            ps.close();
            connection.commit();
        } catch (SQLException e) {
            System.out.println(DataBaseCredentials.EXCEPTION_TAG + DataBaseCredentials.updateError);
        }

    }
}
