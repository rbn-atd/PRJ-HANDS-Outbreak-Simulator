package org.kclhi.hands.utility;
import javax.swing.JFrame;
import javax.swing.JComponent;
import javax.swing.JTable;
import javax.swing.table.*;

import java.awt.Graphics;
import java.awt.Color;
import java.awt.Component;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException; 
import java.nio.file.*;
import java.util.concurrent.atomic.AtomicReference;

public class TableHeatmap {

    public void generateHeatMapFrame(String filePath) {
        JFrame frame = new JFrame("Circular Heatmap (Unfinished, please work on it)");

        String[] filePathSplit = filePath.split("/");
        String fileNameTagged = filePathSplit[1]+".csv";
        String completePath = getFilePath(fileNameTagged);
        System.out.println(completePath);

        // initialise counts hash map to store node visit frequency
        // initialise numberOfNodes to store number of nodes in simulation
        Map<String, Integer> counts = new HashMap<>();
        Integer numberOfNodes = 0;

        try{  

            CSVReader reader = new CSVReader(new FileReader(new File(completePath)));
            System.out.println("File Found.");
            String[] line = reader.readNext();

            for (int i = 0; i < line.length; i++) {
                String entry = line[i];
                if (entry.equals(" {NumberOfNodes")) {
                    String value = line[i+1];
                    if (value.endsWith("}")) {
                        value = value.substring(0, value.length() - 1);
                    }
                    numberOfNodes = Integer.parseInt(value);
                    System.out.println("Number of nodes: " + numberOfNodes);
                    break;
                } 
            }

            while ((line = reader.readNext()) != null && line[0].equals("R")) {
                String nodesVisited = line[18];
                nodesVisited = nodesVisited.substring(1, nodesVisited.length() - 1); // remove the curly braces
                String[] nodes = nodesVisited.split(" "); // split the line into items
                String node = nodes[nodes.length - 1];
                String[] parts = node.split("="); // split the item into variable and count
                String variable = parts[0];
                int count = Integer.parseInt(parts[1]);
                if (line[20].equals(" 1.0")){
                    counts.put(variable, counts.getOrDefault(variable, 0) + count);
                }
            }

            System.out.println("Node counts:" + counts);

            reader.close();
        } catch (IOException|CsvValidationException u){
            System.out.println("Something went wrong when reading the CSV File.");
            u.printStackTrace();
        }

        // create a demo table 10 x 10 cells
        JTable table = new JTable(numberOfNodes, numberOfNodes);
        frame.setContentPane(table);

        
        // fill in some random data (delete this for actual data)
        for (int i = 0; i < numberOfNodes; i++) {
            // Get the value associated with the current node
            Integer value = counts.get("v" + i);
            if (value != null) {
                // If the value exists, set it in the table
                table.setValueAt(value/50, i, i);
            } else {
                // If the value does not exist, you can set a default value or leave the cell empty
                table.setValueAt(0, i, i); // Here, I'm setting a default value of 0
            }
        }

        // set our custom TableCellRenderer
        table.setDefaultRenderer(Object.class, new HeatmapCellRenderer());
        table.setRowHeight(30);
        // show the window
        table.setBackground(Color.GRAY);
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

    public String getFilePath(String fileName) {
        Path currentPath = Paths.get(System.getProperty("user.dir"));
        AtomicReference<String> filePath = new AtomicReference<>("");

        try {
            Files.walk(currentPath)
                .filter(Files::isRegularFile)
                .forEach(path -> {
                    if (path.getFileName().toString().equals(fileName)) {
                        filePath.set(path.toString());
                    }
                });
        } catch (IOException e) {
            e.printStackTrace();
        }

        return filePath.get();
    }

    private static class DotRenderer extends JComponent {
        private int value;

        public void setValue(int value) {
            this.value = value;
        }

        @Override
        protected void paintComponent(Graphics g) {
            g.setColor(Color.DARK_GRAY);
            g.fillRect(0, 0, this.getWidth(), this.getHeight());
            g.setColor(Color.RED);
            int centerX = this.getWidth() / 2;
            int centerY = this.getHeight() / 2;
            g.fillOval(centerX - this.value, centerY - this.value, this.value * 2, this.value * 2);

        }

    }

}
