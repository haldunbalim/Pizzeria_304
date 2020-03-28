package Reusable;

import javax.swing.*;
import java.awt.*;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

public class PlaceholderFocusListener implements FocusListener {

    private JTextField textField;
    private String placeholderText;

    public PlaceholderFocusListener(JTextField textField, String placeholderText) {
        this.textField = textField;
        this.placeholderText = placeholderText;
    }

    @Override
    public void focusGained(FocusEvent e) {
        if (textField.getText().equals(placeholderText)) {
            textField.setText("");
            textField.setForeground(Color.BLACK);
        }
    }

    @Override
    public void focusLost(FocusEvent e) {
        if (textField.getText().isEmpty()) {
            textField.setForeground(Color.GRAY);
            textField.setText(placeholderText);
        }
    }


}
