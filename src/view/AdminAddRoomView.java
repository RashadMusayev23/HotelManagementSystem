package view;

import javax.swing.*;
import java.awt.*;

public class AdminAddRoomView extends JDialog {
    // Components
    private JTextField roomNameField;
    private JComboBox<String> roomTypeCombo;
    private JSpinner maxCapacitySpinner;
    private JComboBox<String> statusCombo;
    private JComboBox<Object> hotelCombo; // will hold AdminDAO.HotelInfo objects

    private JButton addButton;
    private JButton cancelButton;

    public AdminAddRoomView(Frame parent) {
        super(parent, "Add Room", true);
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10,10,10,10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Room Name Field
        gbc.gridx = 0; gbc.gridy = 0;
        add(new JLabel("Room Name:"), gbc);
        roomNameField = new JTextField(15);
        gbc.gridx = 1; 
        add(roomNameField, gbc);

        // Room Type Combo
        gbc.gridx = 0; gbc.gridy = 1;
        add(new JLabel("Room Type:"), gbc);
        roomTypeCombo = new JComboBox<>(new String[]{"Single", "Double", "Family", "Suite"});
        gbc.gridx = 1;
        add(roomTypeCombo, gbc);

        // Max Capacity Spinner
        gbc.gridx = 0; gbc.gridy = 2;
        add(new JLabel("Max Capacity:"), gbc);
        maxCapacitySpinner = new JSpinner(new SpinnerNumberModel(1, 1, 20, 1));
        gbc.gridx = 1;
        add(maxCapacitySpinner, gbc);

        // Status Combo
        gbc.gridx = 0; gbc.gridy = 3;
        add(new JLabel("Status:"), gbc);
        statusCombo = new JComboBox<>(new String[]{"Available", "Under Maintenance", "Booked"});
        gbc.gridx = 1;
        add(statusCombo, gbc);

        // Hotel Combo - will be populated later by the controller
        gbc.gridx = 0; gbc.gridy = 4;
        add(new JLabel("Hotel:"), gbc);
        hotelCombo = new JComboBox<>();
        gbc.gridx = 1;
        add(hotelCombo, gbc);

        // Buttons
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        addButton = new JButton("Add");
        cancelButton = new JButton("Cancel");
        buttonPanel.add(cancelButton);
        buttonPanel.add(addButton);

        gbc.gridx = 0; gbc.gridy = 5; gbc.gridwidth = 2;
        add(buttonPanel, gbc);

        pack();
        setLocationRelativeTo(parent);
    }

    // Getters for fields:
    public String getRoomName() { return roomNameField.getText().trim(); }
    public String getRoomType() { return (String) roomTypeCombo.getSelectedItem(); }
    public int getMaxCapacity() { return (Integer) maxCapacitySpinner.getValue(); }
    public String getStatus() { return (String) statusCombo.getSelectedItem(); }
    public Object getSelectedHotel() { return hotelCombo.getSelectedItem(); }

    public void setHotels(Object[] hotels) {
        // Clear and repopulate
        hotelCombo.removeAllItems();
        for (Object h : hotels) {
            hotelCombo.addItem(h);
        }
    }

    // Button accessors
    public JButton getAddButton() { return addButton; }
    public JButton getCancelButton() { return cancelButton; }
}