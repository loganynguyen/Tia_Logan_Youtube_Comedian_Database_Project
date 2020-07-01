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
            String birthday = resultSet.getString("birthday");
            String birthplace = resultSet.getString("birthplace");
             
            Comedian newComedian = new Comedian(firstname, lastname, birthday, birthplace);
            listComedians.add(newComedian);
        }        
        resultSet.close();
        statement.close();         
        disconnect();        
        return listComedians;
    }
    
//    public void insert(Comedian comedian) throws SQLException {
//        connect_func();         
//        String sql = "insert into  comedian (firstname, lastname, birthdate, birthplace) values (?, ?, ?, ?)";
//        preparedStatement = (PreparedStatement) connect.prepareStatement(sql);
////        preparedStatement.setString(1, comedian.getComedianid());
//        preparedStatement.setString(1, comedian.getFirstname());
//        preparedStatement.setString(2, comedian.getLastname());
//        preparedStatement.setString(3, comedian.getBirthdate());
//        preparedStatement.setString(4, comedian.getBirthplace());
//        preparedStatement.executeUpdate();
//        preparedStatement.close();
//        disconnect();
//    }
//    
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
                    "birthplace VARCHAR(20) NOT NULL,"+
                    "PRIMARY KEY(comedianid) )";
            
            
            String s2 = "INSERT INTO comedian(firstname, lastname, birthdate, birthplace) VALUES" +
                    "('Bob', 'Ricks', '8-8-1965', 'Memphis'), " +
                    "('Terry', 'Rickson', '1-30-1975', 'Detroit'), " +
                    "('Miles', 'Murray', '7-23-1945', 'San Francisco'), " +
                    "('Sam', 'Antha', '1-4-1949', 'LA'), " +
                    "('Kelly', 'Kandle', '3-3-2007', 'Denver'), " +
                    "('Jordan', 'Jet', '6-15-1989', 'DC'), " +
                    "('Li', 'Win', '9-10-1991', 'Baltimore'), " +
                    "('Kevin', 'Kinard', '7-5-1932', 'Chicago'), " +
                    "('Max', 'Muda', '2-21-2001', 'Detroit'), " +
                    "('Maxine', 'Heller', '2-11-2011', 'Warren');";
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