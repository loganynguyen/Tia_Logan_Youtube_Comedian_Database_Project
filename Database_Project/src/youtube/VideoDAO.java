package youtube;

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

public class VideoDAO {
	private static final long serialVersionUID = 1L;
	private static Connection connect = null;
	private static Statement statement = null;
	private static PreparedStatement preparedStatement = null;
	private static ResultSet resultSet = null;
	
	public VideoDAO() {}
	
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
		statement = (Statement) connect.createStatement();
		statement.executeUpdate("SET FOREIGN_KEY_CHECKS = 0");
		statement.executeUpdate("DROP TABLE IF EXISTS video");
		statement.executeUpdate("SET FOREIGN_KEY_CHECKS = 1");
 	}
 	
 	public List<Video> listAllVideo(String searchTerm) throws SQLException {
        
 	 	connect_func();
        statement = (Statement) connect.createStatement();
        
    	List<Video> listVideo = new ArrayList<Video>(); // this will store all the videos that we find
	 	List<String> tags = new ArrayList<String>(); // this will store all the tags that we will search for videos
	 	
	 	// // FIRST: we will search by firstname then lastname // //
	 	
	 	// was the search term a firstname? collect all comedian IDs that are the same
	 	String sql1 = "SELECT comedianid FROM comedian where firstname='"+searchTerm+"'";      
        resultSet = statement.executeQuery(sql1);
        while (resultSet.next())
        {
            tags.add(resultSet.getString("comedianid"));
        }
        
        // was the search term a lastname? collect all comedian IDs that are the same
 	 	String sql2 = "SELECT comedianid FROM comedian where lastname='"+searchTerm+"'";      
        resultSet = statement.executeQuery(sql2);
        while (resultSet.next())
        {
            tags.add(resultSet.getString("comedianid"));
        }
                      
        // now go through all the videos of each comedianid that was returned
        String sql3;
        for (int i = 0; i < tags.size(); i++)
        {
            // select the next comedian
        	sql3 = "SELECT * FROM video where comedianid='"+tags.get(i)+"'";
            resultSet = statement.executeQuery(sql3);
            
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
        
        // // SECOND: now we will search by tags // //
        
        // was the search term a tag? collect all comedian IDs that are the same
//        String sql4 = "SELECT comedianid FROM comedian where tags='"+searchTerm+"'";      
//        resultSet = statement.executeQuery(sql4);
//        while (resultSet.next())
//        {
//            tags.add(resultSet.getString("comedianid"));
//        }
                            
        resultSet.close();
        statement.close();         
        disconnect();        
        return listVideo;
    }
 	
 	public List<Video> listAllVideo(String first, String last) throws SQLException {
 		connect_func();
        statement = (Statement) connect.createStatement();
        
    	List<Video> listVideo = new ArrayList<Video>(); // this will store all the videos that we find
	 	List<String> tags = new ArrayList<String>(); // this will store all the tags that we will search for videos
	 		
	 	// was the search term a firstname? collect all comedian IDs that are the same
	 	String sql1 = "SELECT comedianid FROM comedian where firstname='" + first + "' AND lastname='" + last + "'";      
        resultSet = statement.executeQuery(sql1);
        while (resultSet.next())
        {
            tags.add(resultSet.getString("comedianid"));
        }
                              
        // now let's grab all their videos
        String sql3;
        for (int i = 0; i < tags.size(); i++)
        {
            // select the next comedian
        	sql3 = "SELECT * FROM video where comedianid='"+tags.get(i)+"'";
            resultSet = statement.executeQuery(sql3);
            
            // add all their videos to the listVideo object
            while (resultSet.next())
            {
                String url = resultSet.getString("url");
                String t = resultSet.getString("title");
                String d = resultSet.getString("description");
                String date = resultSet.getString("date");
                System.out.println(t);
                Video v = new Video(url, t, d, date);
                listVideo.add(v);
        	}
        }
        
        resultSet.close();
        statement.close();         
        disconnect();  
 		return listVideo;
 	}
 	
 	public List<Video> deleteDuplicates(List<Video> v) throws SQLException {
 		
 		List<Video> v2 = new ArrayList<Video>();
 			
 		for(Video element : v)
		{
 			if(!v2.contains(element))
 				v2.add(element);
		}
 				
		return v2;
 	}
 	
 	public void insert(Video video) throws SQLException {
        connect_func();        
        String sql = "insert into  video (url, title, description, date, comedianid) values (?, ?, ?, ?, ?)";
        preparedStatement = (PreparedStatement) connect.prepareStatement(sql);
        preparedStatement.setString(1, video.getUrl());
        preparedStatement.setString(2, video.getTitle());
        preparedStatement.setString(3, video.getDescription());
        preparedStatement.setString(4, video.getDate());
        preparedStatement.setString(5, video.getComedianid());
        preparedStatement.executeUpdate();
        preparedStatement.close();
        disconnect();
    }
 	
	// Function that creates and seeds the table
	public void createTable() throws SQLException {
		try {
			connect_func();
			
			// create the user table
			String s = "CREATE TABLE video (" +
					"url VARCHAR(50) NOT NULL," +
					"title VARCHAR(100) NOT NULL," +
					"description VARCHAR(500) NOT NULL," +
					"date VARCHAR(20) NOT NULL," +
					"comedianId VARCHAR(2) NOT NULL," +
					"PRIMARY KEY(url) );";
			statement.executeUpdate(s);
			System.out.println("'Video' table created.");
			
			// seed the table with 10 users
			String s2 = "INSERT INTO video(url, title, description, date, comedianId) VALUES" +
					"('https://www.youtube.com/watch?v=lychTT79gKI', 'Jim Jefferies - The Rules Of Being On An Airplane', '#JimJefferies on plane etiquette, getting flack for using the C-word, and lying about being gay to win arguments.', '2017-1-20', '1'), " +
					"('https://www.youtube.com/watch?v=QdAhlnj97B0', 'Bo Burnham - Sad', '#BoBurnham wows the audience with his poetry and then performs a song about all the sadness in the world.', '2015-8-31', '2'), " +
					"('https://www.youtube.com/watch?v=_px_2mXKry0', 'Bo Burnham Stand-Up 11/30/10 - CONAN on TBS', 'Comedian Bo Burnham wows the crowd with jokes, poems and a song; the comedy triumvirate!', '2016-7-3', '2'), " +
					"('https://www.youtube.com/watch?v=kMiEGUWBn98', 'Bill Hicks standup comedy 1991 - HBO One Night Stand', 'Bill Hicks half hour standup comedy special, first aired April 27, 1991, is as insightful as it is controversial.', '2019-10-1', '3'), " +
					"('https://www.youtube.com/watch?v=EOfFRDryVQM', 'Bill Hicks - Relentless [1992] - Stand Up Comedy Show', 'This special is well regarded as the most knowledge spewing and entertainment filled of them all.', '2019-4-31', '3'), " +
					"('https://www.youtube.com/watch?v=twlb_LJsp4Q', 'Kevin Hart, funniest best jokes comedy', 'Kevin Hart is a beast!', '2016-6-16', '4'), " +
					"('https://www.youtube.com/watch?v=4Xo3Fq7GGWk', 'Sam Morril: I Got This - Full Special', 'Sam Morril wonders if murderers critique each others work and recalls befriending a vigilante in Cleveland.', '2020-2-10', '5'), " +
					"('https://www.youtube.com/watch?v=YLuZjpxmsZQ', 'George Carlin on some cultural issues.', 'Masterful performance of George Carlin taken from the show \"Back in Town\", 1996.', '2010-9-14', '6'), " +
					"('https://www.youtube.com/watch?v=uCJDLgQ6xFk', 'Bill Burr - Let It Go - 2010 - Stand-up Special', 'Comedy of Bill Burr', '2016-7-11', '7'), " +
					"('https://youtu.be/buSv1jjAels', 'C-SPAN: Joe Wong at RTCA Dinner', 'A debut of Joe Wong', '2010-3-18', '8'), " +
					"('https://www.youtube.com/watch?v=tDolNU89SXI', 'Mark Normand: Out To Lunch - Full Special', 'In his third comedy hour he covers it all: Drinking, anxiety, gays, naughty words, trans, race & the ladies.', '2020-5-12', '9'), " +
					"('https://www.youtube.com/watch?v=B7sgN1Hb2zY', 'Brian Regan Stand Up Comedy Full HD Best Comedian Ever', 'Brian Regan Stand Up Comedy Full HD Best Comedian Ever', '2017-4-4', '10'); ";

			statement.executeUpdate(s2);
			System.out.println("12 videos added.");
			
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