package ViewController;

import ViewModel.AbstractViewModel;

import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;

public abstract class MyAbstractTableModel extends AbstractTableModel {
    protected String[] columnNames;
    protected ArrayList<AbstractViewModel> data = new ArrayList<>();

    public int getColumnCount() {
        return columnNames.length;
    }

    public int getRowCount() {
        return data.size();
    }

    public String getColumnName(int col) {
        return columnNames[col];
    }

    public Object getValueAt(int row, int col) {
        return data.get(row).getColumnView(col);
    }

    public void setValueAt(Object value, int row, int col) {
        this.data.get(row).setValueAt(col, value);
        fireTableCellUpdated(row, col);
    }

    public void remove(int row) {
        this.data.remove(row);
        this.fireTableRowsDeleted(row, row);
    }
}
