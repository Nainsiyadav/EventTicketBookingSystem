package interfaces;

import model.Admin;

public interface AdminOperations {

    // Admin Login
    boolean login(String email, String password);

    // Add Admin
    boolean addAdmin(Admin admin);

    // View Admins
    void viewAdmins();

    // Update Admin
    boolean updateAdmin(Admin admin);

    // Delete Admin
    boolean deleteAdmin(int adminId);
}
