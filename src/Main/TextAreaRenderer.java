package Main;

import javax.swing.*;
import javax.swing.table.TableCellRenderer;
import java.awt.*;

/**
 * The TextAreaRenderer class is a custom cell renderer for JTable
 * that uses a JTextArea to enable multi-line and word-wrapped text display
 * within table cells.
 * This renderer adjusts the row height dynamically based on the content size,
 * making it ideal for displaying descriptions or long text values inside a table.
 */
public class TextAreaRenderer extends JTextArea implements TableCellRenderer {

    public TextAreaRenderer() {
        setLineWrap(true);
        setWrapStyleWord(true);
        setOpaque(true);
        setEditable(false);
        setFocusable(false);
        setBorder(null);
        setHighlighter(null);
        setMargin(new Insets(5, 5, 5, 5));
        setFont(new Font("Arial", Font.PLAIN, 12));
        setAlignmentY(Component.CENTER_ALIGNMENT);
        setAlignmentX(Component.CENTER_ALIGNMENT);
    }

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value,
                                                   boolean isSelected, boolean hasFocus, int row, int column) {
        setText(value == null ? "" : value.toString());

        // Wrap the height properly
        setSize(table.getColumnModel().getColumn(column).getWidth(), Short.MAX_VALUE);
        int height = getPreferredSize().height;
        if (table.getRowHeight(row) != height) {
            table.setRowHeight(row, height);
        }

        setAlignmentX(CENTER_ALIGNMENT);
        setAlignmentY(CENTER_ALIGNMENT);
        setCaretPosition(0); // top align

        return this;
    }
}
