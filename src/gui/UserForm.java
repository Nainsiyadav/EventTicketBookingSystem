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

        // Service Object
        service = new UserService();

        // Title
        titleLabel = new JLabel("USER MANAGEMENT");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 22));
        titleLabel.setBounds(180, 30, 300, 30);
        add(titleLabel);

        // User ID
        idLabel = new JLabel("User ID:");
        idLabel.setBounds(80, 90, 100, 25);

        idField = new JTextField();
        idField.setBounds(180, 90, 200, 25);

        add(idLabel);
        add(idField);

        // Name
        nameLabel = new JLabel("Name:");
        nameLabel.setBounds(80, 130, 100, 25);

        nameField = new JTextField();
        nameField.setBounds(180, 130, 200, 25);

        add(nameLabel);
        add(nameField);

        // Email
        emailLabel = new JLabel("Email:");
        emailLabel.setBounds(80, 170, 100, 25);

        emailField = new JTextField();
        emailField.setBounds(180, 170, 200, 25);

        add(emailLabel);
        add(emailField);

        // Phone
        phoneLabel = new JLabel("Phone:");
        phoneLabel.setBounds(80, 210, 100, 25);

        phoneField = new JTextField();
        phoneField.setBounds(180, 210, 200, 25);

        add(phoneLabel);
        add(phoneField);

        // Password
        passwordLabel = new JLabel("Password:");
        passwordLabel.setBounds(80, 250, 100, 25);

        passwordField = new JPasswordField();
        passwordField.setBounds(180, 250, 200, 25);

        add(passwordLabel);
        add(passwordField);

        // Buttons
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

    @Override
    public void actionPerformed(ActionEvent e) {

        String name = nameField.getText();
        String email = emailField.getText();
        String phone = phoneField.getText();
        String password = new String(passwordField.getPassword());

        // ADD USER
        if (e.getSource() == addButton) {

            User user = new User(name, email, phone, password);

            service.addUser(user);

            JOptionPane.showMessageDialog(this,
                    "User Added Successfully!");
        }

        // UPDATE USER
        else if (e.getSource() == updateButton) {

            int id = Integer.parseInt(idField.getText());

            User user = new User(name, email, phone, password);
            user.setUserId(id);

            service.updateUser(user);

            JOptionPane.showMessageDialog(this,
                    "User Updated Successfully!");
        }

        // DELETE USER
        else if (e.getSource() == deleteButton) {

            int id = Integer.parseInt(idField.getText());

            service.deleteUser(id);

            JOptionPane.showMessageDialog(this,
                    "User Deleted Successfully!");
        }

        // VIEW USERS
        else if (e.getSource() == viewButton) {

            service.viewUsers();

            JOptionPane.showMessageDialog(this,
                    "Users displayed in Output/Terminal!");
        }

        // Clear Fields
        idField.setText("");
        nameField.setText("");
        emailField.setText("");
        phoneField.setText("");
        passwordField.setText("");
    }

    public static void main(String[] args) {
        new UserForm();
    }
}