package Reusable;

import javax.swing.*;
import javax.swing.table.TableCellRenderer;
import java.awt.*;

public class WrapTextCellRenderer extends JTextArea implements TableCellRenderer {
    public WrapTextCellRenderer() {
        setOpaque(true);
        setLineWrap(true);
        setWrapStyleWord(true);
    }

    public Component getTableCellRendererComponent(JTable table, Object value,
                                                   boolean isSelected, boolean hasFocus, int row, int column) {

        this.setText(value == null ? "" : value.toString());
        return this;
    }

}
