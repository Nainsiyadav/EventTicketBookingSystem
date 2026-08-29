package main;

import model.User;
import service.UserService;

public class TestUser {

    public static void main(String[] args) {

        // Create User object
        User user = new User(
                "Shristi",
                "shristi@gmail.com",
                "9876543210",
                "123456"
        );

        // Create UserService object
        UserService service = new UserService();

        // Add user to database
        //service.addUser(user);


        // View all users
        service.viewUsers();
        user.setUserId(3);
        user.setName("Shristi Pandey");
        user.setEmail("shristi.pandey@gmail.com");
        user.setPhone("9999999999");

        service.updateUser(user);

        service.viewUsers();
        //Delete user
        service.deleteUser(3);

        service.viewUsers();
    }
}