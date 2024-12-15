package view.admin;

import model.Booking;

import javax.swing.*;
import java.awt.*;

public class AdminCancelBookingView extends JDialog {
    private JComboBox<Booking> bookingComboBox;
    private JButton cancelButton, closeButton;

    public AdminCancelBookingView(JFrame parent) {
        super(parent, "Cancel Booking (No Payment)", true);
        setLayout(new GridLayout(3, 2, 10, 10));

        add(new JLabel("Select Booking:"));
        bookingComboBox = new JComboBox<>();
        add(bookingComboBox);

        cancelButton = new JButton("Cancel Booking");
        closeButton = new JButton("Close");
        add(cancelButton);
        add(closeButton);

        setSize(400, 200);
        setLocationRelativeTo(parent);
    }

    public void setBookings(Object[] bookings) {
        for (Object booking : bookings) {
            bookingComboBox.addItem((Booking) booking);
        }
    }

    public Booking getSelectedBooking() {
        return (Booking) bookingComboBox.getSelectedItem();
    }

    public JButton getCancelButton() { return cancelButton; }
    public JButton getCloseButton() { return closeButton; }
}