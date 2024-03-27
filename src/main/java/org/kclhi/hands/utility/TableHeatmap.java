package org.kclhi.hands.utility;
import javax.swing.JFrame;
import javax.swing.JComponent;
import javax.swing.JTable;
import javax.swing.table.*;

import org.kclhi.hands.utility.output.HiderRecord;

import java.awt.Graphics;
import java.util.ArrayList;
import java.awt.Color;
import java.awt.Component;




public class TableHeatmap {

    public void generateHeatMapFrame(ArrayList<HiderRecord> recordsFromList) {
        JFrame frame = new JFrame("Circular Heatmap (Unfinished, please work on it)");
        // create a demo table 10 x 10 cells
        JTable table = new JTable(10, 10);
        frame.setContentPane(table);

        // fill in some random data
        for (int row = 0; row < 10; row++) {
            for (int col = 0; col < 10; col++) {
                table.setValueAt((int) (Math.random() * 10), row, col);
            }
        }

        // set our custom TableCellRenderer
        table.setDefaultRenderer(Object.class, new HeatmapCellRenderer());
        table.setRowHeight(30);
        frame.getContentPane().setBackground(Color.BLACK);
        // show the window
        frame.pack();
        frame.setVisible(true);
    }

    private static class HeatmapCellRenderer implements TableCellRenderer {

        private final DotRenderer renderer = new DotRenderer();

        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            if (value instanceof Integer) {
                this.renderer.setValue((Integer) value);
                return this.renderer;
            }
            return null;
        }
    }

    private static class DotRenderer extends JComponent {
        private int value;

        public void setValue(int value) {
            this.value = value;
        }

        @Override
        protected void paintComponent(Graphics g) {
            g.setColor(Color.YELLOW);
            g.fillRect(0, 0, this.getWidth(), this.getHeight());
            g.setColor(Color.ORANGE);
            int centerX = this.getWidth() / 2;
            int centerY = this.getHeight() / 2;
            g.fillOval(centerX - this.value, centerY - this.value, this.value * 2, this.value * 2);

        }

    }

}
