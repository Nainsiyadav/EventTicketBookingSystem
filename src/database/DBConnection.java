package database;

import java.sql.Connection;
import java.sql.DriverManager;

public class DBConnection{

    private static final String URL = "jdbc:mysql://localhost:3306/event_ticket_db";
    private static final String USER = "root";
    private static final String PASSWORD = "nainsi";

    public static Connection getConnection(){

        Connection con = null;

        try{
            con = DriverManager.getConnection(URL,USER,PASSWORD);
            System.out.println("Database Connected Successfully!");
        }
        catch(Exception e){
            System.out.println("Database Connection Failed!");
            e.printStackTrace();
        }
        return con;
    }
}