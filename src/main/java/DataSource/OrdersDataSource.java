package DataSource;

public class OrdersDataSource extends AbstractDataSource {
    private static OrdersDataSource ourInstance = new OrdersDataSource();

    private OrdersDataSource() {
    }

    public static OrdersDataSource getInstance() {
        return ourInstance;
    }


}
