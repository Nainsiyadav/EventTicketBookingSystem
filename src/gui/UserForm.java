package gui;

import model.User;
import service.UserService;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class UserForm extends JFrame implements ActionListener {

    JLabel titleLabel, idLabel, nameLabel, emailLabel,
            phoneLabel, passwordLabel;

    JTextField idField, nameField, emailField, phoneField;
    JPasswordField passwordField;

    JButton addButton, updateButton, deleteButton,
            viewButton, clearButton, backButton;

    UserService service;

    public UserForm() {

        setTitle("User Management");
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel mainPanel = new JPanel(new GridBagLayout());
        mainPanel.setBackground(new Color(242, 244, 247));
        setContentPane(mainPanel);

        // ================= WHITE CARD =================

        JPanel card = new RoundedPanel(30);
        card.setPreferredSize(new Dimension(800, 680));
        card.setBackground(Color.WHITE);
        card.setLayout(null);

        mainPanel.add(card);

        service = new UserService();

        // ================= TITLE =================

        titleLabel = new JLabel(
                "USER MANAGEMENT",
                SwingConstants.CENTER
        );

        titleLabel.setFont(
                new Font("Arial", Font.BOLD, 28)
        );

        titleLabel.setForeground(
                new Color(25, 55, 90)
        );

        titleLabel.setBounds(200, 30, 400, 40);

        card.add(titleLabel);

        // ================= USER ID =================

        idLabel = createLabel("User ID:");
        idLabel.setBounds(100, 100, 140, 25);

        idField = new JTextField();
        idField.setBounds(260, 100, 400, 32);

        card.add(idLabel);
        card.add(idField);

        // ================= NAME =================

        nameLabel = createLabel("Name:");
        nameLabel.setBounds(100, 150, 140, 25);

        nameField = new JTextField();
        nameField.setBounds(260, 150, 400, 32);

        card.add(nameLabel);
        card.add(nameField);

        // ================= EMAIL =================

        emailLabel = createLabel("Email:");
        emailLabel.setBounds(100, 200, 140, 25);

        emailField = new JTextField();
        emailField.setBounds(260, 200, 400, 32);

        card.add(emailLabel);
        card.add(emailField);

        // ================= PHONE =================

        phoneLabel = createLabel("Phone:");
        phoneLabel.setBounds(100, 250, 140, 25);

        phoneField = new JTextField();
        phoneField.setBounds(260, 250, 400, 32);

        card.add(phoneLabel);
        card.add(phoneField);

        // ================= PASSWORD =================

        passwordLabel = createLabel("Password:");
        passwordLabel.setBounds(100, 300, 140, 25);

        passwordField = new JPasswordField();
        passwordField.setBounds(260, 300, 400, 32);

        card.add(passwordLabel);
        card.add(passwordField);

        // ================= BUTTONS =================

        addButton = createButton(
                "Add",
                new Color(45, 85, 130)
        );

        updateButton = createButton(
                "Update",
                new Color(55, 140, 100)
        );

        deleteButton = createButton(
                "Delete",
                new Color(190, 70, 70)
        );

        viewButton = createButton(
                "View Users",
                new Color(120, 80, 150)
        );

        clearButton = createButton(
                "Clear",
                new Color(210, 130, 50)
        );

        backButton = createButton(
                "← Back",
                new Color(90, 90, 90)
        );

        addButton.setBounds(70, 380, 120, 42);
        updateButton.setBounds(205, 380, 120, 42);
        deleteButton.setBounds(340, 380, 120, 42);
        viewButton.setBounds(475, 380, 150, 42);

        clearButton.setBounds(250, 455, 120, 42);
        backButton.setBounds(390, 455, 120, 42);

        card.add(addButton);
        card.add(updateButton);
        card.add(deleteButton);
        card.add(viewButton);
        card.add(clearButton);
        card.add(backButton);

        // ================= LISTENERS =================

        addButton.addActionListener(this);
        updateButton.addActionListener(this);
        deleteButton.addActionListener(this);
        viewButton.addActionListener(this);
        clearButton.addActionListener(this);
        backButton.addActionListener(this);

        setVisible(true);
    }

    // ================= LABEL STYLE =================

    private JLabel createLabel(String text) {

        JLabel label = new JLabel(text);

        label.setFont(
                new Font("Arial", Font.BOLD, 14)
        );

        label.setForeground(
                new Color(70, 70, 70)
        );

        return label;
    }

    // ================= BUTTON STYLE =================

    private JButton createButton(
            String text,
            Color color) {

        JButton button = new JButton(text);

        button.setFont(
                new Font("Arial", Font.BOLD, 13)
        );

        button.setBackground(color);
        button.setForeground(Color.WHITE);

        button.setFocusPainted(false);
        button.setBorderPainted(false);

        return button;
    }

    // ================= VALIDATION =================

    private boolean validateUserDetails() {

        String name =
                nameField.getText().trim();

        String email =
                emailField.getText().trim();

        String phone =
                phoneField.getText().trim();

        String password =
                new String(
                        passwordField.getPassword()
                );

        if (name.isEmpty()) {

            showError("Name cannot be empty!");
            return false;
        }

        if (!name.matches("[a-zA-Z ]+")) {

            showError(
                    "Name should contain only letters!"
            );

            return false;
        }

        if (email.isEmpty()) {

            showError("Email cannot be empty!");
            return false;
        }

        if (!email.matches(
                "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$")) {

            showError(
                    "Please enter a valid email!"
            );

            return false;
        }

        if (!phone.matches("\\d{10}")) {

            showError(
                    "Phone number must contain exactly 10 digits!"
            );

            return false;
        }

        if (password.length() < 5) {

            showError(
                    "Password must contain at least 5 characters!"
            );

            return false;
        }

        return true;
    }

    // ================= BUTTON ACTIONS =================

    @Override
    public void actionPerformed(ActionEvent e) {

        try {

            // ================= ADD =================

            if (e.getSource() == addButton) {

                if (!validateUserDetails()) {
                    return;
                }

                String name =
                        nameField.getText().trim();

                String email =
                        emailField.getText().trim();

                String phone =
                        phoneField.getText().trim();

                String password =
                        new String(
                                passwordField.getPassword()
                        );

                User user =
                        new User(
                                name,
                                email,
                                phone,
                                password
                        );

                boolean success =
                        service.addUser(user);

                if (success) {

                    JOptionPane.showMessageDialog(
                            this,
                            "User Added Successfully!",
                            "Success",
                            JOptionPane.INFORMATION_MESSAGE
                    );

                    clearFields();

                } else {

                    showError(
                            "User could not be added!"
                    );
                }
            }

            // ================= UPDATE =================

            else if (e.getSource() == updateButton) {

                if (!validateUserDetails()) {
                    return;
                }

                int id;

                try {

                    id = Integer.parseInt(
                            idField.getText().trim()
                    );

                } catch (NumberFormatException ex) {

                    showError(
                            "Please enter a valid User ID!"
                    );

                    return;
                }

                if (id <= 0) {

                    showError(
                            "User ID must be greater than 0!"
                    );

                    return;
                }

                String name =
                        nameField.getText().trim();

                String email =
                        emailField.getText().trim();

                String phone =
                        phoneField.getText().trim();

                String password =
                        new String(
                                passwordField.getPassword()
                        );

                User user =
                        new User(
                                name,
                                email,
                                phone,
                                password
                        );

                user.setUserId(id);

                String oldPassword =
                        JOptionPane.showInputDialog(
                                this,
                                "Enter your current password:"
                        );

                if (oldPassword == null ||
                        oldPassword.isEmpty()) {

                    showError(
                            "Password is required!"
                    );

                    return;
                }

                boolean success =
                        service.updateUser(
                                user,
                                oldPassword
                        );

                if (success) {

                    JOptionPane.showMessageDialog(
                            this,
                            "User Updated Successfully!",
                            "Success",
                            JOptionPane.INFORMATION_MESSAGE
                    );

                    clearFields();

                } else {

                    showError(
                            "Invalid User ID or Password!\n"
                            + "User was not updated."
                    );
                }
            }

            // ================= DELETE =================

            else if (e.getSource() == deleteButton) {

                int id;

                try {

                    id = Integer.parseInt(
                            idField.getText().trim()
                    );

                } catch (NumberFormatException ex) {

                    showError(
                            "Please enter a valid User ID!"
                    );

                    return;
                }

                if (id <= 0) {

                    showError(
                            "User ID must be greater than 0!"
                    );

                    return;
                }

                String password =
                        JOptionPane.showInputDialog(
                                this,
                                "Enter your password:"
                        );

                if (password == null ||
                        password.isEmpty()) {

                    showError(
                            "Password is required!"
                    );

                    return;
                }

                boolean success =
                        service.deleteUser(
                                id,
                                password
                        );

                if (success) {

                    JOptionPane.showMessageDialog(
                            this,
                            "User Deleted Successfully!",
                            "Success",
                            JOptionPane.INFORMATION_MESSAGE
                    );

                    clearFields();

                } else {

                    showError(
                            "Invalid User ID or Password!\n"
                            + "User was not deleted."
                    );
                }
            }

            // ================= VIEW =================

            else if (e.getSource() == viewButton) {

                showUsersTable();
            }

            // ================= CLEAR =================

            else if (e.getSource() == clearButton) {

                clearFields();
            }

            // ================= BACK =================

            else if (e.getSource() == backButton) {

                int choice =
                        JOptionPane.showConfirmDialog(
                                this,
                                "Go back to Dashboard?",
                                "Back",
                                JOptionPane.YES_NO_OPTION
                        );

                if (choice ==
                        JOptionPane.YES_OPTION) {

                    new Dashboard().setVisible(true);
                    dispose();
                }
            }

        } catch (Exception ex) {

            JOptionPane.showMessageDialog(
                    this,
                    "Something went wrong: "
                    + ex.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }

    // ================= VIEW USERS =================

    private void showUsersTable() {

        Object[][] data =
                service.getUsersForTable();

        String[] columns = {
                "User ID",
                "Name",
                "Email",
                "Phone"
        };

        JTable table =
                new JTable(data, columns);

        table.setFont(
                new Font("Arial", Font.PLAIN, 14)
        );

        table.setRowHeight(30);

        table.getTableHeader().setFont(
                new Font("Arial", Font.BOLD, 14)
        );

        table.getTableHeader().setBackground(
                new Color(45, 85, 130)
        );

        table.getTableHeader().setForeground(
                Color.WHITE
        );

        table.setSelectionMode(
                ListSelectionModel.SINGLE_SELECTION
        );

        JScrollPane scrollPane =
                new JScrollPane(table);

        JFrame frame =
                new JFrame("All Users");

        frame.setExtendedState(
                JFrame.MAXIMIZED_BOTH
        );

        frame.setDefaultCloseOperation(
                JFrame.DISPOSE_ON_CLOSE
        );

        JPanel panel =
                new JPanel(new BorderLayout());

        panel.setBackground(
                new Color(242, 244, 247)
        );

        panel.setBorder(
                BorderFactory.createEmptyBorder(
                        30, 30, 30, 30
                )
        );

        JLabel title =
                new JLabel(
                        "ALL USERS",
                        SwingConstants.CENTER
                );

        title.setFont(
                new Font("Arial", Font.BOLD, 26)
        );

        title.setForeground(
                new Color(25, 55, 90)
        );

        title.setBorder(
                BorderFactory.createEmptyBorder(
                        0, 0, 20, 0
                )
        );

        panel.add(
                title,
                BorderLayout.NORTH
        );

        panel.add(
                scrollPane,
                BorderLayout.CENTER
        );

        JButton closeButton =
                createButton(
                        "← Back",
                        new Color(90, 90, 90)
                );

        closeButton.addActionListener(
                e -> frame.dispose()
        );

        JPanel bottomPanel =
                new JPanel();

        bottomPanel.setBackground(
                new Color(242, 244, 247)
        );

        bottomPanel.add(closeButton);

        panel.add(
                bottomPanel,
                BorderLayout.SOUTH
        );

        frame.setContentPane(panel);
        frame.setVisible(true);
    }

    // ================= ERROR =================

    private void showError(String message) {

        JOptionPane.showMessageDialog(
                this,
                message,
                "Error",
                JOptionPane.ERROR_MESSAGE
        );
    }

    // ================= CLEAR =================

    private void clearFields() {

        idField.setText("");
        nameField.setText("");
        emailField.setText("");
        phoneField.setText("");
        passwordField.setText("");
    }

    // ================= ROUNDED CARD =================

    class RoundedPanel extends JPanel {

        private int radius;

        RoundedPanel(int radius) {

            this.radius = radius;
            setOpaque(false);
        }

        @Override
        protected void paintComponent(Graphics g) {

            super.paintComponent(g);

            Graphics2D g2 =
                    (Graphics2D) g.create();

            g2.setRenderingHint(
                    RenderingHints.KEY_ANTIALIASING,
                    RenderingHints.VALUE_ANTIALIAS_ON
            );

            g2.setColor(getBackground());

            g2.fillRoundRect(
                    0,
                    0,
                    getWidth() - 1,
                    getHeight() - 1,
                    radius,
                    radius
            );

            g2.dispose();
        }
    }

    // ================= MAIN =================

    public static void main(String[] args) {

        SwingUtilities.invokeLater(
                () -> new UserForm()
        );
    }
}