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

    public AdminMainView(AdminController adminController) {
        super("Administrator Dashboard");

        // Set BoxLayout to align buttons vertically
        setLayout(new GridLayout(6, 1, 10, 10));

        // Initialize and configure UI components
        addRoomMainButton = new JButton("Add Room");
        deleteRoomButton = new JButton("Delete Room");
        manageRoomStatusButton = new JButton("Manage Room Status");
        exitButton = new JButton("Exit");
        addUserButton = new JButton("Add User Account");
        viewUserButton = new JButton("View User Accounts");

        addRoomMainButton.addActionListener(e -> adminController.showAddRoomDialog(this));
        deleteRoomButton.addActionListener(e -> adminController.showDeleteRoomDialog(this));
        manageRoomStatusButton.addActionListener(e -> adminController.showManageRoomStatusDialog(this));
        addUserButton.addActionListener(e -> adminController.showAddUserAccountDialog(this));
        viewUserButton.addActionListener(e -> adminController.showViewUserAccountsDialog(this));
        exitButton.addActionListener(e -> System.exit(0));

        add(addRoomMainButton);
        add(deleteRoomButton);
        add(manageRoomStatusButton);
        add(addUserButton);
        add(viewUserButton);
        add(exitButton);

        pack();
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    }
}