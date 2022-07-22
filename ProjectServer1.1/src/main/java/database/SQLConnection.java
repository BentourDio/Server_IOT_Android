package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

// Class to connect to the mysql database
public class SQLConnection{
    String driverClassName = "com.mysql.cj.jdbc.Driver";
    String url = "jdbc:mysql://127.0.0.1:3306/dangerdb?useSSL=false";
    String user = "project";
    String password = "12341234";
    Connection con = null;
    Statement stmt = null;

    // Constructor
    public SQLConnection(){
        try{
            // Load driver to memory using forName() method
            Class.forName(driverClassName);
        }
        catch(ClassNotFoundException ex){
            ex.printStackTrace();
        }
    }

    // Constructor with specific values
    public SQLConnection(String url, String user, String password){
        this.url = url;
        this.user = user;
        this.password = password;

        try{
            // Load driver to memory using forName() method
            Class.forName(driverClassName);
        }
        catch(ClassNotFoundException ex){
            ex.printStackTrace();
        }
    }

    // Connect to database
    public void CreateConnection(){
        try {
            // Registering driver using DriverManager
            // Establish connection to database using the given database url
            this.con = DriverManager.getConnection(url, user, password);

            // Create a statement
            stmt = con.createStatement();
        }
        catch(SQLException ex){
            ex.printStackTrace();
        }
    }

    // Close connection
    public void CloseConnection(){
        try{
            stmt.close();
            con.close();
        }
        catch(SQLException ex){
            ex.printStackTrace();
        }
    }
}
