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
	 
	 public List<String> listPopularTags() throws SQLException
       {
	        
	        List<String> popularTagList = new ArrayList<String>(); 
	       	        
	        connect_func();
	         statement = (Statement) connect.createStatement();
	        statement.executeUpdate("DROP VIEW IF EXISTS tagnumber");
	        statement.executeUpdate("CREATE VIEW tagnumber(tag, num) AS (SELECT T.tag, COUNT(distinct postuser) AS num FROM video V, videotag T WHERE V.url = T.url GROUP BY T.tag)");
	        String sql = "SELECT * FROM tagnumber WHERE num = (SELECT COUNT(DISTINCT postuser) FROM video)";
	        resultSet = statement.executeQuery(sql);
	        while (resultSet.next())
            {
                String tag = resultSet.getString("tag");
                popularTagList.add(tag);
        	}
	        resultSet.close();
	        statement.close();         
	        disconnect();        
	        return popularTagList;
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
  
  public List<String> listAllUrlsspecifictotags(String tagList) throws SQLException {
      
      List<String> videoList = new ArrayList<String>();
      String[] tag = tagList.split(", ");
      for(int i = 0; i < tag.length; i++)
      {
          String sql = "SELECT url FROM videotag where url='"+ tag[i] +"'";
          connect_func();     
          statement =  (Statement) connect.createStatement();
          resultSet = statement.executeQuery(sql);
          while (resultSet.next())
          {
              String url = resultSet.getString("url");
              for(int j = 0; j < videoList.size(); j++)
              {
                  if(url == videoList.get(i))
                  {
                      return null;
                  }
                  else
                      videoList.add(url);
              }
             
          }
      }
      resultSet.close();
      statement.close();        
      disconnect();       
      return videoList;
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
				"('https://www.youtube.com/embed/lychTT79gKI', 'funny' ), " +
				"('https://www.youtube.com/embed/lychTT79gKI', 'helpful'), " +
				"('https://www.youtube.com/embed/_px_2mXKry0', 'funny'), " +
				"('https://www.youtube.com/embed/kMiEGUWBn98', 'helpful'), " +
				"('https://www.youtube.com/embed/EOfFRDryVQM', 'phones'), " +
				"('https://www.youtube.com/embed/twlb_LJsp4Q', 'phones'), " +
				"('https://www.youtube.com/embed/uCJDLgQ6xFk', 'phones'), " +
				"('https://www.youtube.com/embed/buSv1jjAels', 'relationships'), " +
				"('https://www.youtube.com/embed/buSv1jjAels', 'funny'), " +
				"('https://www.youtube.com/embed/tDolNU89SXI', 'relationships'), " +
				"('https://www.youtube.com/embed/B7sgN1Hb2zY', 'funny'), " +
				"('https://www.youtube.com/embed/B7sgN1Hb2zY', 'relationships');";

		statement.executeUpdate(s2);
		System.out.println("10 videotags added.");
		
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