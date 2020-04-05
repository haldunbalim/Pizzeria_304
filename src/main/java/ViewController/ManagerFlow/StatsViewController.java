package ViewController.ManagerFlow;

import DataSource.StatsDataSource;
import ViewController.AbstractViewController;
import oracle.ucp.common.waitfreepool.Tuple;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

public class StatsViewController extends AbstractViewController {
    private static StatsViewController instance = new StatsViewController();
    protected JPanel mainPanel;
    private JFormattedTextField startDateTextField;
    private JFormattedTextField endDateTextField;
    private JCheckBox priceCheckBox;
    private JCheckBox vehicleCheckBox;
    private JCheckBox amountCheckBox;
    private JCheckBox orderedByAllCheckBox;
    private JCheckBox drivenByAllCheckBox;
    private JButton fetchStatsButton;
    private JTextArea textArea;
    private JLabel errorLabel;
    private StatsDataSource dataSource = StatsDataSource.getInstance();

    private StatsViewController() {
        configureUI();
    }

    public static StatsViewController getInstance() {
        return instance;
    }

    private void configureUI() {
        DateFormat df = new SimpleDateFormat("MM/dd/yyyy");
        startDateTextField = new JFormattedTextField(df);
        endDateTextField = new JFormattedTextField(df);

        startDateTextField.setValue(new Date());
        endDateTextField.setValue(new Date());

        fetchStatsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (fieldsAreInvalid()) {
                    errorLabel.setText("Date format is not valid");
                    return;
                }
                String st = String.format(startDateTextField.getText(), endDateTextField.getText());
                if (priceCheckBox.isSelected()) {
                    Tuple<Double, Integer> tuple = dataSource.getPriceInfo(startDateTextField.getText(), endDateTextField.getText());
                    double price = tuple.get1();
                    int amount = tuple.get2();
                    st += "Total number of products sold is" + amount + "\n";
                    st += "Total money from sales is " + price + " dollars\n";
                }

                if (amountCheckBox.isSelected()) {
                    st += String.format("There are %d orders\n", dataSource.getOrderCount());
                }

                if (vehicleCheckBox.isSelected()) {
                    HashMap<String, Integer> map = dataSource.getDriveCountOfEachVehicle();
                    for (String licensePlate : map.keySet()) {
                        st += String.format("The vehicle with license plate %s is used %d times\n", licensePlate, map.get(licensePlate));
                    }
                }

                if (orderedByAllCheckBox.isSelected()) {
                    ArrayList<String> result = dataSource.getOrderedByAllInfo(startDateTextField.getText(), endDateTextField.getText());
                    if (result.size() == 0) {
                        st += "There is no item which is ordered by every customer\n";
                    } else {
                        st += String.format("The items with following names are ordered by every customer: %s\n", String.join(", ", result));
                    }
                }

                if (drivenByAllCheckBox.isSelected()) {
                    ArrayList<String> result = dataSource.getDrivenByAllInfo(startDateTextField.getText(), endDateTextField.getText());
                    if (result.size() == 0) {
                        st += "There is no vehicle which is driven by every employee\n";
                    } else {
                        st += String.format("The vehicles with following license plates are ordered by every employee: %s\n", String.join(", ", result));
                    }
                }

                textArea.setText(st);
            }
        });

    }

    private boolean fieldsAreInvalid() {
        return !fieldIsValid(startDateTextField) || !fieldIsValid(endDateTextField);
    }

    private boolean fieldIsValid(JFormattedTextField jtf) {
        String[] dateParts = jtf.getText().split("/");
        if (dateParts.length != 3) {
            return false;
        }
        try {
            int day = Integer.parseInt(dateParts[0]);
            if (day < 1 || day > 31) {
                return false;
            }
        } catch (NumberFormatException e) {
            return false;
        }
        try {
            int month = Integer.parseInt(dateParts[1]);
            if (month < 1 || month > 12) {
                return false;
            }
        } catch (NumberFormatException e) {
            return false;
        }
        try {
            int year = Integer.parseInt(dateParts[2]);
            if (year < 1000 || year > 3000) {
                return false;
            }
        } catch (NumberFormatException e) {
            return false;
        }
        return true;
    }

    @Override
    public JPanel getMainPanel() {
        return mainPanel;
    }
}
