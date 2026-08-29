package service;

import database.DBConnection;
import interfaces.UserOperations;
import model.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class UserService implements UserOperations {

    // ================= ADD USER =================

    @Override
    public boolean addUser(User user) {

        String sql =
                "INSERT INTO users(name, email, phone, password) " +
                "VALUES (?, ?, ?, ?)";

        try (
                Connection con = DBConnection.getConnection();
                PreparedStatement ps = con.prepareStatement(sql)
        ) {

            ps.setString(1, user.getName());
            ps.setString(2, user.getEmail());
            ps.setString(3, user.getphone());
            ps.setString(4, user.getPassword());

            int rows = ps.executeUpdate();

            if (rows > 0) {
                System.out.println("User added successfully!");
                return true;
            }

        } catch (Exception e) {

            System.out.println("Error while adding user!");
            e.printStackTrace();
        }

        return false;
    }


    // ================= VIEW USERS =================

    @Override
    public void viewUsers() {

        String sql = "SELECT * FROM users";

        try (
                Connection con = DBConnection.getConnection();
                PreparedStatement ps = con.prepareStatement(sql);
                ResultSet rs = ps.executeQuery()
        ) {

            System.out.println("\n========== USERS ==========");

            while (rs.next()) {

                System.out.println(
                        rs.getInt("user_id") + " | " +
                        rs.getString("name") + " | " +
                        rs.getString("email") + " | " +
                        rs.getString("phone")
                );
            }

            System.out.println("============================\n");

        } catch (Exception e) {

            System.out.println("Error while viewing users!");
            e.printStackTrace();
        }
    }


    // ================= UPDATE USER =================

    @Override
    public boolean updateUser(User user, String password) {

        // First check User ID and current password

        String checkSql =
                "SELECT password FROM users WHERE user_id = ?";

        try (
                Connection con = DBConnection.getConnection();
                PreparedStatement checkPs =
                        con.prepareStatement(checkSql)
        ) {

            checkPs.setInt(1, user.getUserId());

            ResultSet rs = checkPs.executeQuery();

            // User ID does not exist
            if (!rs.next()) {

                System.out.println("User not found!");
                return false;
            }

            // Get current password from database
            String actualPassword =
                    rs.getString("password");

            // Check current password
            if (!actualPassword.equals(password)) {

                System.out.println("Invalid password!");
                return false;
            }

        } catch (Exception e) {

            System.out.println("Error while checking password!");
            e.printStackTrace();

            return false;
        }


        // ================= PASSWORD CORRECT =================
        // Now update the user

        String sql =
                "UPDATE users SET " +
                "name = ?, " +
                "email = ?, " +
                "phone = ?, " +
                "password = ? " +
                "WHERE user_id = ?";

        try (
                Connection con = DBConnection.getConnection();
                PreparedStatement ps =
                        con.prepareStatement(sql)
        ) {

            ps.setString(1, user.getName());
            ps.setString(2, user.getEmail());
            ps.setString(3, user.getphone());
            ps.setString(4, user.getPassword());
            ps.setInt(5, user.getUserId());

            int rows = ps.executeUpdate();

            if (rows > 0) {

                System.out.println(
                        "User updated successfully!"
                );

                return true;
            }

        } catch (Exception e) {

            System.out.println(
                    "Error while updating user!"
            );

            e.printStackTrace();
        }

        return false;
    }


    // ================= DELETE USER =================

    @Override
    public boolean deleteUser(int userId, String password) {

        // First check User ID and password

        String checkSql =
                "SELECT password FROM users WHERE user_id = ?";

        try (
                Connection con = DBConnection.getConnection();
                PreparedStatement checkPs =
                        con.prepareStatement(checkSql)
        ) {

            checkPs.setInt(1, userId);

            ResultSet rs = checkPs.executeQuery();

            // User ID does not exist
            if (!rs.next()) {

                System.out.println("User not found!");
                return false;
            }

            // Get password from database
            String actualPassword =
                    rs.getString("password");

            // Check password
            if (!actualPassword.equals(password)) {

                System.out.println("Invalid password!");
                return false;
            }

        } catch (Exception e) {

            System.out.println(
                    "Error while checking password!"
            );

            e.printStackTrace();

            return false;
        }


        // ================= PASSWORD CORRECT =================
        // Now delete the user

        String sql =
                "DELETE FROM users WHERE user_id = ?";

        try (
                Connection con = DBConnection.getConnection();
                PreparedStatement ps =
                        con.prepareStatement(sql)
        ) {

            ps.setInt(1, userId);

            int rows = ps.executeUpdate();

            if (rows > 0) {

                System.out.println(
                        "User deleted successfully!"
                );

                return true;
            }

        } catch (Exception e) {

            System.out.println(
                    "Error while deleting user!"
            );

            e.printStackTrace();
        }

        return false;
    }
}