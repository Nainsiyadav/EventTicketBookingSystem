package interfaces;

import model.User;

public interface UserOperations {

    boolean addUser(User user);

    void viewUsers();

    boolean updateUser(User user, String password);

    boolean deleteUser(int userId, String password);
}