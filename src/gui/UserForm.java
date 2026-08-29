package gui;

import model.User;
import service.UserService;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class UserForm extends JFrame implements ActionListener {

    JLabel titleLabel, idLabel, nameLabel, emailLabel, phoneLabel, passwordLabel;

    JTextField idField, nameField, emailField, phoneField;
    JPasswordField passwordField;

    JButton addButton, updateButton, deleteButton, viewButton;

    UserService service;

    public UserForm() {

        // Frame Settings
        setTitle("User Management");
        setSize(600, 500);
        setLayout(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        service = new UserService();

        // ================= TITLE =================

        titleLabel = new JLabel("USER MANAGEMENT");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 22));
        titleLabel.setBounds(180, 30, 300, 30);
        add(titleLabel);


        // ================= USER ID =================

        idLabel = new JLabel("User ID:");
        idLabel.setBounds(80, 90, 100, 25);

        idField = new JTextField();
        idField.setBounds(180, 90, 200, 25);

        add(idLabel);
        add(idField);


        // ================= NAME =================

        nameLabel = new JLabel("Name:");
        nameLabel.setBounds(80, 130, 100, 25);

        nameField = new JTextField();
        nameField.setBounds(180, 130, 200, 25);

        add(nameLabel);
        add(nameField);


        // ================= EMAIL =================

        emailLabel = new JLabel("Email:");
        emailLabel.setBounds(80, 170, 100, 25);

        emailField = new JTextField();
        emailField.setBounds(180, 170, 200, 25);

        add(emailLabel);
        add(emailField);


        // ================= PHONE =================

        phoneLabel = new JLabel("Phone:");
        phoneLabel.setBounds(80, 210, 100, 25);

        phoneField = new JTextField();
        phoneField.setBounds(180, 210, 200, 25);

        add(phoneLabel);
        add(phoneField);


        // ================= PASSWORD =================

        passwordLabel = new JLabel("Password:");
        passwordLabel.setBounds(80, 250, 100, 25);

        passwordField = new JPasswordField();
        passwordField.setBounds(180, 250, 200, 25);

        add(passwordLabel);
        add(passwordField);


        // ================= BUTTONS =================

        addButton = new JButton("Add");
        addButton.setBounds(100, 310, 100, 35);

        updateButton = new JButton("Update");
        updateButton.setBounds(210, 310, 100, 35);

        deleteButton = new JButton("Delete");
        deleteButton.setBounds(320, 310, 100, 35);

        viewButton = new JButton("View Users");
        viewButton.setBounds(210, 360, 130, 35);

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


    // ================= VALIDATION =================

    private boolean validateUserDetails() {

        String name = nameField.getText().trim();
        String email = emailField.getText().trim();
        String phone = phoneField.getText().trim();
        String password = new String(passwordField.getPassword());


        // NAME

        if (name.isEmpty()) {

            JOptionPane.showMessageDialog(this,
                    "Name cannot be empty!");

            return false;
        }

        if (!name.matches("[a-zA-Z ]+")) {

            JOptionPane.showMessageDialog(this,
                    "Name should contain only letters!");

            return false;
        }


        // EMAIL

        if (email.isEmpty()) {

            JOptionPane.showMessageDialog(this,
                    "Email cannot be empty!");

            return false;
        }

        if (!email.matches(
                "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$")) {

            JOptionPane.showMessageDialog(this,
                    "Please enter a valid email!");

            return false;
        }


        // PHONE

        if (!phone.matches("\\d{10}")) {

            JOptionPane.showMessageDialog(this,
                    "Phone number must contain exactly 10 digits!");

            return false;
        }


        // PASSWORD

        if (password.length() < 5) {

            JOptionPane.showMessageDialog(this,
                    "Password must contain at least 5 characters!");

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

                String name = nameField.getText().trim();
                String email = emailField.getText().trim();
                String phone = phoneField.getText().trim();
                String password =
                        new String(passwordField.getPassword());

                User user =
                        new User(name, email, phone, password);

                boolean success = service.addUser(user);

                if (success) {

                    JOptionPane.showMessageDialog(this,
                            "User Added Successfully!");

                } else {

                    JOptionPane.showMessageDialog(this,
                            "User could not be added!");
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
                            idField.getText().trim());

                } catch (NumberFormatException ex) {

                    JOptionPane.showMessageDialog(this,
                            "Please enter a valid User ID!");

                    return;
                }


                if (id <= 0) {

                    JOptionPane.showMessageDialog(this,
                            "User ID must be greater than 0!");

                    return;
                }


                String name =
                        nameField.getText().trim();

                String email =
                        emailField.getText().trim();

                String phone =
                        phoneField.getText().trim();

                String password =
                        new String(passwordField.getPassword());


                User user =
                        new User(name, email, phone, password);

                user.setUserId(id);


                // Password used for verification
                String oldPassword =
                        JOptionPane.showInputDialog(
                                this,
                                "Enter your current password:"
                        );


                if (oldPassword == null ||
                        oldPassword.isEmpty()) {

                    JOptionPane.showMessageDialog(this,
                            "Password is required!");

                    return;
                }


                boolean success =
                        service.updateUser(user, oldPassword);


                if (success) {

                    JOptionPane.showMessageDialog(this,
                            "User Updated Successfully!");

                } else {

                    JOptionPane.showMessageDialog(this,
                            "Invalid User ID or Password!\nUser was not updated.");
                }
            }


            // ================= DELETE =================

            else if (e.getSource() == deleteButton) {

                int id;

                try {

                    id = Integer.parseInt(
                            idField.getText().trim());

                } catch (NumberFormatException ex) {

                    JOptionPane.showMessageDialog(this,
                            "Please enter a valid User ID!");

                    return;
                }


                if (id <= 0) {

                    JOptionPane.showMessageDialog(this,
                            "User ID must be greater than 0!");

                    return;
                }


                String password =
                        JOptionPane.showInputDialog(
                                this,
                                "Enter your password:"
                        );


                if (password == null ||
                        password.isEmpty()) {

                    JOptionPane.showMessageDialog(this,
                            "Password is required!");

                    return;
                }


                boolean success =
                        service.deleteUser(id, password);


                if (success) {

                    JOptionPane.showMessageDialog(this,
                            "User Deleted Successfully!");

                } else {

                    JOptionPane.showMessageDialog(this,
                            "Invalid User ID or Password!\nUser was not deleted.");
                }
            }


            // ================= VIEW =================

            else if (e.getSource() == viewButton) {

                service.viewUsers();

                JOptionPane.showMessageDialog(this,
                        "Users displayed in Output/Terminal!");
            }


            // ================= CLEAR =================

            idField.setText("");
            nameField.setText("");
            emailField.setText("");
            phoneField.setText("");
            passwordField.setText("");

        } catch (Exception ex) {

            JOptionPane.showMessageDialog(this,
                    "Something went wrong: " + ex.getMessage());
        }
    }


    public static void main(String[] args) {

        new UserForm();
    }
}