package service;

import database.DBConnection;
import interfaces.AdminOperations;
import model.Admin;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class AdminService implements AdminOperations {

    // ================= ADMIN LOGIN =================

    @Override
    public boolean login(String email, String password) {

        String sql = "SELECT * FROM admins WHERE email=? AND password=?";

        try (
            Connection con = DBConnection.getConnection();
            PreparedStatement ps = con.prepareStatement(sql)
        ) {

            ps.setString(1, email);
            ps.setString(2, password);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                System.out.println("Admin login successful!");
                return true;
            }

            System.out.println("Invalid email or password!");
            return false;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }


    // ================= ADD ADMIN =================

    @Override
    public boolean addAdmin(Admin admin) {

        String sql =
                "INSERT INTO admins(name, email, password, role) VALUES (?, ?, ?, ?)";

        try (
            Connection con = DBConnection.getConnection();
            PreparedStatement ps = con.prepareStatement(sql)
        ) {

            ps.setString(1, admin.getName());
            ps.setString(2, admin.getEmail());
            ps.setString(3, admin.getPassword());
            ps.setString(4, admin.getRole());

            int rows = ps.executeUpdate();

            if (rows > 0) {
                System.out.println("Admin added successfully!");
                return true;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }


    // ================= VIEW ADMINS =================

    @Override
    public void viewAdmins() {

        String sql = "SELECT * FROM admins";

        try (
            Connection con = DBConnection.getConnection();
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery()
        ) {

            while (rs.next()) {

                System.out.println(
                    rs.getInt("admin_id") + " | " +
                    rs.getString("name") + " | " +
                    rs.getString("email") + " | " +
                    rs.getString("role")
                );
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    // ================= UPDATE ADMIN =================

    @Override
    public boolean updateAdmin(Admin admin) {

        String sql =
                "UPDATE admins SET name=?, email=?, password=?, role=? WHERE admin_id=?";

        try (
            Connection con = DBConnection.getConnection();
            PreparedStatement ps = con.prepareStatement(sql)
        ) {

            ps.setString(1, admin.getName());
            ps.setString(2, admin.getEmail());
            ps.setString(3, admin.getPassword());
            ps.setString(4, admin.getRole());
            ps.setInt(5, admin.getAdminId());

            int rows = ps.executeUpdate();

            if (rows > 0) {
                System.out.println("Admin updated successfully!");
                return true;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }


    // ================= DELETE ADMIN =================

    @Override
    public boolean deleteAdmin(int adminId) {

        String sql = "DELETE FROM admins WHERE admin_id=?";

        try (
            Connection con = DBConnection.getConnection();
            PreparedStatement ps = con.prepareStatement(sql)
        ) {

            ps.setInt(1, adminId);

            int rows = ps.executeUpdate();

            if (rows > 0) {
                System.out.println("Admin deleted successfully!");
                return true;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }
}
