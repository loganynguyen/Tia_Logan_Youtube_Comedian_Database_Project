package youtube;

import youtube.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class FunctionDAO {
    private static final long serialVersionUID = 1L;
    private Connection connect = null;
    private Statement statement = null;
    private PreparedStatement preparedStatement = null;
    private ResultSet resultSet = null;
    
    
    public FunctionDAO() {}
    
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
    
     public List<Video> listAllVideo(String comedianFirstName) throws SQLException {
            
	 	 	connect_func();
	        statement = (Statement) connect.createStatement();
	        
        	List<Video> listVideo = new ArrayList<Video>();
    	 	List<String> tags = new ArrayList<String>();
    	 	
    	 	// collect all the different names
    	 	String sql1 = "SELECT comedianid FROM comedian where firstname='"+comedianFirstName+"'";      
            resultSet = statement.executeQuery(sql1);
            while (resultSet.next())
            {
                tags.add(resultSet.getString("comedianid"));
            }
            
            // go through all the videos of each comedian
            String sql2;
            for (int i = 0; i < tags.size(); i++)
            {
                // select the next comedian
            	sql2 = "SELECT * FROM video where comedianid='"+tags.get(i)+"'";
                resultSet = statement.executeQuery(sql2);
                
                // add all their videos to the listVideo object
                while (resultSet.next())
                {
	                String url = resultSet.getString("url");
	                String t = resultSet.getString("title");
	                String d = resultSet.getString("description");
	                String date = resultSet.getString("date");
	
	                Video v = new Video(url, t, d, date);
	                listVideo.add(v);
            	}
            }
                        
            resultSet.close();
            statement.close();         
            disconnect();        
            return listVideo;
        }
}