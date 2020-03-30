package Service;


import Model.Address;
import Model.RestaurantBranch;
import Model.User;
import Model.UserType;
import ViewController.CustomerFlow.CustomerTabs;
import ViewController.EmployeeFlow.EmployeeTabs;
import ViewController.LoginViewController;
import ViewController.ManagerFlow.ManagerTabs;
import ViewController.SetProfilePage;

import javax.swing.*;

public class Coordinator {
    private static final ScreenEnum initialPage = ScreenEnum.EMPLOYEE_TABS;
    private static Coordinator instance = new Coordinator();
    private JFrame frame = new JFrame();
    private ScreenEnum currentScreen;

    private Coordinator() {
    }

    public static Coordinator getInstance() {
        return instance;
    }

    public void start() {
        frame.setVisible(true);
        frame.setTitle("Pizzeria");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setBounds(100, 100, Constants.SCREEN_WIDTH, Constants.SCREEN_HEIGHT);
        frame.setResizable(false);
        //following line is for testing
        AuthenticationManager.getInstance().setCurrentUser(new User(123, "adf", "adfs", "adfa", "dfa", "adf", new Address("Istanbul", "adf", "af", 14), UserType.CUSTOMER, 15, new RestaurantBranch(132413, "fdafds", new Address("dsf", "adf", "adfa", 1234))));
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

}

