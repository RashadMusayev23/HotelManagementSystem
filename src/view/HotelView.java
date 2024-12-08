package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class HotelView extends JFrame {
    private JButton createHotelButton;

    public HotelView() {
        super("Hotel Management");

        createHotelButton = new JButton("Create New Hotel");

        // Simple layout
        this.setLayout(new FlowLayout());
        this.add(createHotelButton);

        this.setSize(300, 100);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null); // Center on screen
    }

    public void addCreateHotelButtonListener(ActionListener listener) {
        createHotelButton.addActionListener(listener);
    }

    public void display() {
        this.setVisible(true);
    }
}
