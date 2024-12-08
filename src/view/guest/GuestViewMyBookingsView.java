package view.guest;

import model.BookingInfo;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class GuestViewMyBookingsView extends JDialog {
    private JTextField guestIdField;
    private JButton viewButton;
    private JButton closeButton;
    private JTable bookingsTable;
    private DefaultTableModel tableModel;

    public GuestViewMyBookingsView(Frame parent) {
        super(parent, "View My Bookings", true);
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets=new Insets(10,10,10,10);
        gbc.fill=GridBagConstraints.HORIZONTAL;

        gbc.gridx=0; gbc.gridy=0;
        add(new JLabel("Guest ID:"), gbc);
        guestIdField = new JTextField(10);
        gbc.gridx=1;
        add(guestIdField, gbc);

        viewButton = new JButton("View");
        closeButton = new JButton("Close");

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.add(closeButton);
        buttonPanel.add(viewButton);

        gbc.gridx=0; gbc.gridy=1; gbc.gridwidth=2;
        add(buttonPanel, gbc);

        tableModel = new DefaultTableModel(new String[]{"Booking ID", "Room ID", "Start", "End", "Status"},0);
        bookingsTable = new JTable(tableModel);

        gbc.gridy=2; gbc.gridwidth=2;
        gbc.weightx=1.0; gbc.weighty=1.0;
        gbc.fill=GridBagConstraints.BOTH;
        add(new JScrollPane(bookingsTable), gbc);

        setSize(500,400);
        setLocationRelativeTo(parent);
    }

    public int getGuestId() {
        return Integer.parseInt(guestIdField.getText().trim());
    }

    public JButton getViewButton() { return viewButton; }
    public JButton getCloseButton() { return closeButton; }

    public void updateBookingTable(List<BookingInfo> bookings) {
        tableModel.setRowCount(0);
        for (BookingInfo b : bookings) {
            tableModel.addRow(new Object[]{b.getBookingId(), b.getRoomId(), b.getStartDate(), b.getEndDate(), b.getStatus()});
        }
    }
}