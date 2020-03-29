package Service;


import ViewController.LoginViewController;
import ViewController.ManagerFlow.ManagerTabs;
import ViewController.SetProfilePage;

import javax.swing.*;

public class Coordinator {
    private static Coordinator instance = new Coordinator();
    private JFrame frame = new JFrame();
    private static final ScreenEnum initialPage = ScreenEnum.LOGIN;
    private ScreenEnum currentScreen;

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

        switch (screen){
            case LOGIN:
                frame.setContentPane(LoginViewController.getInstance().getMainPanel());
                break;
            case ORDER:
                frame.setContentPane(SetProfilePage.getInstance().getMainPanel());
                break;
            case MANAGER_TABS:
                frame.setContentPane(ManagerTabs.getInstance());
                break;
        }
        frame.revalidate();
        currentScreen = screen;
    }

}

