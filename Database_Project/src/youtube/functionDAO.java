package youtube;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class functionDAO {
	private static final long serialVersionUID = 1L;
	private Connection connect = null;
	private Statement statement = null;
	private PreparedStatement preparedStatement = null;
	private ResultSet resultSet = null;
	
	
	public functionDAO() {}
	
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
	
	 public List<String> listAllVideo(String comedianFirstName) throws SQLException {
	        List<String> listVideo = new ArrayList<String>();        
	        connect_func();     
	        String sql1 = "SELECT comedianid FROM comedian where firstname='"+comedianFirstName+"'";      
	        
	        statement =  (Statement) connect.createStatement();
	        ResultSet resultSet = statement.executeQuery(sql1);
	        String comedianid = resultSet.getString("comedianid");
	        
	        
	        String sql2 = "SELECT url FROM video where comedianId='"+comedianid+"'";      
	        resultSet = statement.executeQuery(sql2);
	        while (resultSet.next()) 
	        {
	            String url = resultSet.getString("url");
	            listVideo.add(url);
	        }        
	        resultSet.close();
	        statement.close();         
	        disconnect();        
	        return listVideo;
	    }
	    
	
	

}
