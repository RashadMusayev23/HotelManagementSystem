package view.admin;

import model.info.UserInfo;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class AdminViewUserAccountsView extends JDialog {
    private JTable userTable;
    private JButton closeButton;

    public AdminViewUserAccountsView(JFrame parent) {
        super(parent, "View User Accounts", true);
        setLayout(new BorderLayout());

        userTable = new JTable();
        JScrollPane scrollPane = new JScrollPane(userTable);
        add(scrollPane, BorderLayout.CENTER);

        closeButton = new JButton("Close");
        add(closeButton, BorderLayout.SOUTH);

        setSize(500, 300);
        setLocationRelativeTo(parent);
    }

    public void updateUserTable(List<UserInfo> users) {
        String[] columnNames = {"User ID", "Username", "Role"};
        DefaultTableModel model = new DefaultTableModel(columnNames, 0);

        for (UserInfo user : users) {
            model.addRow(new Object[]{user.getUserId(), user.getUsername(), user.getRole()});
        }

        userTable.setModel(model);
    }

    public JButton getCloseButton() {
        return closeButton;
    }
}