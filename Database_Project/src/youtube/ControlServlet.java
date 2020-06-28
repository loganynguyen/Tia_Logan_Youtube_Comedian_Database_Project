package youtube;

import youtube.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.DriverManager;
import java.sql.SQLException;
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

public class ControlServlet extends HttpServlet 
{
    // Declaring all private variables
	private static final long serialVersionUID = 1L;
    private UserDAO userDAO;
    private ReviewDAO reviewDAO;
    private VideoDAO videoDAO;
    private ComedianDAO comedianDAO;
    private FavoriteDAO favoriteDAO;
     
    // Initialize the table objects
    public void init() {
		System.out.println("Connected to the servlet...");
    	userDAO = new UserDAO();
    	reviewDAO = new ReviewDAO();
    	videoDAO = new VideoDAO();
    	comedianDAO = new ComedianDAO();
    	favoriteDAO = new FavoriteDAO();
    }
    
    // doPost -> doGet
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }
    
    // doGet, handling all the different request types
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String action = request.getServletPath();
        try 
        {
        	RequestDispatcher dispatcher;
            switch (action) 
            {
            case "/login":
        		System.out.println("Logging in...");
            	login(request, response);
                break;
            case "/register":
        		System.out.println("Registering...");
            	register(request, response);
            	break;
            case "/initialize":
            	initializeDatabase(request, response);
                break;
            }
        } catch (SQLException ex) { throw new ServletException(ex); }
    }
    
    // Function to initialize the database for the root user
    private void initializeDatabase(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException, ServletException {    	
    	userDAO.dropTable();
    	reviewDAO.dropTable();
    	videoDAO.dropTable();
    	comedianDAO.dropTable();
    	favoriteDAO.dropTable();
    	System.out.println("All tables dropped.");
    	
    	userDAO.createTable();
    	reviewDAO.createTable();
    	videoDAO.createTable();
    	comedianDAO.createTable();
    	favoriteDAO.createTable();
    	System.out.println("Database successfully initalized!");
    	
    	RequestDispatcher dispatcher = request.getRequestDispatcher("success.jsp");
		dispatcher.forward(request, response);
    }
    
    // Function to login a user
	private void login(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException, ServletException {
		String email = request.getParameter("email");
        String password = request.getParameter("password");
        
		RequestDispatcher dispatcher;
        if(userDAO.isUserValid(email, password) && !email.isEmpty()) {
    		if(email.contentEquals("root")) {
    			dispatcher = request.getRequestDispatcher("initialize.jsp");
    			dispatcher.forward(request, response);
    	    	System.out.println("Welcome root user!");
    		} else {
    			dispatcher = request.getRequestDispatcher("mainpage.jsp");
				dispatcher.forward(request, response);
    	    	System.out.println("Welcome user!");
    		}
    	} else {
    		dispatcher = request.getRequestDispatcher("index.jsp");
			dispatcher.forward(request, response);
	    	System.out.println("Please enter correct credentials!");
    	}
	}
	
    // Function to register a new user 
	private void register(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException, ServletException {
		String email = request.getParameter("email");
        String password = request.getParameter("password");
        String password2 = request.getParameter("password2");
        String firstname = request.getParameter("firstname");
        String lastname = request.getParameter("lastname");
        int age = Integer.parseInt(request.getParameter("age"));
    	
		RequestDispatcher dispatcher;
        if(password.contentEquals(password2)) {
	        if(!userDAO.isDuplicateEmail(email)) {
	            User newUser = new User(email, password, firstname, lastname, age);
	            userDAO.insert(newUser);
	            dispatcher = request.getRequestDispatcher("index.jsp");
	    		dispatcher.forward(request, response);
	        	System.out.println("Registration complete");
	    	} else {
	    		request.setAttribute("registerError", "This email is already taken");
	    		dispatcher = request.getRequestDispatcher("registerpage.jsp");
	    		dispatcher.forward(request, response);
	        	System.out.println("Registration failed, please enter a unique email.");
	    	}
        } else {
        	request.setAttribute("registerError", "Passwords do not match");
    		dispatcher = request.getRequestDispatcher("registerpage.jsp");
    		dispatcher.forward(request, response);
        	System.out.println("Registration failed, please make sure your passwords match.");
        }
	}
}