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

public class ComedianDAO extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private static Connection connect = null;
    private static Statement statement = null;
    private static PreparedStatement preparedStatement = null;
    private static ResultSet resultSet = null;
    
    public ComedianDAO() {}
    
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
    
    public List<Comedian> listAllComedian() throws SQLException {
        List<Comedian> listComedians = new ArrayList<Comedian>();        
        String sql = "SELECT * FROM comedian";      
        connect_func();      
        statement =  (Statement) connect.createStatement();
        resultSet = statement.executeQuery(sql);
         
        while (resultSet.next()) {
           // String comedianid = resultSet.getString("comedianid");
            String firstname = resultSet.getString("firstname");
            String lastname = resultSet.getString("lastname");
            String birthday = resultSet.getString("birthdate");
            String birthplace = resultSet.getString("birthplace");
             
            Comedian newComedian = new Comedian(firstname, lastname, birthday, birthplace);
            listComedians.add(newComedian);
        }        
        resultSet.close();
        statement.close();         
        disconnect();        
        return listComedians;
    }
    
    public List<String> listCoolComedians() throws SQLException {
        List<String> listComedians = new ArrayList<String>();        
        String sql = "SELECT * FROM comedian WHERE comedianid IN (\r\n" + 
        		"SELECT DISTINCT V.comedianId FROM review R, video V WHERE R.url = V.url AND R.url NOT IN\r\n" + 
        		"(SELECT url FROM review WHERE score = '" + 'G' + "' OR score = '" + 'P' + "' OR score ='" + 'F' + "'))";      
        connect_func();      
        statement =  (Statement) connect.createStatement();
        resultSet = statement.executeQuery(sql);
         
        while (resultSet.next()) {
           // String comedianid = resultSet.getString("comedianid");
            String firstname = resultSet.getString("firstname");
            String lastname = resultSet.getString("lastname");
            String birthday = resultSet.getString("birthdate");
            String birthplace = resultSet.getString("birthplace");
            String comedianFullName = firstname + " " + lastname;
            Comedian newComedian = new Comedian(firstname, lastname, birthday, birthplace);
            listComedians.add(comedianFullName);
        }        
        resultSet.close();
        statement.close();         
        disconnect();        
        return listComedians;
    }
    
    public int getComedianId(String firstname, String lastname) throws SQLException 
    {
    	String s = "Select comedianid from comedian where firstname='" + firstname + "' and lastname='" + lastname + "'";
    	connect_func();
    	int id = 0;
    	statement =  (Statement) connect.createStatement();
    	resultSet = statement.executeQuery(s);
        while (resultSet.next()) 
        {
        	id = resultSet.getInt("comedianid");
        }
        return id;
    }
    
    public void insert(Comedian comedian) throws SQLException {
        connect_func();
        System.out.println(comedian.getFirstname());
        System.out.println(comedian.getLastname());
        System.out.println(comedian.getBirthdate());
        System.out.println(comedian.getBirthplace());
        
        String sql = "insert into comedian (firstname, lastname, birthdate, birthplace) values (?, ?, ?, ?)";
        preparedStatement = (PreparedStatement) connect.prepareStatement(sql);
        preparedStatement.setString(1, comedian.getFirstname());
        preparedStatement.setString(2, comedian.getLastname());
        preparedStatement.setString(3, comedian.getBirthdate());
        preparedStatement.setString(4, comedian.getBirthplace());
        preparedStatement.executeUpdate();
        preparedStatement.close();
        disconnect();
    }
    
    public String getComedianSpecificToID(int id) throws SQLException {

    	String sql = "SELECT * FROM comedian where comedianid='" + id + "'";   
        connect_func();     
        statement =  (Statement) connect.createStatement();
        resultSet = statement.executeQuery(sql);
        resultSet.next();
        
        String firstname = resultSet.getString("firstname");
        String lastname = resultSet.getString("lastname");
        String comedian = firstname + " " + lastname;
        
        resultSet.close();
        statement.close();        
        disconnect();       
        return comedian;
    }
    
	public List<Comedian> getCommonFavs(String user1, String user2) throws SQLException {
		List<Comedian> list = new ArrayList<Comedian>();
		connect_func();
        statement = (Statement) connect.createStatement();
        String s = "SELECT * FROM comedian WHERE comedianid IN " +
        		   "(SELECT comedianId FROM favorite WHERE username = '" + user1 + "' AND comedianid IN " +
        		   "(SELECT comedianId FROM favorite WHERE username = '" + user2 + "'));";
        
        resultSet = statement.executeQuery(s);
		System.out.println("in");

        while (resultSet.next())
        {
        	String firstname = resultSet.getString("firstname");
        	String lastname = resultSet.getString("lastname");
        	String birthdate = resultSet.getString("birthdate");
        	String birthplace = resultSet.getString("birthplace");
    		System.out.println(firstname + ", " + lastname + ", " + birthdate + ", " + birthplace);
            list.add(new Comedian(firstname, lastname, birthdate, birthplace));
        }
        
        resultSet.close();
        statement.close();        
        disconnect();
		return list;
	}
	
	public List<String> getHotComedians() throws SQLException {
    	
    	List<Integer> comedianId = new ArrayList<Integer>();

    	String sql1 = "CREATE VIEW ReviewNumber(comedianId, num) AS SELECT V.comedianId, COUNT(*) AS num​ FROM review R, video V WHERE R.url = V.url GROUP BY V.comedianId";
    	String sql2 = "SELECT * FROM ReviewNumber ORDER BY num desc;";		
    	
        connect_func();     
        List<String> listComedians = new ArrayList<String>();   
        statement =  (Statement) connect.createStatement();
        statement.executeUpdate("DROP VIEW IF EXISTS ReviewNumber");
        statement.executeUpdate(sql1);
        resultSet = statement.executeQuery(sql2);
        
        while (resultSet.next()) 
        {
        	Integer id = resultSet.getInt("comedianId");
            comedianId.add(id);
        }
        
        for(int i = 0; i < comedianId.size(); i++)
        {
        	String sql = "SELECT * FROM comedian where comedianid='" + comedianId.get(i) + "'";
        	statement =  (Statement) connect.createStatement();
        	resultSet = statement.executeQuery(sql);
            while (resultSet.next()) 
            {
            	String firstname = resultSet.getString("firstname");
                String lastname = resultSet.getString("lastname");
                String comedian = firstname + " " + lastname;
                listComedians.add(comedian);
            }
        }
             
        resultSet.close();
        statement.close();        
        disconnect();       
        return listComedians;
    }
    
    public List<String> getTopComedians() throws SQLException 
    {
    	String sql1 = "CREATE VIEW videonumber(comedianId, num) AS (SELECT V.comedianId, COUNT(*) AS num FROM comedian C2, video V WHERE C2.comedianid = V.comedianId GROUP BY V.comedianId)";
    	String sql2 = "SELECT DISTINCT C.firstname, C.lastname, C.comedianid FROM comedian C WHERE C.comedianid IN (SELECT comedianId FROM videonumber WHERE num = (SELECT MAX(num) FROM videonumber))";

        connect_func();     
        List<String> listComedians = new ArrayList<String>();   
        statement =  (Statement) connect.createStatement();
        statement.executeUpdate("DROP VIEW IF EXISTS videonumber");
        statement.executeUpdate(sql1);
        resultSet = statement.executeQuery(sql2);
        while (resultSet.next()) 
        {
        	String firstname = resultSet.getString("firstname");
            String lastname = resultSet.getString("lastname");
            String comedian = firstname + " " + lastname;
            listComedians.add(comedian);
        }
         
        
        resultSet.close();
        statement.close();        
        disconnect();       
        return listComedians;
    }
        
    public boolean ifComedianExist(String firstname, String lastname) throws SQLException 
    {
		connect_func();
		boolean status = false;
		
		statement = (Statement) connect.createStatement();
		String s = "Select * from comedian where firstname='" + firstname + "' and lastname='" + lastname + "'";
		resultSet = statement.executeQuery(s);
		
	    if(resultSet.next())
	    	status = true;
	    return status;
	}
    
    
    // Function that drops the table
     public void dropTable() throws SQLException {
        connect_func();
        statement = (Statement) connect.createStatement();
        statement.executeUpdate("SET FOREIGN_KEY_CHECKS = 0");
        statement.executeUpdate("DROP TABLE IF EXISTS comedian");
        statement.executeUpdate("SET FOREIGN_KEY_CHECKS = 1");
     }
     
    // Function that creates and seeds the table
     public void createTable() throws SQLException {
        try {
            connect_func();
            String s = "CREATE TABLE comedian (" +
                    "comedianid INTEGER NOT NULL AUTO_INCREMENT," +
                    "firstname VARCHAR(20) NOT NULL," +
                    "lastname VARCHAR(20) NOT NULL," +
                    "birthdate VARCHAR(20) NOT NULL," +
                    "birthplace VARCHAR(20) NOT NULL," +
                    "PRIMARY KEY(comedianid) )";
            
            String s2 = "INSERT INTO comedian(firstname, lastname, birthdate, birthplace) VALUES" +
                    "('Jim', 'Jefferies', '8-8-1965', 'Memphis'), " +
                    "('Bo', 'Burnham', '1-30-1975', 'Detroit'), " +
                    "('Bill', 'Hicks', '7-23-1945', 'San Francisco'), " +
                    "('Kevin', 'Hart', '1-4-1949', 'LA'), " +
                    "('Sam', 'Morril', '3-3-2007', 'Denver'), " +
                    "('George', 'Carlin', '6-15-1989', 'DC'), " +
                    "('Bill', 'Burr', '9-10-1991', 'Baltimore'), " +
                    "('Joe', 'Wong', '7-5-1932', 'Chicago'), " +
                    "('Mark', 'Normand', '2-21-2001', 'Detroit'), " +
                    "('Brian', 'Regan', '2-11-2011', 'Warren');";
            statement.executeUpdate(s);
            System.out.println("'Comedian' table created.");
            statement.executeUpdate(s2);
            System.out.println("10 comedians added.");
            
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