import Service.Coordinator;
import database.DatabaseConnectionHandler;

import java.util.Locale;


public class Main {

    public static void main(String[] args) {
        // necessary for my Turkish comp
        Locale.setDefault(Locale.CANADA);
        Coordinator.getInstance().start();
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            DatabaseConnectionHandler.getInstance().close();
        }, "Shutdown-thread"));
    }
}
