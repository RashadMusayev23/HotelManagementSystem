package view.admin;

import javax.swing.*;
import java.awt.*;

public class AdminAddUserView extends JDialog {
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JComboBox<String> roleComboBox;
    private JButton addButton, cancelButton;

    public AdminAddUserView(JFrame parent) {
        super(parent, "Add User Account", true);
        setLayout(new GridLayout(4, 2, 10, 10));

        add(new JLabel("Username:"));
        usernameField = new JTextField();
        add(usernameField);

        add(new JLabel("Password:"));
        passwordField = new JPasswordField();
        add(passwordField);

        add(new JLabel("Role:"));
        roleComboBox = new JComboBox<>(new String[]{"Administrator", "Receptionist", "Housekeeper", "Guest"});
        add(roleComboBox);

        addButton = new JButton("Add");
        cancelButton = new JButton("Cancel");
        add(cancelButton);
        add(addButton);

        setSize(400, 200);
        setLocationRelativeTo(parent);
    }

    public String getUsername() { return usernameField.getText(); }
    public String getPassword() { return new String(passwordField.getPassword()); }
    public String getSelectedRole() { return (String) roleComboBox.getSelectedItem(); }
    public JButton getAddButton() { return addButton; }
    public JButton getCancelButton() { return cancelButton; }
}