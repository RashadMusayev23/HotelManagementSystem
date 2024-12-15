package view.admin;

import controller.AdminController;

import javax.swing.*;
import java.awt.*;

public class AdminMainView extends JFrame {
    private JButton addRoomMainButton;
    private JButton deleteRoomButton;
    private JButton manageRoomStatusButton;
    private JButton exitButton;
    private JButton addUserButton;
    private JButton viewUserButton;
    private JButton viewAllBookingsButton;
    private JButton viewAllHousekeepingButton;
    private JButton generateRevenueReportButton;
    private JButton viewMostBookedRoomsButton;
    private JButton viewAllEmployeesButton;
    private JButton cancelBookingButton;

    public AdminMainView(AdminController adminController) {
        super("Administrator Dashboard");

        // Set BoxLayout to align buttons vertically
        setLayout(new GridLayout(12, 1, 10, 10));

        // Initialize and configure UI components
        addRoomMainButton = new JButton("Add Room");
        deleteRoomButton = new JButton("Delete Room");
        manageRoomStatusButton = new JButton("Manage Room Status");
        exitButton = new JButton("Exit");
        addUserButton = new JButton("Add User Account");
        viewUserButton = new JButton("View User Accounts");
        viewAllBookingsButton = new JButton("View All Booking Records");
        viewAllHousekeepingButton = new JButton("View All Housekeeping Records");
        viewMostBookedRoomsButton = new JButton("View Most Booked Room Types");
        viewAllEmployeesButton = new JButton("View All Employees with Their Role");
        cancelBookingButton = new JButton("Cancel Booking if No Payment");
        generateRevenueReportButton = new JButton("Generate Revenue Report");

        addRoomMainButton.addActionListener(e -> adminController.showAddRoomDialog(this));
        deleteRoomButton.addActionListener(e -> adminController.showDeleteRoomDialog(this));
        manageRoomStatusButton.addActionListener(e -> adminController.showManageRoomStatusDialog(this));
        addUserButton.addActionListener(e -> adminController.showAddUserAccountDialog(this));
        viewUserButton.addActionListener(e -> adminController.showViewUserAccountsDialog(this));
        viewAllBookingsButton.addActionListener(e -> adminController.showViewAllBookingRecords(this));
        viewAllHousekeepingButton.addActionListener(e -> adminController.showViewAllHousekeepingRecords(this));
        viewMostBookedRoomsButton.addActionListener(e -> adminController.showMostBookedRoomTypes(this));
        viewAllEmployeesButton.addActionListener(e -> adminController.showAllEmployeesWithRole(this));
        cancelBookingButton.addActionListener(e -> adminController.showCancelBookingIfNoPaymentDialog(this));
        generateRevenueReportButton.addActionListener(e -> adminController.showGenerateRevenueReport(this));
        exitButton.addActionListener(e -> dispose());

        add(addRoomMainButton);
        add(deleteRoomButton);
        add(manageRoomStatusButton);
        add(addUserButton);
        add(viewUserButton);
        add(generateRevenueReportButton);
        add(viewAllBookingsButton);
        add(viewAllHousekeepingButton);
        add(viewMostBookedRoomsButton);
        add(viewAllEmployeesButton);
        add(cancelBookingButton);
        add(exitButton);

        pack();
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    }
}