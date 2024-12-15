package view.guest;

import model.RoomInfo;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.Date;

public class GuestViewAvailableRoomsView extends JDialog {
    private JSpinner startDateSpinner;
    private JSpinner endDateSpinner;
    private JButton searchButton;
    private JButton closeButton;
    private JTable roomsTable;
    private DefaultTableModel tableModel;

    public GuestViewAvailableRoomsView(Frame parent) {
        super(parent, "View Available Rooms", true);
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets=new Insets(10,10,10,10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Start Date Spinner
        gbc.gridx=0; gbc.gridy=0;
        add(new JLabel("Start Date:"), gbc);
        startDateSpinner = createDateSpinner();
        gbc.gridx=1;
        add(startDateSpinner, gbc);

        // End Date Spinner
        gbc.gridx=0; gbc.gridy=1;
        add(new JLabel("End Date:"), gbc);
        endDateSpinner = createDateSpinner();
        gbc.gridx=1;
        add(endDateSpinner, gbc);

        searchButton = new JButton("Search");
        closeButton = new JButton("Close");

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.add(closeButton);
        buttonPanel.add(searchButton);

        gbc.gridx=0; gbc.gridy=2; gbc.gridwidth=2;
        add(buttonPanel, gbc);

        tableModel = new DefaultTableModel(new String[]{"Room ID", "Room Name", "Type", "Max Capacity", "Status"},0);
        roomsTable = new JTable(tableModel);

        gbc.gridy=3; gbc.gridwidth=2;
        gbc.fill=GridBagConstraints.BOTH;
        gbc.weightx=1.0; gbc.weighty=1.0;
        add(new JScrollPane(roomsTable), gbc);

        setSize(500,400);
        setLocationRelativeTo(parent);
    }

    private JSpinner createDateSpinner() {
        SpinnerDateModel dateModel = new SpinnerDateModel(new Date(), null, null, java.util.Calendar.DAY_OF_MONTH);
        JSpinner spinner = new JSpinner(dateModel);
        JSpinner.DateEditor editor = new JSpinner.DateEditor(spinner, "yyyy-MM-dd");
        spinner.setEditor(editor);
        return spinner;
    }

    public java.sql.Date getStartDate() {
        var utilDate = (java.util.Date) startDateSpinner.getValue();
        return new java.sql.Date(utilDate.getTime());
    }

    public java.sql.Date getEndDate() {
        var utilDate = (java.util.Date) endDateSpinner.getValue();
        return new java.sql.Date(utilDate.getTime());
    }

    public JButton getSearchButton() { return searchButton; }
    public JButton getCloseButton() { return closeButton; }

    public void updateRoomTable(java.util.List<RoomInfo> rooms) {
        tableModel.setRowCount(0);
        for (RoomInfo r : rooms) {
            tableModel.addRow(new Object[]{r.getRoomId(), r.getRoomName(), r.getRoomType(), r.getMaxCapacity(), r.getStatus()});
        }
    }
}