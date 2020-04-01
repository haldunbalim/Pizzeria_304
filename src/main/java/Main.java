import Service.Coordinator;
import database.DatabaseConnectionHandler;


public class Main {

    public static void main(String[] args) {
        Coordinator.getInstance().start();
        Runtime.getRuntime().addShutdownHook(new Thread(new Runnable() {
            public void run() {
                DatabaseConnectionHandler.getInstance().close();
            }
        }, "Shutdown-thread"));
    }
}
