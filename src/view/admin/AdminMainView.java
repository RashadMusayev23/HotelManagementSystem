package view.admin;

import controller.AdminController;

import javax.swing.*;
import java.awt.*;

public class AdminMainView extends JFrame {
    private JButton addRoomMainButton;
    private AdminController adminController;

    public AdminMainView(AdminController adminController) {
        super("Administrator Dashboard");
        this.adminController = adminController;

        // Set layout manager, e.g., BorderLayout or BoxLayout
        setLayout(new FlowLayout());

        // Initialize and configure UI components
        addRoomMainButton = new JButton("Add Room");

        // Add action listener using the adminController to open the Add Room dialog
        addRoomMainButton.addActionListener(e -> adminController.showAddRoomDialog(this));

        // Add the button to the frame’s content pane or panel
        add(addRoomMainButton);

        // Set basic frame configurations
        setSize(400, 300);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}