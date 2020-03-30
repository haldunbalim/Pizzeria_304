package DataSource;

import Model.*;
import Service.AuthenticationManager;

import java.util.ArrayList;
import java.util.Date;

public class OrdersDataSource extends AbstractDataSource {
    private static OrdersDataSource ourInstance = new OrdersDataSource();

    private OrdersDataSource() {
    }

    public static OrdersDataSource getInstance() {
        return ourInstance;
    }

    // TODO: Use Authmanager.currentUser info
    public ArrayList<Order> getOrdersOfUser() {
        ArrayList<Order> list = new ArrayList<>();
        User user = new User(123, "adf", "adfs", "adfa", "dfa", "adf", new Address("Istanbul", "adf", "af", 14), UserType.CUSTOMER, 15, new RestaurantBranch(132413, "fdafds", new Address("dsf", "adf", "adfa", 1234)));
        ArrayList<Deliverable> deliverables = DeliverableDataSource.getInstance().getDeliverables();
        Date date = new Date();
        list.add(new Order(1324, user, date.getTime(), deliverables, OrderState.PENDING));
        return list;
    }

    // TODO: Use Authmanager.currentUsers affiliated branch info
    public ArrayList<Order> getOrdersOfBranch() {
        ArrayList<Order> list = new ArrayList<>();
        User user = new User(123, "adf", "adfs", "adfa", "dfa", "adf", new Address("Istanbul", "adf", "af", 14), UserType.CUSTOMER, 15, new RestaurantBranch(132413, "fdafds", new Address("dsf", "adf", "adfa", 1234)));
        ArrayList<Deliverable> deliverables = DeliverableDataSource.getInstance().getDeliverables();
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
    }
}
