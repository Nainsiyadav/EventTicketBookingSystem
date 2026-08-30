package main;

import model.User;
import service.UserService;

public class TestUser {

    public static void main(String[] args) {

        UserService service = new UserService();

        // ================= ADD USER =================

        User user = new User(
                "Test User",
                "test@gmail.com",
                "9876543210",
                "123456"
        );

        boolean addResult = service.addUser(user);

        System.out.println(
                addResult
                        ? "User Added Successfully!"
                        : "User could not be added."
        );


        // ================= VIEW USERS =================

        service.viewUsers();


        // ================= UPDATE =================
        // Test karne ke liye actual User ID use karo

        /*
        user.setUserId(1);

        user.setName("Updated User");
        user.setEmail("updated@gmail.com");
        user.setphone("9999999999");
        user.setPassword("654321");

        boolean updateResult =
                service.updateUser(user, "123456");

        System.out.println(
                updateResult
                        ? "User Updated Successfully!"
                        : "User Update Failed!"
        );
        */


        // ================= DELETE =================
        // Test karne ke liye actual User ID use karo

        /*
        boolean deleteResult =
                service.deleteUser(1, "123456");

        System.out.println(
                deleteResult
                        ? "User Deleted Successfully!"
                        : "User Delete Failed!"
        );
        */
    }
}