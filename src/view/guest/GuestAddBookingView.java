package view.guest;

import javax.swing.*;
import java.awt.*;
import java.util.Date;

public class GuestAddBookingView extends JDialog {
    private JComboBox<Object> guestCombo;
    private JComboBox<Object> roomCombo;
    private JSpinner startDateSpinner;
    private JSpinner endDateSpinner;
    private JButton addButton;
    private JButton cancelButton;

    public GuestAddBookingView(Frame parent) {
        super(parent, "Add New Booking", true);
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10,10,10,10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Guest Combo
        gbc.gridx=0; gbc.gridy=0;
        add(new JLabel("Guest:"), gbc);
        guestCombo = new JComboBox<>();
        gbc.gridx=1;
        gbc.weightx = 1.0;
        add(guestCombo, gbc);

        // Room Combo
        gbc.gridx=0; gbc.gridy=1;
        add(new JLabel("Room:"), gbc);
        roomCombo = new JComboBox<>();
        gbc.gridx=1;
        gbc.weightx = 1.0;
        add(roomCombo, gbc);

        // Start Date Spinner
        gbc.gridx=0; gbc.gridy=2;
        add(new JLabel("Start Date:"), gbc);
        startDateSpinner = createDateSpinner();
        gbc.gridx=1;
        gbc.weightx = 1.0;
        add(startDateSpinner, gbc);

        // End Date Spinner
        gbc.gridx=0; gbc.gridy=3;
        add(new JLabel("End Date:"), gbc);
        endDateSpinner = createDateSpinner();
        gbc.gridx=1;
        gbc.weightx = 1.0;
        add(endDateSpinner, gbc);

        // Buttons
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        addButton = new JButton("Add");
        cancelButton = new JButton("Cancel");
        buttonPanel.add(cancelButton);
        buttonPanel.add(addButton);

        gbc.gridx=0; gbc.gridy=4; gbc.gridwidth=2;
        add(buttonPanel, gbc);

        pack();
        setSize(new Dimension(400, 300));
        setLocationRelativeTo(parent);
    }

    private JSpinner createDateSpinner() {
        // Create a spinner with a date model, defaulting to today's date
        SpinnerDateModel dateModel = new SpinnerDateModel(new Date(), null, null, java.util.Calendar.DAY_OF_MONTH);
        JSpinner spinner = new JSpinner(dateModel);
        // Optionally set date editor
        JSpinner.DateEditor editor = new JSpinner.DateEditor(spinner, "yyyy-MM-dd");
        spinner.setEditor(editor);
        return spinner;
    }

    public void setGuests(Object[] guests) {
        guestCombo.removeAllItems();
        for (Object g : guests) {
            guestCombo.addItem(g);
        }
    }

    public void setRooms(Object[] rooms) {
        roomCombo.removeAllItems();
        for (Object r : rooms) {
            roomCombo.addItem(r);
        }
    }

    public Object getSelectedGuest() {
        return guestCombo.getSelectedItem();
    }

    public Object getSelectedRoom() {
        return roomCombo.getSelectedItem();
    }

    public java.sql.Date getSelectedStartDate() {
        var utilDate = (java.util.Date) startDateSpinner.getValue();
        return new java.sql.Date(utilDate.getTime());
    }

    public java.sql.Date getSelectedEndDate() {
        var utilDate = (java.util.Date) endDateSpinner.getValue();
        return new java.sql.Date(utilDate.getTime());
    }

    public JButton getAddButton() { return addButton; }
    public JButton getCancelButton() { return cancelButton; }
}