package view.guest;

import controller.GuestController;

import javax.swing.*;
import java.awt.*;

// GuestMenuView.java (example sketch)
public class GuestMenuView extends JFrame {
    private JButton addBookingButton;
    private JButton viewAvailableButton;
    private JButton viewMyBookingsButton;
    private JButton cancelBookingButton;
    private JButton exitButton;

    private GuestController guestController;

    public GuestMenuView(GuestController guestController) {
        super("Guest Menu");
        this.guestController = guestController;

        setLayout(new GridLayout(5,1,10,10));

        addBookingButton = new JButton("Add New Booking");
        viewAvailableButton = new JButton("View Available Rooms");
        viewMyBookingsButton = new JButton("View My Bookings");
        cancelBookingButton = new JButton("Cancel Booking");
        exitButton = new JButton("Exit");

        addBookingButton.addActionListener(e -> guestController.showAddBookingDialog(this));
        viewAvailableButton.addActionListener(e -> guestController.showViewAvailableRoomsDialog(this));
        viewMyBookingsButton.addActionListener(e -> guestController.showViewMyBookingsDialog(this));
        cancelBookingButton.addActionListener(e -> guestController.showCancelBookingDialog(this));
        exitButton.addActionListener(e -> dispose());

        add(addBookingButton);
        add(viewAvailableButton);
        add(viewMyBookingsButton);
        add(cancelBookingButton);
        add(exitButton);

        pack();
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    }
}