package view.guest;

import javax.swing.*;
import java.awt.*;

public class GuestCancelBookingView extends JDialog {
    private JTextField guestIdField;
    private JComboBox<Object> bookingCombo;
    private JButton loadButton;
    private JButton cancelBookingButton;
    private JButton closeButton;

    public GuestCancelBookingView(Frame parent) {
        super(parent, "Cancel Booking", true);
        setLayout(new GridBagLayout());
        GridBagConstraints gbc=new GridBagConstraints();
        gbc.insets=new Insets(10,10,10,10);
        gbc.fill=GridBagConstraints.HORIZONTAL;

        gbc.gridx=0; gbc.gridy=0;
        add(new JLabel("Guest ID:"), gbc);
        guestIdField = new JTextField(10);
        gbc.gridx=1;
        add(guestIdField, gbc);

        loadButton = new JButton("Load Bookings");
        gbc.gridx=0; gbc.gridy=1; gbc.gridwidth=2;
        add(loadButton, gbc);

        gbc.gridx=0; gbc.gridy=2; gbc.gridwidth=1;
        add(new JLabel("Select Booking:"), gbc);
        bookingCombo = new JComboBox<>();
        gbc.gridx=1;
        add(bookingCombo, gbc);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        cancelBookingButton = new JButton("Cancel Booking");
        closeButton = new JButton("Close");
        buttonPanel.add(closeButton);
        buttonPanel.add(cancelBookingButton);

        gbc.gridx=0; gbc.gridy=3; gbc.gridwidth=2;
        add(buttonPanel, gbc);

        pack();
        setLocationRelativeTo(parent);
    }

    public int getGuestId() {
        return Integer.parseInt(guestIdField.getText().trim());
    }

    public void setBookings(Object[] bookings) {
        bookingCombo.removeAllItems();
        for (Object b : bookings) {
            bookingCombo.addItem(b);
        }
    }

    public Object getSelectedBooking() {
        return bookingCombo.getSelectedItem();
    }

    public JButton getLoadButton() { return loadButton; }
    public JButton getCancelBookingButton() { return cancelBookingButton; }
    public JButton getCloseButton() { return closeButton; }
}