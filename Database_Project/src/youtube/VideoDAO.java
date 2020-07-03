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
 	
 	// Function that returns a specific video baesd on a url
 	public Video retrieveVideoByUrl(String url) throws SQLException {
 		
 		connect_func();
 		
 		String sql = "SELECT * FROM video where url='" + url + "'";
        statement = (Statement) connect.createStatement();
        resultSet = statement.executeQuery(sql);
        resultSet.next();

        String u = resultSet.getString("url");
        String t = resultSet.getString("title");
        String de = resultSet.getString("description");
        String da = resultSet.getString("date");

    	Video v = new Video(u, t, de, da);
    	
        resultSet.close();
        statement.close();         
        disconnect();
 		return v;
 	} 
 	
 	// Function to list all videos by any tag of a comedian
 	public List<Video> listAllVideo(String searchTerm) throws SQLException {
        
 	 	connect_func();
        statement = (Statement) connect.createStatement();
        
    	List<Video> listVideo = new ArrayList<Video>(); // this will store all the videos that we find
	 	List<String> tags = new ArrayList<String>(); // this will store all the tags that we will search for videos
	 	
	 	// // FIRST: search by comedian name // //
	 	
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
        
        // // SECOND: search by tag // //
        
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
 	
 	// Function to list all videos by full name of the comedian
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

                Video v = new Video(url, t, d, date);
                listVideo.add(v);
        	}
        }
        
        resultSet.close();
        statement.close();         
        disconnect();  
 		return listVideo;
 	}
 	
<<<<<<< HEAD
// 	public List<Video> listAllVideo(String comedianFirstName, String comedianLastName) throws SQLException 
// 	{
//        
// 	 	connect_func();
//        statement = (Statement) connect.createStatement();
//        
//    	List<Video> listVideo = new ArrayList<Video>();
//	 	List<String> tags = new ArrayList<String>();
//	 	
//	 	// collect all the different names
//	 	String sql1 = "SELECT comedianid FROM comedian where firstname='"+comedianFirstName+"'";      
//        resultSet = statement.executeQuery(sql1);
//        while (resultSet.next())
//        {
//            tags.add(resultSet.getString("comedianid"));
//        }
//        
//        // go through all the videos of each comedian
//        String sql2;
//        for (int i = 0; i < tags.size(); i++)
//        {
//            // select the next comedian
//        	sql2 = "SELECT * FROM video where comedianid='"+tags.get(i)+"'";
//            resultSet = statement.executeQuery(sql2);
//            
//            // add all their videos to the listVideo object
//            while (resultSet.next())
//            {
//                String url = resultSet.getString("url");
//                String t = resultSet.getString("title");
//                String d = resultSet.getString("description");
//                String date = resultSet.getString("date");
//
//                Video v = new Video(url, t, d, date);
//                listVideo.add(v);
//        	}
//        }
//                    
//        resultSet.close();
//        statement.close();         
//        disconnect();        
//        return listVideo;
//    }
// 	
 	public int getNoOfVideos(Video video)throws SQLException
 	{
        connect_func();        
        String postUser = video.getPostUser();
        String postDate = video.getDate();
        String sql = "SELECT COUNT(date) AS total FROM video WHERE date='" + postDate + "' AND postuser='" + postUser + "'";
        resultSet = statement.executeQuery(sql);
        resultSet.next();
        int number = resultSet.getInt("total");
        return number;
 	}
=======
 	// Function to delete any duplicate videos in a list
 	public List<Video> deleteDuplicates(List<Video> v) throws SQLException {
 		
        List<Video> v2 = new ArrayList<Video>();
        int numDuplicates;
 		
 		for(int m = 0; m < v.size(); m++) {
 			numDuplicates = 0;			
 			for(int n = 0; n < v2.size(); n++) {
				if(v.get(m).getTitle().contentEquals(v2.get(n).getTitle()))
					numDuplicates++;
 			}
 			
 			if(numDuplicates == 0)
 				v2.add(v.get(m));
 		}
 		
		return v2;
 	}
 	
 	// Function to insert a new video into the database
>>>>>>> branch 'master' of https://github.com/tia-gijo/Database_Project.git
 	public void insert(Video video) throws SQLException {
        connect_func();        
        
        String sql = "insert into  video (url, title, description, date, comedianid, postuser) values (?, ?, ?, ?, ?, ?)";
        preparedStatement = (PreparedStatement) connect.prepareStatement(sql);
        preparedStatement.setString(1, video.getUrl());
        preparedStatement.setString(2, video.getTitle());
        preparedStatement.setString(3, video.getDescription());
        preparedStatement.setString(4, video.getDate());
        preparedStatement.setInt(5, video.getComedianid());
        preparedStatement.setString(5, video.getPostUser());
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
					"comedianId INTEGER NOT NULL," +
					"postuser VARCHAR(50) NOT NULL" +
					"PRIMARY KEY(url) );";
			statement.executeUpdate(s);
			System.out.println("'Video' table created.");
			
			// seed the table with 10 users
<<<<<<< HEAD
			String s2 = "INSERT INTO video(url, title, description, date, comedianId, postuser, 'mary@gmail.com') VALUES" +
					"('youtube.com', 'Christmas Special', 'This holiday season, Bob Ricks takes it to a new level of funny.', '2014-12-25', '1', 'luke@gmail.com'), " +
					"('google.com', 'Ricks, LIVE! at the Toyota Arena', 'Legendary comedian, Bob Ricks, takes the stage at the Toyta Arena.', '2015-1-29', '1', 'evanlogan@gmail.com'), " +
					"('wix.com', 'The Best of Bob Terry', 'A collection of his best jokes.', '2016-2-18', '2', 'tia@gmail.com'), " +
					"('yahoo.com', 'general', 'A very helpful search engine', '2017-3-19', '4', 'junwen@gmail.com'), " +
					"('gmail.com', 'mailing services', 'Can send emails from any part to the world and recieve emails too', '2017-4-20', '5', 'evanlogan@gmail.com'), " +
					"('facebook.com', 'social media', 'Upload photos and videos', '2018-4-24', '6', 'luke@gmail.com'), " +
					"('amazon.com', 'shopping', 'Purchase anything you want and get it delivered in 2 days', '2011-3-2', '7', 'logan@gmail.com'), " +
					"('instagram.com', 'social media', 'Upload status and stories', '2019-6-13', '8', 'tia@gmail.com'), " +
					"('samsung.com', 'shopping', 'Purchase phones you want', '2011-3-2', '9', 'junwen@gmail.com'), " +
					"('ebay.com', 'shopping', 'Very cheap shopping but ships slow', '2020-1-1', '10', 'luke@gmail.com');";
=======
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

>>>>>>> branch 'master' of https://github.com/tia-gijo/Database_Project.git
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