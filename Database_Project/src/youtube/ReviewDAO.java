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
<<<<<<< HEAD
					"reviewId INTEGER NOT NULL AUO_INCREMENT," +
=======
					"reviewId VARCHAR(2) NOT NULL AUTO_INCREMENT," +
>>>>>>> branch 'master' of https://github.com/tia-gijo/Database_Project.git
					"url VARCHAR(50) NOT NULL," +
					"username VARCHAR(50) NOT NULL," +
					"remark VARCHAR(100) NOT NULL," +
<<<<<<< HEAD
					"score CHAR(1) NOT NULL," +
=======
					"score VARCHAR(20) NOT NULL," +
>>>>>>> branch 'master' of https://github.com/tia-gijo/Database_Project.git
					"PRIMARY KEY(reviewId) );";
			statement.executeUpdate(s);
			System.out.println("'Review' table created.");
			
			// seed the table with 10 reviews
			String s2 = "INSERT INTO review(url, username, remark, score) VALUES" +
<<<<<<< HEAD
					"('youtube.com', 'mary@gmail.com', 'Somany videos and really helpful', 'P'), " +
					"('google.com', 'luke@gmail.com', 'Can search anything you want', 'F'), " +
					"('wix.com', 'john@gmail.com', 'Very helpful in making sites', 'G'), " +
					"('yahoo.com', 'tess@gmail.com', 'Very helpful search engine', 'E'), " +
					"('gmail.com', 'tia@gmail.com', 'Can send emails from any part to the world and recieve emails too', 'G'), " +
					"('facebook.com', 'logan@gmail.com', 'Upload photos and videos', 'P'), " +
					"('amazon.com', 'junwen@gmail.com', 'purchase anything you want and get delivered in 2 days', 'F'), " +
					"('instagram.com', 'evan@gmail.com', 'Upload status and stories', 'F'), " +
					"('samsung.com', 'evanlogan@gmail.com', 'purchase phones you want', 'P'), " +
					"('ebay.com', 'bob@gmail.com', 'Very cheap shopping but ships slow', 'E');";
=======
					"('youtube.com', 'mary@gmail.com', 'Somany videos and really helpful', 'Poor'), " +
					"('google.com', 'luke@gmail.com', 'Can search anything you want', 'Fair'), " +
					"('wix.com', 'john@gmail.com', 'Very helpful in making sites', 'Good'), " +
					"('yahoo.com', 'tess@gmail.com', 'Very helpful search engine', 'Excellent'), " +
					"('gmail.com', 'tia@gmail.com', 'Can send emails from any part to the world and recieve emails too', 'Good'), " +
					"('facebook.com', 'logan@gmail.com', 'Upload photos and videos', 'Poor'), " +
					"('amazon.com', 'junwen@gmail.com', 'purchase anything you want and get delivered in 2 days', 'Fair'), " +
					"('instagram.com', 'evan@gmail.com', 'Upload status and stories', 'Fair'), " +
					"('samsung.com', 'evanlogan@gmail.com', 'purchase phones you want', 'Poor'), " +
					"('ebay.com', 'bob@gmail.com', 'Very cheap shopping but ships slow', 'Excellent');";
>>>>>>> branch 'master' of https://github.com/tia-gijo/Database_Project.git
			statement.executeUpdate(s2);
			System.out.println("10 reviews added.");
			
		} catch (Exception e) {
			System.out.println(e);
		} finally {
			close();
		}
	}
	
    public void insert(Video video, User user, String remark, String score) throws SQLException {
    	connect_func();         
    	String sql = "insert into  review (url, username, remark, score) values (?, ?, ?, ?)";
		preparedStatement = (PreparedStatement) connect.prepareStatement(sql);
		preparedStatement.setString(1, video.getUrl());
		preparedStatement.setString(2, user.getEmail());
		preparedStatement.setString(3, remark);
		preparedStatement.setString(4, score);
		preparedStatement.executeUpdate();
        preparedStatement.close();
        disconnect();
    }
	
	private void close() throws SQLException {
		if (resultSet != null)
			resultSet.close();
		if (statement != null)
			statement.close();
	}
}

//comment