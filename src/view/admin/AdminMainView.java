package view.admin;

import controller.AdminController;

import javax.swing.*;
import java.awt.*;

public class AdminMainView extends JFrame {
    private JButton addRoomMainButton;
    private JButton deleteRoomButton;
    private JButton manageRoomStatusButton;
    private JButton exitButton;

    public AdminMainView(AdminController adminController) {
        super("Administrator Dashboard");

        // Set BoxLayout to align buttons vertically
        setLayout(new GridLayout(5,1,10,10));

        // Initialize and configure UI components
        addRoomMainButton = new JButton("Add Room");
        deleteRoomButton = new JButton("Delete Room");
        manageRoomStatusButton = new JButton("Manage Room Status");
        exitButton = new JButton("Exit");

        // Add action listener using the adminController to open respective dialogs
        addRoomMainButton.addActionListener(e -> adminController.showAddRoomDialog(this));
        deleteRoomButton.addActionListener(e -> adminController.showDeleteRoomDialog(this));
        manageRoomStatusButton.addActionListener(e -> adminController.showManageRoomStatusDialog(this));
        exitButton.addActionListener(e -> System.exit(0));

        add(addRoomMainButton);
        add(deleteRoomButton);
        add(manageRoomStatusButton);
        add(exitButton);

        pack();
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    }
}