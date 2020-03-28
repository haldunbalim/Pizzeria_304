package DataSource;

public class OrdersDataSource {
    private static OrdersDataSource ourInstance = new OrdersDataSource();

    public static OrdersDataSource getInstance() {
        return ourInstance;
    }

    private OrdersDataSource() {
    }


}
