package service;

import database.DBConnection;
import interfaces.UserOperations;
import model.User;

import  java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class UserService  implements UserOperations{
    //create - add user
  @Override
  public void addUser(User user){
    String sql = "INSERT INTO users(name,email,phone,password) VALUES (?,?,?,?)";
    try(
        Connection con = DBConnection.getConnection();
        PreparedStatement ps = con.prepareStatement(sql)
    ){
        ps.setString(1, user.getName());
        ps.setString(2, user.getEmail());
        ps.setString(3, user.getphone());
        ps.setString(4, user.getPassword());

        ps.executeUpdate();

        System.out.println("USer added successfully!");

    }
    catch(Exception e){
        e.printStackTrace();
    }
  }

  //read - view Uers
  @Override
  public void viewUsers(){
    String sql = "SELECT * FROM users";

    try(
        Connection con = DBConnection.getConnection();
        PreparedStatement ps = con.prepareStatement(sql);
        ResultSet rs = ps.executeQuery()
    ){
        while(rs.next()){
            System.out.println(
                rs.getInt("user_id")+ " | " +
                rs.getString("name")+ " | " +
                rs.getString("email")+ " | " +
                rs.getString("phone")
            );
        }
    }catch(Exception e){
        e.printStackTrace();
    }
  }

  //update - update User
      @Override
    public void updateUser(User user) {

        String sql = "UPDATE users SET name=?, email=?, phone=?, password=? WHERE user_id=?";

        try (
            Connection con = DBConnection.getConnection();
            PreparedStatement ps = con.prepareStatement(sql)
        ) {

            ps.setString(1, user.getName());
            ps.setString(2, user.getEmail());
            ps.setString(3, user.getphone());
            ps.setString(4, user.getPassword());
            ps.setInt(5, user.getUserId());

            ps.executeUpdate();

            System.out.println("User updated successfully!");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // DELETE - Delete User
    @Override
    public void deleteUser(int userId) {

        String sql = "DELETE FROM users WHERE user_id=?";

        try (
            Connection con = DBConnection.getConnection();
            PreparedStatement ps = con.prepareStatement(sql)
        ) {

            ps.setInt(1, userId);

            ps.executeUpdate();

            System.out.println("User deleted successfully!");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

