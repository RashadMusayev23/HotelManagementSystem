package view;

import javax.swing.*;
import java.awt.*;

public class MenuUI extends JFrame {
    
    public MenuUI() {
        super("Menu");
        
        // Create main panel with a vertical layout for text and buttons
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        
        // Add some padding around the panel
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Create a label for the welcome message
        JLabel welcomeLabel = new JLabel("Welcome to the Hotel Management System");
        welcomeLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        welcomeLabel.setFont(new Font("SansSerif", Font.PLAIN, 16));

        // Create a label for the instruction
        JLabel instructionLabel = new JLabel("Please select an option:");
        instructionLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        instructionLabel.setFont(new Font("SansSerif", Font.PLAIN, 14));

        // Add the welcome and instruction labels
        mainPanel.add(welcomeLabel);
        mainPanel.add(Box.createVerticalStrut(10)); // spacing
        mainPanel.add(instructionLabel);
        mainPanel.add(Box.createVerticalStrut(20));

        // We’ll create buttons for each option. In a real app, you might add ActionListeners.
        JButton option1 = new JButton("1. Add a new booking");
        JButton option2 = new JButton("2. Modify a booking");
        JButton option3 = new JButton("3. Delete a booking");
        JButton option4 = new JButton("4. View room availability");
        JButton option5 = new JButton("5. Check in");
        JButton option6 = new JButton("6. Check out");
        JButton option7 = new JButton("7. Exit");

        // Align all buttons to center
        option1.setAlignmentX(Component.CENTER_ALIGNMENT);
        option2.setAlignmentX(Component.CENTER_ALIGNMENT);
        option3.setAlignmentX(Component.CENTER_ALIGNMENT);
        option4.setAlignmentX(Component.CENTER_ALIGNMENT);
        option5.setAlignmentX(Component.CENTER_ALIGNMENT);
        option6.setAlignmentX(Component.CENTER_ALIGNMENT);
        option7.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Add some spacing between buttons
        mainPanel.add(option1);
        mainPanel.add(Box.createVerticalStrut(5));
        mainPanel.add(option2);
        mainPanel.add(Box.createVerticalStrut(5));
        mainPanel.add(option3);
        mainPanel.add(Box.createVerticalStrut(5));
        mainPanel.add(option4);
        mainPanel.add(Box.createVerticalStrut(5));
        mainPanel.add(option5);
        mainPanel.add(Box.createVerticalStrut(5));
        mainPanel.add(option6);
        mainPanel.add(Box.createVerticalStrut(5));
        mainPanel.add(option7);

        // Set up the frame
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setContentPane(mainPanel);
        this.pack();
        this.setLocationRelativeTo(null); // Center on screen
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            MenuUI menu = new MenuUI();
            menu.setVisible(true);
        });
    }
}