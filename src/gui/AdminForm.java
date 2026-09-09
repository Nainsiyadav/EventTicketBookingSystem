package gui;

import model.Admin;
import service.AdminService;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AdminForm extends JFrame implements ActionListener {

    JLabel titleLabel, idLabel, nameLabel, emailLabel,
            passwordLabel, roleLabel;

    JTextField idField, nameField, emailField, roleField;
    JPasswordField passwordField;

    JButton addButton, updateButton, deleteButton, viewButton;

    AdminService service;

    public AdminForm() {

        setTitle("Admin Management");
        setSize(600, 520);
        setLayout(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        service = new AdminService();

        // ================= TITLE =================

        titleLabel = new JLabel("ADMIN MANAGEMENT");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 22));
        titleLabel.setForeground(new Color(25, 55, 90));
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        titleLabel.setBounds(150, 30, 300, 30);
        add(titleLabel);

        // ================= ADMIN ID =================

        idLabel = new JLabel("Admin ID:");
        idLabel.setFont(new Font("Arial", Font.BOLD, 14));
        idLabel.setForeground(new Color(70, 70, 70));
        idLabel.setBounds(80, 90, 120, 25);

        idField = new JTextField();
        idField.setBounds(210, 90, 200, 25);

        add(idLabel);
        add(idField);

        // ================= NAME =================

        nameLabel = new JLabel("Name:");
        nameLabel.setFont(new Font("Arial", Font.BOLD, 14));
        nameLabel.setForeground(new Color(70, 70, 70));
        nameLabel.setBounds(80, 130, 120, 25);

        nameField = new JTextField();
        nameField.setBounds(210, 130, 200, 25);

        add(nameLabel);
        add(nameField);

        // ================= EMAIL =================

        emailLabel = new JLabel("Email:");
        emailLabel.setFont(new Font("Arial", Font.BOLD, 14));
        emailLabel.setForeground(new Color(70, 70, 70));
        emailLabel.setBounds(80, 170, 120, 25);

        emailField = new JTextField();
        emailField.setBounds(210, 170, 200, 25);

        add(emailLabel);
        add(emailField);

        // ================= PASSWORD =================

        passwordLabel = new JLabel("Password:");
        passwordLabel.setFont(new Font("Arial", Font.BOLD, 14));
        passwordLabel.setForeground(new Color(70, 70, 70));
        passwordLabel.setBounds(80, 210, 120, 25);

        passwordField = new JPasswordField();
        passwordField.setBounds(210, 210, 200, 25);

        add(passwordLabel);
        add(passwordField);

        // ================= ROLE =================

        roleLabel = new JLabel("Role:");
        roleLabel.setFont(new Font("Arial", Font.BOLD, 14));
        roleLabel.setForeground(new Color(70, 70, 70));
        roleLabel.setBounds(80, 250, 120, 25);

        roleField = new JTextField();
        roleField.setBounds(210, 250, 200, 25);
        roleField.setText("Administrator");

        add(roleLabel);
        add(roleField);

        // ================= BUTTONS =================

        addButton = new JButton("Add");
        addButton.setBounds(80, 320, 100, 35);
        addButton.setBackground(new Color(45, 85, 130));
        addButton.setForeground(Color.WHITE);

        updateButton = new JButton("Update");
        updateButton.setBounds(190, 320, 100, 35);
        updateButton.setBackground(new Color(45, 85, 130));
        updateButton.setForeground(Color.WHITE);

        deleteButton = new JButton("Delete");
        deleteButton.setBounds(300, 320, 100, 35);
        deleteButton.setBackground(new Color(190, 70, 70));
        deleteButton.setForeground(Color.WHITE);

        viewButton = new JButton("View Admins");
        viewButton.setBounds(190, 370, 130, 35);
        viewButton.setBackground(new Color(55, 140, 100));
        viewButton.setForeground(Color.WHITE);

        addButton.setFocusPainted(false);
        updateButton.setFocusPainted(false);
        deleteButton.setFocusPainted(false);
        viewButton.setFocusPainted(false);

        addButton.setBorderPainted(false);
        updateButton.setBorderPainted(false);
        deleteButton.setBorderPainted(false);
        viewButton.setBorderPainted(false);

        addButton.addActionListener(this);
        updateButton.addActionListener(this);
        deleteButton.addActionListener(this);
        viewButton.addActionListener(this);

        add(addButton);
        add(updateButton);
        add(deleteButton);
        add(viewButton);

        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        try {

            // ================= ADD ADMIN =================

            if (e.getSource() == addButton) {

                String name = nameField.getText().trim();
                String email = emailField.getText().trim();
                String password =
                        new String(passwordField.getPassword());
                String role = roleField.getText().trim();

                if (name.isEmpty() ||
                    email.isEmpty() ||
                    password.isEmpty() ||
                    role.isEmpty()) {

                    JOptionPane.showMessageDialog(
                            this,
                            "Please fill all fields!",
                            "Input Error",
                            JOptionPane.ERROR_MESSAGE
                    );

                    return;
                }

                if (!email.contains("@") || !email.contains(".")) {

                    JOptionPane.showMessageDialog(
                            this,
                            "Please enter a valid email!",
                            "Invalid Email",
                            JOptionPane.ERROR_MESSAGE
                    );

                    return;
                }

                Admin admin = new Admin(
                        name,
                        email,
                        password,
                        role
                );

                boolean result = service.addAdmin(admin);

                if (result) {

                    JOptionPane.showMessageDialog(
                            this,
                            "Admin Added Successfully!",
                            "Success",
                            JOptionPane.INFORMATION_MESSAGE
                    );

                    clearFields();

                } else {

                    JOptionPane.showMessageDialog(
                            this,
                            "Admin could not be added!",
                            "Error",
                            JOptionPane.ERROR_MESSAGE
                    );
                }
            }

            // ================= UPDATE ADMIN =================

            else if (e.getSource() == updateButton) {

                String idText = idField.getText().trim();
                String name = nameField.getText().trim();
                String email = emailField.getText().trim();
                String password =
                        new String(passwordField.getPassword());
                String role = roleField.getText().trim();

                if (idText.isEmpty() ||
                    name.isEmpty() ||
                    email.isEmpty() ||
                    password.isEmpty() ||
                    role.isEmpty()) {

                    JOptionPane.showMessageDialog(
                            this,
                            "Please fill all fields!",
                            "Input Error",
                            JOptionPane.ERROR_MESSAGE
                    );

                    return;
                }

                int adminId = Integer.parseInt(idText);

                if (!email.contains("@") || !email.contains(".")) {

                    JOptionPane.showMessageDialog(
                            this,
                            "Please enter a valid email!",
                            "Invalid Email",
                            JOptionPane.ERROR_MESSAGE
                    );

                    return;
                }

                Admin admin = new Admin(
                        name,
                        email,
                        password,
                        role
                );

                admin.setAdminId(adminId);

                boolean result = service.updateAdmin(admin);

                if (result) {

                    JOptionPane.showMessageDialog(
                            this,
                            "Admin Updated Successfully!",
                            "Success",
                            JOptionPane.INFORMATION_MESSAGE
                    );

                    clearFields();

                } else {

                    JOptionPane.showMessageDialog(
                            this,
                            "Admin ID not found!",
                            "Error",
                            JOptionPane.ERROR_MESSAGE
                    );
                }
            }

            // ================= DELETE ADMIN =================

            else if (e.getSource() == deleteButton) {

                String idText = idField.getText().trim();

                if (idText.isEmpty()) {

                    JOptionPane.showMessageDialog(
                            this,
                            "Please enter Admin ID!",
                            "Input Error",
                            JOptionPane.ERROR_MESSAGE
                    );

                    return;
                }

                int adminId = Integer.parseInt(idText);

                int choice = JOptionPane.showConfirmDialog(
                        this,
                        "Are you sure you want to delete this admin?",
                        "Confirm Delete",
                        JOptionPane.YES_NO_OPTION
                );

                if (choice == JOptionPane.YES_OPTION) {

                    boolean result =
                            service.deleteAdmin(adminId);

                    if (result) {

                        JOptionPane.showMessageDialog(
                                this,
                                "Admin Deleted Successfully!",
                                "Success",
                                JOptionPane.INFORMATION_MESSAGE
                        );

                        clearFields();

                    } else {

                        JOptionPane.showMessageDialog(
                                this,
                                "Admin ID not found!",
                                "Error",
                                JOptionPane.ERROR_MESSAGE
                        );
                    }
                }
            }

            // ================= VIEW ADMINS =================

            else if (e.getSource() == viewButton) {

                service.viewAdmins();

                JOptionPane.showMessageDialog(
                        this,
                        "Admins displayed in Output/Terminal!",
                        "Admin List",
                        JOptionPane.INFORMATION_MESSAGE
                );
            }

        } catch (NumberFormatException ex) {

            JOptionPane.showMessageDialog(
                    this,
                    "Admin ID must be a valid number!",
                    "Invalid Admin ID",
                    JOptionPane.ERROR_MESSAGE
            );

        } catch (Exception ex) {

            JOptionPane.showMessageDialog(
                    this,
                    "Something went wrong! Please check your details.",
                    "Error",
                    JOptionPane.ERROR_MESSAGE
            );

            ex.printStackTrace();
        }
    }

    // ================= CLEAR FIELDS =================

    private void clearFields() {

        idField.setText("");
        nameField.setText("");
        emailField.setText("");
        passwordField.setText("");
        roleField.setText("Administrator");
    }

    // ================= MAIN =================

    public static void main(String[] args) {
        new AdminForm();
    }
}