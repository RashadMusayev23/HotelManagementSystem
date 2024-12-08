package controller;

import model.Hotel;
import model.HotelDAO;
import view.HotelView;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

public class HotelController {
    private HotelView view;
    private HotelDAO hotelDAO;

    public HotelController(HotelView view, HotelDAO hotelDAO) {
        this.view = view;
        this.hotelDAO = hotelDAO;

        // Add listener for the button
        this.view.addCreateHotelButtonListener(new CreateHotelListener());
    }

    // Inner class listener
    class CreateHotelListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            // For demonstration, we'll just hardcode a hotel.
            // In a real scenario, you might open a dialog or form to get these details from the user.
            Hotel hotel = new Hotel(
                    101,
                    "Sunrise Beach Resort",
                    "123 Ocean Drive",
                    "555-6789",
                    100
            );

            try {
                hotelDAO.addHotel(hotel);
                JOptionPane.showMessageDialog(view, "Hotel created successfully!");
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(view, "Error creating hotel: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}
