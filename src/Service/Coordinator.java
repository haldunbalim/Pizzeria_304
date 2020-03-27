package Service;


import ViewController.AbstractViewController;
import ViewController.LoginViewController;

import javax.swing.*;

public class Coordinator {
    private static Coordinator instance = new Coordinator();;
    private JFrame frame = new JFrame();
    public ScreenEnum currentScreen;
    private static final ScreenEnum initialPage = ScreenEnum.LOGIN;

    public static Coordinator getInstance() {
        return instance;
    }

    private Coordinator() {
    }

    public void start(){
        frame.setVisible(true);
        frame.setTitle("The Hateful 6");
        frame.setBounds(0,0, Constants.SCREEN_WIDTH, Constants.SCREEN_HEIGHT);
        frame.setResizable(false);
        frame.setFocusable(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        openScreen(ScreenEnum.LOGIN);
    }


    public void openScreen(ScreenEnum screen){
        AbstractViewController panel = getPanel(screen);

        frame.setContentPane(panel);
        frame.revalidate();
        currentScreen = screen;
    }


    private AbstractViewController getPanel(ScreenEnum screen){
        switch (screen){
            case LOGIN:
                return LoginViewController.getInstance();
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
            case VEHICLE_ASSIGNMENT:
                return null;
        }
        return null;
    }
}

