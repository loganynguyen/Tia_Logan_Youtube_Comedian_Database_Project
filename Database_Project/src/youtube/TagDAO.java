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

import java.io.PrintWriter;





public class TagDAO extends HttpServlet
{
	
	private static final long serialVersionUID = 1L;
private static Connection connect = null;
private static Statement statement = null;
private static PreparedStatement preparedStatement = null;
private static ResultSet resultSet = null;

public TagDAO() {}

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
	statement.executeUpdate("DROP TABLE IF EXISTS videotag");
	statement.executeUpdate("SET FOREIGN_KEY_CHECKS = 1");
	}
	
	// Return a list of tags specific 
	 public List<String> listAllTagsSpecifcToUrl(String Url) throws SQLException {
	        
	        List<String> tagsSpecificToUrl = new ArrayList<String>(); 
	        String sql = "SELECT tag FROM videotag where url='"+Url+"'";     
	        connect_func();      
	        statement =  (Statement) connect.createStatement();
	        resultSet = statement.executeQuery(sql);
	        while (resultSet.next())
	            {
	                String tag = resultSet.getString("tag");
	                tagsSpecificToUrl.add(tag);
	        	}
	        resultSet.close();
	        statement.close();         
	        disconnect();        
	        return tagsSpecificToUrl;
	    }
	 
	 
	 
  public void insert(Tag tag) throws SQLException 
  {
  connect_func();         
  String sql = "insert into videotag(url, tag) values (?, ?)";
  preparedStatement = (PreparedStatement) connect.prepareStatement(sql);
  preparedStatement.setString(1, tag.getUrl());
  preparedStatement.setString(2, tag.getTags());

  preparedStatement.executeUpdate();
  preparedStatement.close();
  disconnect();
 }

	
	
	
// Function that creates and seeds the table
public void createTable() throws SQLException {
	try {
		connect_func();
		
		// create the user table
		String s = "CREATE TABLE videotag (" +
				"url VARCHAR(50) NOT NULL," +
				"tag VARCHAR(50) NOT NULL," +
				"PRIMARY KEY(url, tag) )";
		statement.executeUpdate(s);
		System.out.println("videotag 'table' created.");
		
		// seed the table with 10 users
		String s2 = "INSERT INTO videotag(url, tag) VALUES" +
				"('youtube.com', 'Somany videos and really 'helpful' ), " +
				"('google.com', 'browser'), " +
				"('wix.com', 'helpful'), " +
				"('yahoo.com', 'browser'), " +
				"('gmail.com', 'emails'), " +
				"('facebook.com', 'photos'), " +
				"('facebook.com', 'videos'), " +
				"('instagram.com', 'stories'), " +
				"('samsung.com', 'phones'), " +
				"('samsung.com', 'tabs');";
		statement.executeUpdate(s2);
		System.out.println("10 videotag added.");
		
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