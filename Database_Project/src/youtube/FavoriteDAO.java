package youtube;

import java.net.ConnectException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.DriverManager;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.sql.PreparedStatement;
//import java.sql.Connection;
//import java.sql.PreparedStatement;
import java.sql.ResultSet;
//import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class FavoriteDAO {
	private static final long serialVersionUID = 1L;
	private static Connection connect = null;
	private static Statement statement = null;
	private static PreparedStatement preparedStatement = null;
	private static ResultSet resultSet = null;
	
	public FavoriteDAO() {}
	
	protected void connect_func() throws SQLException {
        if (connect == null || connect.isClosed()) {
    		System.out.println("Connecting to the database...");        
    		try {
                Class.forName("com.mysql.jdbc.Driver");
            } catch (ClassNotFoundException e) {
                throw new SQLException(e);
            }
            connect = (Connection) DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/youtube?" + "user=john&password=pass1234");
            System.out.println(connect);
        }
		System.out.println("Connection established.");        
    }
	
	protected void disconnect() throws SQLException {
        if (connect != null && !connect.isClosed()) {
        	connect.close();
        }
    }
	
	// Function that creates and seeds the table
 	public void dropTable() throws SQLException {
		connect_func();
		
		// drop the table and create a new one
		statement = (Statement) connect.createStatement();
		statement.executeUpdate("SET FOREIGN_KEY_CHECKS = 0");
		statement.executeUpdate("DROP TABLE IF EXISTS favorite");
		statement.executeUpdate("SET FOREIGN_KEY_CHECKS = 1");
 	}
 	
	public List<Favorite> getFavObjects (String currentUser) throws SQLException {
    	
		String sql = "SELECT * FROM favorite where username='" + currentUser + "'";   
        List<Favorite> favlist = new ArrayList<Favorite>();
        
    	connect_func();     
        statement =  (Statement) connect.createStatement();
        resultSet = statement.executeQuery(sql);
        while(resultSet.next())
    	{
        	int Cid = resultSet.getInt("comedianId");
        	int Fid = resultSet.getInt("favoriteId");
        	String user = resultSet.getString("username");
        	Favorite favorite = new Favorite(Fid, user, Cid);
        	favlist.add(favorite);
    	}
        
        resultSet.close();
        statement.close();        
        disconnect();       
        return favlist;	
	}
	
	public boolean ifComedianExistinFavList(int comedianID, String user) throws SQLException 
    {
		connect_func();
		boolean status = false;
		
		statement = (Statement) connect.createStatement();
		String s = "Select * from favorite where comedianId='" + comedianID + "' and username='" + user + "'";
		resultSet = statement.executeQuery(s);
		 resultSet.close();
	        statement.close();        
	        disconnect();    
	    if(resultSet.next())
	    	status = true;
	    return status;
	    
	}
	
    public boolean delete(int comedianid, String username) throws SQLException {
        String sql = "DELETE FROM favorite WHERE username = ? and comedianId = ?";        
        connect_func();
         
        preparedStatement = (PreparedStatement) connect.prepareStatement(sql);
        preparedStatement.setString(1, username);
        preparedStatement.setInt(2, comedianid);
         
        boolean rowDeleted = preparedStatement.executeUpdate() > 0;
        preparedStatement.close();
        resultSet.close();
        statement.close();        
        disconnect();    

        return rowDeleted;     
    }
    
    public void add(int comedianid, String username) throws SQLException {       
    	String sql = "insert into favorite (username, comedianId) values (?, ?)";
		preparedStatement = (PreparedStatement) connect.prepareStatement(sql);
		preparedStatement.setString(1, username);
		preparedStatement.setInt(2, comedianid);
		preparedStatement.executeUpdate();
        preparedStatement.close();
        disconnect();
    }
    
    public boolean ifComedianExistsInFavList(int comedianID, String user) throws SQLException
    {
        connect_func();
        boolean status = false;
       
        statement = (Statement) connect.createStatement();
        String s = "Select * from favorite where comedianId='" + comedianID + "' and username='" + user + "'";
        resultSet = statement.executeQuery(s);
       
        if(resultSet.next())
            status = true;
        return status;
    }
 	
 	// Function that creates and seeds the table
 	public void createTable() throws SQLException {
        try {
            connect_func();
           
            // create the user table
            String s = "CREATE TABLE favorite (" +
                    "favoriteId INTEGER NOT NULL AUTO_INCREMENT," +
                    "username VARCHAR(50) NOT NULL," +
                    "comedianId INTEGER NOT NULL," +
                    "FOREIGN KEY(username) REFERENCES User(email)," +
                    "FOREIGN KEY(comedianId) REFERENCES Comedian(comedianid)," +
                    "PRIMARY KEY(favoriteId));";
            statement.executeUpdate(s);
            System.out.println("favorite 'table' created.");
           
            // seed the table with 10 users
            String s2 = "INSERT INTO favorite(username, comedianId) VALUES" +
                    "('mary@gmail.com', '1'), " +
                    "('mary@gmail.com', '6'), " +            		
                    "('mary@gmail.com', '9'), " +            		
                    "('luke@gmail.com', '6'), " +   
                    "('luke@gmail.com', '7'), " +                    
                    "('luke@gmail.com', '8'), " +   
                    "('luke@gmail.com', '9'), " +                    
                    "('john@gmail.com', '8'), " +
                    "('john@gmail.com', '9'), " +
                    "('john@gmail.com', '10'), " +
                    "('tess@gmail.com', '1'), " +
                    "('tess@gmail.com', '2'), " +
                    "('tess@gmail.com', '9'), " +
                    "('tess@gmail.com', '6'), " +
                    "('tia@gmail.com', '1'), " +
                    "('tia@gmail.com', '2'), " +
                    "('tia@gmail.com', '3'), " +
                    "('tia@gmail.com', '10'), " +
                    "('tia@gmail.com', '9'), " +
                    "('logan@gmail.com', '6'), " +
                    "('logan@gmail.com', '9'), " +
                    "('junwen@gmail.com', '6'), " +
                    "('junwen@gmail.com', '7'), " +
                    "('junwen@gmail.com', '8'), " +
                    "('junwen@gmail.com', '9'), " +
                    "('evan@gmail.com', '1'), " +
                    "('evan@gmail.com', '2'), " +
                    "('evan@gmail.com', '3'), " +
                    "('evan@gmail.com', '8'), " +
                    "('evan@gmail.com', '9'), " +
                    "('bob@gmail.com', '8'), " +
                    "('bob@gmail.com', '9'), " +
                    "('bob@gmail.com', '10');";
            statement.executeUpdate(s2);
            System.out.println("10 favorite comedians added.");
           
        } catch (Exception e) {
            System.out.println(e);
        } finally {
            close();
        }
    }
   
    private void close() throws SQLException {
        if (resultSet != null)
            resultSet.close();
        if (statement != null)
            statement.close();
    }
}