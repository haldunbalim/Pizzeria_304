package ViewController.ManagerFlow;

import DataSource.StatsDataSource;
import ViewController.AbstractViewController;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

public class StatsViewController extends AbstractViewController {
    private static StatsViewController instance = new StatsViewController();
    protected JPanel mainPanel;
    private JTextField startDateTextField;
    private JTextField endDateTextField;
    private JCheckBox priceCheckBox;
    private JCheckBox vehicleCheckBox;
    private JCheckBox orderCountCheckBox;
    private JCheckBox drivenByAllCheckBox;
    private JButton fetchStatsButton;
    private JTextArea textArea;
    private JLabel errorLabel;
    private JCheckBox highestPriceOrderCheckBox;
    private StatsDataSource dataSource = StatsDataSource.getInstance();

    private StatsViewController() {
        configureUI();
    }

    public static StatsViewController getInstance() {
        return instance;
    }

    private void configureUI() {
        errorLabel.setText("Enter date, put ticks in checkboxes and press the fetch button");
        fetchStatsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                SimpleDateFormat dtf = new SimpleDateFormat("dd/MM/yyyy");
                Date startDate, endDate;
                try {
                    startDate = dtf.parse(startDateTextField.getText());
                    endDate = dtf.parse(endDateTextField.getText());
                } catch (ParseException exp) {
                    errorLabel.setText("Date format is not valid");
                    return;
                }
                String st = String.format("Fetching data from %s to %s\n", startDate, endDate);
                if (priceCheckBox.isSelected()) {
                    double price = dataSource.getPriceInfo(startDate, endDate);
                    st += "Total revenue from sales is " + price + " dollars\n";
                }

                if (highestPriceOrderCheckBox.isSelected()) {
                    st += String.format("Highest revenue from single sale is %.2f dollars\n", dataSource.getMaxPriceOrder(startDate, endDate));
                }

                if (orderCountCheckBox.isSelected()) {
                    st += String.format("There are %d orders\n", dataSource.getOrderCount(startDate, endDate));
                }

                if (vehicleCheckBox.isSelected()) {
                    HashMap<String, Integer> map = dataSource.getDriveCountOfEachVehicle(startDate, endDate);
                    for (String licensePlate : map.keySet()) {
                        st += String.format("The vehicle with license plate %s is used %d times\n", licensePlate, map.get(licensePlate));
                    }
                }

                if (drivenByAllCheckBox.isSelected()) {
                    ArrayList<String> result = dataSource.getDrivenByAllInfo(startDate, endDate);
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

    @Override
    public JPanel getMainPanel() {
        return mainPanel;
    }
}
