package view.guest;

import javax.swing.*;
import java.awt.*;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.text.ParseException;

public class GuestAddBookingView extends JDialog {
    private JTextField guestIdField;
    private JComboBox<Object> roomCombo;
    private JTextField startDateField;
    private JTextField endDateField;
    private JButton addButton;
    private JButton cancelButton;

    public GuestAddBookingView(Frame parent) {
        super(parent, "Add New Booking", true);
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10,10,10,10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Guest ID
        gbc.gridx=0; gbc.gridy=0;
        add(new JLabel("Guest ID:"), gbc);
        guestIdField = new JTextField(10);
        gbc.gridx=1;
        add(guestIdField, gbc);

        // Room
        gbc.gridx=0; gbc.gridy=1;
        add(new JLabel("Room:"), gbc);
        roomCombo = new JComboBox<>();
        gbc.gridx=1;
        add(roomCombo, gbc);

        // Start Date
        gbc.gridx=0; gbc.gridy=2;
        add(new JLabel("Start Date (YYYY-MM-DD):"), gbc);
        startDateField = new JTextField(10);
        gbc.gridx=1;
        add(startDateField, gbc);

        // End Date
        gbc.gridx=0; gbc.gridy=3;
        add(new JLabel("End Date (YYYY-MM-DD):"), gbc);
        endDateField = new JTextField(10);
        gbc.gridx=1;
        add(endDateField, gbc);

        // Buttons
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        addButton = new JButton("Add");
        cancelButton = new JButton("Cancel");
        buttonPanel.add(cancelButton);
        buttonPanel.add(addButton);

        gbc.gridx=0; gbc.gridy=4; gbc.gridwidth=2;
        add(buttonPanel, gbc);

        pack();
        setLocationRelativeTo(parent);
    }

    public int getGuestId() throws NumberFormatException {
        return Integer.parseInt(guestIdField.getText().trim());
    }

    public void setRooms(Object[] rooms) {
        roomCombo.removeAllItems();
        for (Object r : rooms) {
            roomCombo.addItem(r);
        }
    }

    public Object getSelectedRoom() {
        return roomCombo.getSelectedItem();
    }

    private Date parseDate(String dateStr) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        sdf.setLenient(false);
        return new Date(sdf.parse(dateStr.trim()).getTime());
    }

    public Date getStartDate() throws ParseException {
        return parseDate(startDateField.getText());
    }

    public Date getEndDate() throws ParseException {
        return parseDate(endDateField.getText());
    }

    public JButton getAddButton() { return addButton; }
    public JButton getCancelButton() { return cancelButton; }
}