package youtube;

import youtube.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;

import java.util.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

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
            case "/search":
                System.out.println("Searching...");
                search(request, response);
                break;
            case "/videoSubmit":
                System.out.println("Submitting...");
                videoSubmit(request, response);
                break;
            case "/initialize":
                initializeDatabase(request, response);
                break;
            case "/watch":
            	watch(request, response);
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
       
        RequestDispatcher dispatcher = request.getRequestDispatcher("root_successpage.jsp");
        dispatcher.forward(request, response);
    }
   
    // Function to login a user
    private void login(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException, ServletException {
        String email = request.getParameter("email");
        String password = request.getParameter("password");
       
        RequestDispatcher dispatcher;
        if(userDAO.isUserValid(email, password) && !email.isEmpty()) {
            if(email.contentEquals("root")) {
                dispatcher = request.getRequestDispatcher("initializepage.jsp");
                dispatcher.forward(request, response);
                System.out.println("Welcome root user!");
            } else {
            	dispatcher = request.getRequestDispatcher("user_successpage.jsp");
                dispatcher.forward(request, response);
                System.out.println("Welcome user!");
            }
        } else {
            dispatcher = request.getRequestDispatcher("loginpage.jsp");
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
                dispatcher = request.getRequestDispatcher("loginpage.jsp");
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
   
    private void search(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException, ServletException
    {
        RequestDispatcher dispatcher;
        String firstName = request.getParameter("field");
        List<Video> listVideo = videoDAO.listAllVideo(firstName);
        request.setAttribute("listVideo", listVideo);      
        dispatcher = request.getRequestDispatcher("searchlistpage.jsp");      
        dispatcher.forward(request, response);
        System.out.println("Printing the videos");
        
//        // if you want to separate in the begining
//        RequestDispatcher dispatcher;
//        String input = request.getParameter("field");
//        String[] name = input.split(" ");
//        String firstName = name[0];
//        String lastName = name[1];
//        if(name.length == 1)
//        {
//        	List<Video> listVideo = videoDAO.listAllVideo(firstName);
////          request.setAttribute("listVideo", listVideo);      
//            dispatcher = request.getRequestDispatcher("searchlistpage.jsp");      
//            dispatcher.forward(request, response);
//            System.out.println("Printing the videos");
//        }
//        else if(name.length == 2)
//        {
//        	List<Video> listVideo = videoDAO.listAllVideo(firstName, lastName);
////          request.setAttribute("listVideo", listVideo);      
//            dispatcher = request.getRequestDispatcher("searchlistpage.jsp");      
//            dispatcher.forward(request, response);
//            System.out.println("Printing the videos");
//        }
    }
   
    private void videoSubmit(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException, ServletException
    {
        RequestDispatcher dispatcher;
        String url = request.getParameter("url");
        String title = request.getParameter("title");
        String description = request.getParameter("description");
        String tag = request.getParameter("tag");
       
        DateFormat df = new SimpleDateFormat("dd/MM/yy");
        Date dateobj = new Date();
        String date = df.format(dateobj);
        //System.out.println(df.format(dateobj));
       
        Video newVideo = new Video(url, title, description, date);
        videoDAO.insert(newVideo);
        dispatcher = request.getRequestDispatcher("videoinsertpage.jsp");
        dispatcher.forward(request, response);
        System.out.println("Video submitted");    
    }
    
    private void watch(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException, ServletException
    {
        RequestDispatcher dispatcher;
        String title = request.getParameter("title");
        System.out.println(title);

        request.setAttribute("title", title);      
        dispatcher = request.getRequestDispatcher("watchvideopage.jsp");      
        dispatcher.forward(request, response);
        System.out.println("Going to the specific video...");
    }
}