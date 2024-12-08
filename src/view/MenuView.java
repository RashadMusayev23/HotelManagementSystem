package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class MenuView extends JFrame {
    private JButton addBookingButton;
    private JButton modifyBookingButton;
    private JButton deleteBookingButton;
    private JButton viewAvailabilityButton;
    private JButton checkInButton;
    private JButton checkOutButton;
    private JButton exitButton;

    public MenuView() {
        super("Menu");
        
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JLabel welcomeLabel = new JLabel("Welcome to the Hotel Management System");
        welcomeLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        welcomeLabel.setFont(new Font("SansSerif", Font.PLAIN, 16));

        JLabel instructionLabel = new JLabel("Please select an option:");
        instructionLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        instructionLabel.setFont(new Font("SansSerif", Font.PLAIN, 14));

        mainPanel.add(welcomeLabel);
        mainPanel.add(Box.createVerticalStrut(10));
        mainPanel.add(instructionLabel);
        mainPanel.add(Box.createVerticalStrut(20));

        addBookingButton = new JButton("1. Add a new booking");
        modifyBookingButton = new JButton("2. Modify a booking");
        deleteBookingButton = new JButton("3. Delete a booking");
        viewAvailabilityButton = new JButton("4. View room availability");
        checkInButton = new JButton("5. Check in");
        checkOutButton = new JButton("6. Check out");
        exitButton = new JButton("7. Exit");

        addBookingButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        modifyBookingButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        deleteBookingButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        viewAvailabilityButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        checkInButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        checkOutButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        exitButton.setAlignmentX(Component.CENTER_ALIGNMENT);

        mainPanel.add(addBookingButton);
        mainPanel.add(Box.createVerticalStrut(5));
        mainPanel.add(modifyBookingButton);
        mainPanel.add(Box.createVerticalStrut(5));
        mainPanel.add(deleteBookingButton);
        mainPanel.add(Box.createVerticalStrut(5));
        mainPanel.add(viewAvailabilityButton);
        mainPanel.add(Box.createVerticalStrut(5));
        mainPanel.add(checkInButton);
        mainPanel.add(Box.createVerticalStrut(5));
        mainPanel.add(checkOutButton);
        mainPanel.add(Box.createVerticalStrut(5));
        mainPanel.add(exitButton);

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setContentPane(mainPanel);
        this.pack();
        this.setLocationRelativeTo(null);
    }

    public void display() {
        this.setVisible(true);
    }

    // Methods to add listeners
    public void addAddBookingListener(ActionListener l) { addBookingButton.addActionListener(l); }
    public void addModifyBookingListener(ActionListener l) { modifyBookingButton.addActionListener(l); }
    public void addDeleteBookingListener(ActionListener l) { deleteBookingButton.addActionListener(l); }
    public void addViewAvailabilityListener(ActionListener l) { viewAvailabilityButton.addActionListener(l); }
    public void addCheckInListener(ActionListener l) { checkInButton.addActionListener(l); }
    public void addCheckOutListener(ActionListener l) { checkOutButton.addActionListener(l); }
    public void addExitListener(ActionListener l) { exitButton.addActionListener(l); }
}