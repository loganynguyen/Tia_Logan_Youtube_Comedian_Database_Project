package youtube;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class ReviewDAO {
	private static final long serialVersionUID = 1L;
	private static Connection connect = null;
	private static Statement statement = null;
	private static PreparedStatement preparedStatement = null;
	private static ResultSet resultSet = null;
	
	public ReviewDAO() {}
	
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
		statement.executeUpdate("DROP TABLE IF EXISTS review");
		statement.executeUpdate("SET FOREIGN_KEY_CHECKS = 1");
 	}
 	
	// Function that creates and seeds the table
	public void createTable() throws SQLException {
		try {
			connect_func();
			
			// create the review table
			String s = "CREATE TABLE review (" +
					"reviewid INTEGER unsigned NOT NULL AUTO_INCREMENT," +
					"url VARCHAR(50) NOT NULL," +
					"username VARCHAR(100) NOT NULL," +
					"remark VARCHAR(100) NOT NULL," +
					"score CHAR(1) NOT NULL," +
					"FOREIGN KEY(url) REFERENCES video(url)," +
                    "FOREIGN KEY(username) REFERENCES user(email)," +
					"PRIMARY KEY(reviewid) )";
			statement.executeUpdate(s);
			System.out.println("'review' table created.");
			System.out.println("'Review' table created.");
			// seed the table with 10 reviews
			String s2 = "INSERT INTO review(url, username, remark, score) VALUES" +
					"('https://www.youtube.com/embed/tDolNU89SXI', 'mary@gmail.com', 'So many videos and really helpful', 'P'), " +
					"('https://www.youtube.com/embed/tDolNU89SXI', 'luke@gmail.com', 'Can search anything you want', 'F'), " +
					"('https://www.youtube.com/embed/QdAhlnj97B0', 'john@gmail.com', 'Very helpful in making sites', 'G'), " +
					"('https://www.youtube.com/embed/QdAhlnj97B0', 'tess@gmail.com', 'Very helpful search engine', 'E'), " +
					"('https://www.youtube.com/embed/QdAhlnj97B0', 'tia@gmail.com', 'Can send emails from any part to the world and recieve emails too', 'G'), " +
					"('https://www.youtube.com/embed/QdAhlnj97B0', 'logan@gmail.com', 'Upload photos and videos', 'P'), " +
					"('https://www.youtube.com/embed/QdAhlnj97B0', 'junwen@gmail.com', 'purchase anything you want and get delivered in 2 days', 'F'), " +
					"('https://www.youtube.com/embed/kMiEGUWBn98', 'evan@gmail.com', 'Upload status and stories', 'F'), " +
					"('https://www.youtube.com/embed/kMiEGUWBn98', 'evanlog@gmail.com', 'purchase phones you want', 'P'), " +
					"('https://www.youtube.com/embed/kMiEGUWBn98', 'bob@gmail.com', 'Very cheap shopping but ships slow', 'E');";
			statement.executeUpdate(s2);
			System.out.println("10 reviews added.");
			
		} catch (Exception e) {
			System.out.println(e);
		} finally {
			close();
		}
	}
	
    public void insert(String url, String user, String remark, char rating) throws SQLException {
    	connect_func();         
    	String sql = "insert into  review (url, username, remark, score) values (?, ?, ?, ?)";
		preparedStatement = (PreparedStatement) connect.prepareStatement(sql);
		preparedStatement.setString(1, url);
		preparedStatement.setString(2, user);
		preparedStatement.setString(3, remark);
		preparedStatement.setString(4, String.valueOf(rating));
		preparedStatement.executeUpdate();
        preparedStatement.close();
        disconnect();
    }
    
    public boolean checkPostUser(String url, String currentUser) throws SQLException 
    {
        connect_func();
        boolean flag = true;

        statement = (Statement) connect.createStatement();
        String s = "Select postuser from video where url='" + url + "'";
        ResultSet rs = statement.executeQuery(s);
        rs.next();
        String postUser = rs.getString("postuser");

        if(postUser.contentEquals(currentUser))
        {
            flag = false;
        }

        return flag;
    }
	
	private void close() throws SQLException {
		if (resultSet != null)
			resultSet.close();
		if (statement != null)
			statement.close();
	}
}