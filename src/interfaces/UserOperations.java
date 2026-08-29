package interfaces;
import model.User;

public interface UserOperations{
    void addUser(User user);
    void  viewUsers();
    void updateUser(User user);
    void deleteUser(int userId);
}