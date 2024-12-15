package view.admin;

import model.Room;

import javax.swing.*;
import java.awt.*;

public class AdminManageRoomView extends JDialog {
    private JComboBox<Room> roomComboBox;
    private JComboBox<String> statusComboBox;
    private JButton confirmButton;
    private JButton cancelButton;

    public AdminManageRoomView(JFrame parent, String title, boolean showStatus) {
        super(parent, title, true);
        setLayout(new GridLayout(showStatus ? 3 : 2, 2, 10, 10)); // Adjust layout
        setSize(400, 200);
        setLocationRelativeTo(parent);

        // Room Selection
        add(new JLabel("Select Room:"));
        roomComboBox = new JComboBox<>();
        add(roomComboBox);

        // Room Status dropdown (conditionally displayed)
        if (showStatus) {
            add(new JLabel("Room Status:"));
            statusComboBox = new JComboBox<>(new String[]{"Available", "Booked", "Maintenance"});
            add(statusComboBox);
        }

        // Buttons
        confirmButton = new JButton("Confirm");
        cancelButton = new JButton("Cancel");
        add(cancelButton);
        add(confirmButton);

        setVisible(false);
    }

    // Method to get selected status (only for status management)
    public String getSelectedStatus() {
        return statusComboBox != null ? (String) statusComboBox.getSelectedItem() : null;
    }

    // Populate room dropdown
    public void setRooms(Object[] rooms) {
        for (Object room : rooms) {
            roomComboBox.addItem((Room) room);
        }
    }

    public Room getSelectedRoom() {
        return (Room) roomComboBox.getSelectedItem();
    }

    public JButton getConfirmButton() {
        return confirmButton;
    }

    public JButton getCancelButton() {
        return cancelButton;
    }
}