package view.admin;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class AdminViewRecordsView extends JDialog {
    private JTable recordsTable;
    private JButton closeButton;

    public AdminViewRecordsView(JFrame parent, String title) {
        super(parent, title, true);
        setLayout(new BorderLayout());

        // Table to display records
        recordsTable = new JTable();
        JScrollPane scrollPane = new JScrollPane(recordsTable);
        add(scrollPane, BorderLayout.CENTER);

        // Close button
        closeButton = new JButton("Close");
        closeButton.addActionListener(e -> dispose());
        add(closeButton, BorderLayout.SOUTH);

        setSize(600, 400);
        setLocationRelativeTo(parent);
    }

    // Update table with data
    public void updateTable(Object[][] data, String[] columnNames) {
        DefaultTableModel model = new DefaultTableModel(data, columnNames);
        recordsTable.setModel(model);
    }
}