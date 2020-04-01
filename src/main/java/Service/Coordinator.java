package Service;


import ViewController.CustomerFlow.CustomerTabs;
import ViewController.EmployeeFlow.EmployeeTabs;
import ViewController.LoginViewController;
import ViewController.ManagerFlow.ManagerTabs;
import ViewController.SetProfilePage;
import database.DatabaseConnectionHandler;

import javax.swing.*;

public class Coordinator {
    private static final ScreenEnum initialPage = ScreenEnum.LOGIN;
    private static Coordinator instance = new Coordinator();
    private JFrame frame = new JFrame();
    private ScreenEnum currentScreen;
    DatabaseConnectionHandler dbHandler = DatabaseConnectionHandler.getInstance();

    private Coordinator() {
    }

    public static Coordinator getInstance() {
        return instance;
    }

    public void start() {
        dbHandler.connectToDatabase();

        frame.setVisible(true);
        frame.setTitle("Pizzeria");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setBounds(100, 100, Constants.SCREEN_WIDTH, Constants.SCREEN_HEIGHT);
        frame.setResizable(false);
        openScreen(initialPage);
    }

    public void openScreen(ScreenEnum screen) {

        switch (screen) {
            case LOGIN:
                frame.setContentPane(LoginViewController.getInstance().getMainPanel());
                break;
            case ORDER:
                frame.setContentPane(SetProfilePage.getInstance().getMainPanel());
                break;
            case MANAGER_TABS:
                frame.setContentPane(ManagerTabs.getInstance());
                break;
            case SET_PROFILE_PAGE:
                frame.setContentPane(SetProfilePage.getInstance().getMainPanel());
                break;
            case CUSTOMER_TABS:
                frame.setContentPane(CustomerTabs.getInstance());
                break;
            case EMPLOYEE_TABS:
                frame.setContentPane(EmployeeTabs.getInstance());
                break;
        }
        frame.revalidate();
        currentScreen = screen;
    }

    public ScreenEnum getCurrentScreen() {
        return currentScreen;
    }
}

