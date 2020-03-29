package ViewController;

import javax.swing.*;

public class SetProfilePage extends AbstractViewController {
    private static SetProfilePage instance = new SetProfilePage();
    private JPanel mainPanel;

    public static SetProfilePage getInstance() {
        return instance;
    }

    public JPanel getMainPanel() {
        return mainPanel;
    }
}
