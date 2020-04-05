package DataSource;

import Model.*;
import Service.AuthenticationManager;
import database.DataBaseCredentials;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

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

    // TODO: Use Authmanager.currentUser info
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
                // TODO: get order deliverables
                ArrayList<Deliverable> deliverables = DeliverableDataSource.getInstance().getDeliverablesOfCurrentBranch();

                // TODO: change date to DAte format in Model.Order
                list.add(new Order(oid, currentUser, date.getTime(), deliverables, os));

            }
            rs.close();
            stmt.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return list;
    }

    // TODO: Use Authmanager.currentUsers affiliated branch info
    public ArrayList<Order> getOrdersOfBranch() {
        ArrayList<Order> list = new ArrayList<>();
        User user = new User(123, "adf", "adfs", "adfa", "dfa", "adf", new Address("Istanbul", "adf", "af", 14), UserType.CUSTOMER, 15, new RestaurantBranch(132413, "fdafds", new Address("dsf", "adf", "adfa", 1234)));
        ArrayList<Deliverable> deliverables = DeliverableDataSource.getInstance().getDeliverablesOfCurrentBranch();
        Date date = new Date();
        list.add(new Order(1324, user, date.getTime(), deliverables, OrderState.PENDING));
        return list;
    }

    public Order createOrder(ArrayList<Deliverable> deliverables) {
        Date date = new Date();
        return new Order(12413, AuthenticationManager.getInstance().getCurrentUser(), date.getTime(), deliverables, OrderState.PENDING);
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
        } catch (SQLException e) {
            System.out.println(DataBaseCredentials.EXCEPTION_TAG + DataBaseCredentials.updateError);
        }

    }
}
