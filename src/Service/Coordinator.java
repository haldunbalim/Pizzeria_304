package Service;


import ViewController.LoginViewController;
import ViewController.ManagerFlow.EditDeliverablesViewController;

import javax.swing.*;

public class Coordinator {
    private static Coordinator instance = new Coordinator();
    private JFrame frame = new JFrame();
    public ScreenEnum currentScreen;
    private static final ScreenEnum initialPage = ScreenEnum.EDIT_DELIVERABLES_LIST;

    public static Coordinator getInstance() {
        return instance;
    }

    private Coordinator() {
    }

    public void start(){
        frame.setVisible(true);
        frame.setTitle("Pizzeria");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setBounds(100, 100, Constants.SCREEN_WIDTH, Constants.SCREEN_HEIGHT);
        frame.setResizable(false);
        openScreen(initialPage);
    }


    public void openScreen(ScreenEnum screen){
        JPanel panel = getPanel(screen);
        frame.setContentPane(panel);
        frame.revalidate();
        currentScreen = screen;
    }


    private JPanel getPanel(ScreenEnum screen) {
        switch (screen){
            case LOGIN:
                return LoginViewController.getInstance().mainPanel;
            case ORDER:
                return null;
            case EMPLOYEE_LIST:
                return null;
            case CUSTOMER_PROFILE:
                return null;
            case EMPLOYEE_PROFILE:
                return null;
            case ORDER_ASSIGNMENT:
                return null;
            case DELIVERABLES_LIST:
                return null;
            case EDIT_DELIVERABLES_LIST:
                return EditDeliverablesViewController.getInstance().mainPanel;
            case VEHICLE_ASSIGNMENT:
                return null;
        }
        return null;
    }
}

