package ViewController;

import javax.swing.*;
import java.awt.*;

public abstract class AbstractTableViewController extends AbstractViewController {

    protected JTable table;
    protected MyAbstractTableModel tableModel;

    protected void addTable() {
        JScrollPane scrollPane = new JScrollPane(table);
        table.setFillsViewportHeight(true);
        getMainPanel().add(scrollPane, BorderLayout.CENTER);
    }

}
