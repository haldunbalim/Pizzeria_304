package ViewController;

import javax.swing.*;

public abstract class AbstractViewController {
    protected JPanel mainPanel;

    protected AbstractViewController() {
        mainPanel.setFocusable(true);
        configureUI();
    }

    public JPanel getMainPanel() {
        return mainPanel;
    }

    public abstract void configureUI();
}
