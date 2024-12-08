package view.guest;

import model.RoomInfo;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class GuestViewAvailableRoomsView extends JDialog {
    private JTextField startDateField;
    private JTextField endDateField;
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

        // Start Date
        gbc.gridx=0; gbc.gridy=0;
        add(new JLabel("Start Date (YYYY-MM-DD):"), gbc);
        startDateField = new JTextField(10);
        gbc.gridx=1;
        add(startDateField, gbc);

        // End Date
        gbc.gridx=0; gbc.gridy=1;
        add(new JLabel("End Date (YYYY-MM-DD):"), gbc);
        endDateField = new JTextField(10);
        gbc.gridx=1;
        add(endDateField, gbc);

        searchButton = new JButton("Search");
        closeButton = new JButton("Close");

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.add(closeButton);
        buttonPanel.add(searchButton);

        gbc.gridx=0; gbc.gridy=2; gbc.gridwidth=2;
        add(buttonPanel, gbc);

        // Table
        tableModel = new DefaultTableModel(new String[]{"Room ID", "Room Name", "Type", "Max Capacity", "Status"},0);
        roomsTable = new JTable(tableModel);
        gbc.gridy=3; gbc.gridwidth=2;
        gbc.fill=GridBagConstraints.BOTH;
        gbc.weightx=1.0; gbc.weighty=1.0;
        add(new JScrollPane(roomsTable), gbc);

        setSize(500,400);
        setLocationRelativeTo(parent);
    }

    private Date parseDate(String str) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        sdf.setLenient(false);
        return new Date(sdf.parse(str.trim()).getTime());
    }

    public Date getStartDate() throws ParseException {
        return parseDate(startDateField.getText());
    }

    public Date getEndDate() throws ParseException {
        return parseDate(endDateField.getText());
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