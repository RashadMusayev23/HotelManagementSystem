package view;

import controller.AdminController;
import controller.GuestController;
import model.dao.AdminDAO;
import model.dao.GuestDAO;
import view.admin.AdminMainView;
import view.guest.GuestMenuView;
import view.housekeeping.HousekeepingGUI;
import view.receptionist.ReceptionistGUI;

import javax.swing.*;
import java.awt.*;

public class MainGUI extends JFrame {
    private JButton guestButton;
    private JButton adminButton;
    private JButton receptionistButton;
    private JButton housekeepingButton;
    private JButton exitButton;

    public MainGUI() {
        super("Main Menu");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridLayout(5, 1, 10, 10));
        setLocationRelativeTo(null);

        guestButton = new JButton("Guest");
        adminButton = new JButton("Administrator");
        receptionistButton = new JButton("Receptionist");
        housekeepingButton = new JButton("Housekeeping");
        exitButton = new JButton("Exit");

        add(guestButton);
        add(adminButton);
        add(receptionistButton);
        add(housekeepingButton);
        add(exitButton);

        // Action listeners
        guestButton.addActionListener(e -> {
            new GuestMenuView(new GuestController(new GuestDAO())).setVisible(true);
        });

        adminButton.addActionListener(e -> {
            new AdminMainView(new AdminController(new AdminDAO())).setVisible(true);
        });

        receptionistButton.addActionListener(e -> {
            new ReceptionistGUI().setVisible(true);
        });

        housekeepingButton.addActionListener(e -> {
            new HousekeepingGUI().setVisible(true);
        });

        exitButton.addActionListener(e -> System.exit(0));

        pack();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            MainGUI main = new MainGUI();
            main.setVisible(true);
        });
    }
}