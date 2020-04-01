import Service.Coordinator;
import database.DatabaseConnectionHandler;

import java.util.Locale;


public class Main {

    public static void main(String[] args) {
        Locale localeFromBuilder = new Locale.Builder().setLanguage("en").setRegion("CA").build();
        Coordinator.getInstance().start();
        Runtime.getRuntime().addShutdownHook(new Thread(new Runnable() {
            public void run() {
                DatabaseConnectionHandler.getInstance().close();
            }
        }, "Shutdown-thread"));
    }
}
