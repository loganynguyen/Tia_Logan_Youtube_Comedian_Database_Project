package youtube;

import youtube.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;
import java.util.LinkedHashSet;

import java.util.Date;
import java.util.ArrayList;
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
import javax.servlet.http.HttpSession;

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
    private TagDAO tagDAO;
    private HttpSession session = null;
    
    // Initialize the table objects
    public void init() {
        System.out.println("Connected to the servlet...");
        userDAO = new UserDAO();
        reviewDAO = new ReviewDAO();
        videoDAO = new VideoDAO();
        comedianDAO = new ComedianDAO();
        favoriteDAO = new FavoriteDAO();
        tagDAO = new TagDAO();
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
            case "/comedianRegister":
                System.out.println("Registering...");
                comedianRegister(request, response);
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
            case "/logout":
            	logout(request, response);
            	break;
            case "/review":
            	review(request, response);
            	break;
            case "/favorite":
            	favorite(request, response);
            	break;
            case "/addFav":
            	addFav(request, response);
            	break;
        	case "/deleteFav":
            	deleteFav(request, response);
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
        tagDAO.dropTable();

        System.out.println("All tables dropped.");
       
        userDAO.createTable();
        reviewDAO.createTable();
        videoDAO.createTable();
        comedianDAO.createTable();
        favoriteDAO.createTable();
        tagDAO.createTable();

        System.out.println("Database successfully initalized!");
       
        RequestDispatcher dispatcher = request.getRequestDispatcher("root_successpage.jsp");
        dispatcher.forward(request, response);
    }
   
    // Function to login a user
    private void login(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException, ServletException {
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        Login login = new Login(email, password);
       
        RequestDispatcher dispatcher;
        if(userDAO.isUserValid(email, password) && !email.isEmpty()) {
        	session = request.getSession();
        	session.setAttribute("currentUsername", login.getEmail());
        	session.setAttribute("currentPassword", login.getPassword());
        	
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
    
    private void logout(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException, ServletException {
    	RequestDispatcher dispatcher;
    	session = request.getSession();
        session.invalidate();
        //response.sendRedirect("loginpage.jsp");
        dispatcher = request.getRequestDispatcher("loginpage.jsp");
        dispatcher.forward(request, response);
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
        boolean foundFullName = false;
        List<Video> listVideoTemp = new ArrayList<Video>();
        List<Video> listVideo = new ArrayList<Video>();

        // Grab the entire long string of search terms and split it up into individual smaller strings
        String field = request.getParameter("field");
        String[] tags = field.split(" ");
        
        // look for the possibility of a search term that was a full name
        if (tags.length > 1)
        {
            for (int i = 0; i < (tags.length - 1); i++)
            {
                List<Video> temp = new ArrayList<Video>();
                temp = videoDAO.listAllVideo(tags[i], tags[i+1]);
                
                if (temp.size() > 0) // if we found the full name of at least one comedian
            	{
            		// now add all these videos to the 'listVideo' ArrayList
                    for (int j = 0; j < temp.size(); j++)
                    {
                        //System.out.print("<<<1 " + temp.get(j).getTitle());
                        listVideoTemp.add(temp.get(j));
                    }
                    
                    foundFullName = true;
            	}
            }
        }
                       
        // if we didn't find a full name then just return results of all the search terms
        if (foundFullName == false)
        {
        	for (String a: tags)
            {
                List<Video> temp3 = new ArrayList<Video>();
                temp3 = videoDAO.listAllVideo(a);
                
                // now add all these videos to the 'listVideo' ArrayList
                for (int i = 0; i < temp3.size(); i++)
                {
                    //System.out.print("<<<2 " + temp3.get(i).getTitle());
                    listVideoTemp.add(temp3.get(i));
                }
            }
        }
        
        // make sure the final list does not have duplicate videos    
        listVideo = videoDAO.deleteDuplicates(listVideoTemp);
                
        // adding the full name of the comedian to the video object
        for(int i = 0; i < listVideo.size(); i++)
    	{
        	listVideo.get(i).setFullName(comedianDAO.getComedianSpecificToID(listVideo.get(i).getComedianid()));
    	}
        
        request.setAttribute("listVideo", listVideo);      
        dispatcher = request.getRequestDispatcher("searchlistpage.jsp");      
        dispatcher.forward(request, response);
        System.out.println("Printing the videos...");
    }
   
    private void videoSubmit(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException, ServletException
    {
    	session = request.getSession(false);
    	if(session != null || request.isRequestedSessionIdValid())
    	{
    		String currentUser = (String) session.getAttribute("currentUsername");
    		 		 
    		RequestDispatcher dispatcher;
            String url = request.getParameter("url");
            String firstName = request.getParameter("comedianf");
            String lastName = request.getParameter("comedianl");
            String title = request.getParameter("title");
            String description = request.getParameter("description");
            String tag = request.getParameter("tag");
            String[] tagList = tag.split(", ");
           
            DateFormat df = new SimpleDateFormat("dd/MM/yy");
            Date dateobj = new Date();
            String date = df.format(dateobj);
            System.out.println(date);
            
            if(comedianDAO.ifComedianExist(firstName, lastName))
            {
            	int id = comedianDAO.getComedianId(firstName, lastName);
            	Video newVideo = new Video(url, title, description, date, id, currentUser);
            	int numberOfvideos = videoDAO.getNumOfVideos(newVideo);
            	if(numberOfvideos < 5)
            	{
            		videoDAO.insert(newVideo);
                	for(int i = 0; i < tagList.length; i++){
                		Tag tagRow = new Tag(url, tagList[i]);
                		tagDAO.insert(tagRow);
                	}
                	response.setContentType("text/html");
            		PrintWriter out = response.getWriter();
            		out.print("<script>alert('Video uploaded!'); window.location='videoinsertpage.jsp' </script>");
                	System.out.println("Video submitted");
            	}
            	else
            	{
            		response.setContentType("text/html");
            		PrintWriter out = response.getWriter();
            		out.print("<script>alert('You have inserted 5 videos today. You cannot insert anymore!'); window.location='user_successpage.jsp' </script>");
            	}    	 
            }
            else
            {
            	// shows an alert box and when the user clicks ok, it will move on to the comedian register page
            	response.setContentType("text/html");
            	PrintWriter pw = response.getWriter();
            	pw.println("<script type=\"text/javascript\">");
            	pw.println("alert('Comedian does not exist! Register comedian to insert video');");
            	pw.println("</script>");
            	dispatcher=request.getRequestDispatcher("comedianregisterpage.jsp");
            	dispatcher.include(request, response);
            }
    	}
        
    	else
    	{
    		response.sendRedirect("loginpage.jsp");
    	}   
    }
    
    private void comedianRegister(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException, ServletException
    {
        RequestDispatcher dispatcher;
        String birthdate = request.getParameter("birthdate");
        String firstName = request.getParameter("comedianf");
        String lastName = request.getParameter("comedianl");
        String birthplace = request.getParameter("birthplace");
        Comedian newComedian = new Comedian(firstName, lastName, birthdate, birthplace);
        comedianDAO.insert(newComedian);

        response.setContentType("text/html");
    	PrintWriter pw = response.getWriter();
    	pw.println("<script type=\"text/javascript\">");
    	pw.println("alert('Comedian has been registered! Proceed to video insert page');");
    	pw.println("</script>");
    	dispatcher=request.getRequestDispatcher("videoinsertpage.jsp");
    	dispatcher.include(request, response);
    	System.out.println("Registration complete");   
    }
    
    private void watch(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException, ServletException
    {
        RequestDispatcher dispatcher;
        String url = request.getParameter("url");
        Video video = videoDAO.retrieveVideoByUrl(url);
        List<Video> listVideo = new ArrayList<Video>();
        listVideo.add(video);
        
        request.setAttribute("listVideo", listVideo);      
        dispatcher = request.getRequestDispatcher("watchvideopage.jsp");      
        dispatcher.forward(request, response);
        System.out.println("Going to the specific video...");
    }
    
    private void review(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException, ServletException
    {
    	session = request.getSession(false);
    	if(session != null || request.isRequestedSessionIdValid())
    	{
            RequestDispatcher dispatcher;
            
            String currentUser = (String) session.getAttribute("currentUsername");
	        String url = request.getParameter("url");
	        
	        if(reviewDAO.checkPostUser(url, currentUser) == false)
	        {
		        System.out.println("Review NOT posted.");
		        
		        response.setContentType("text/html");
	        	PrintWriter w = response.getWriter();
	        	w.println("<script type=\"text/javascript\">");
	        	w.println("alert('Review NOT posted, you cannot review your OWN videos!');");
	        	w.println("</script>");
	        	dispatcher=request.getRequestDispatcher("searchpage.jsp");
	        	dispatcher.include(request, response);
        	}
	        else
	        {
		        String remark = request.getParameter("remark");
		        char rating = request.getParameter("rating").charAt(0);
		        
		        reviewDAO.insert(url, currentUser, remark, rating);
		        System.out.println("Review posted");
		        
		        response.setContentType("text/html");
	        	PrintWriter pw = response.getWriter();
	        	pw.println("<script type=\"text/javascript\">");
	        	pw.println("alert('Review posted!');");
	        	pw.println("</script>");
	        	dispatcher=request.getRequestDispatcher("searchpage.jsp");
	        	dispatcher.include(request, response);
	        }
    	}
    	else
    	{
    		response.sendRedirect("loginpage.jsp");
    	}
    }
    
    private void favorite(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException, ServletException
    {
    	session = request.getSession(false);
    	if(session != null || request.isRequestedSessionIdValid())
    	{
    		RequestDispatcher dispatcher;
            
    		String currentUser = (String) session.getAttribute("currentUsername");
    		List<Favorite> favList = new ArrayList<Favorite>();
    		List<String> nameList = new ArrayList<String>();
            
    		favList = favoriteDAO.getFavObjects(currentUser);
    		
            for (int i = 0; i < favList.size(); i++)
            {
            	String s = comedianDAO.getComedianSpecificToID(favList.get(i).getComedianid());
            	nameList.add(s);
            }
                                    
            request.setAttribute("listFav", nameList);      
            dispatcher = request.getRequestDispatcher("user_favoritepage.jsp");      
            dispatcher.forward(request, response);
            System.out.println("printing favorite comedians...");
        }
    }
   
    private void deleteFav(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException, ServletException
    {
        session = request.getSession(false);
        if(session != null || request.isRequestedSessionIdValid())
        {          
             String currentUser = (String) session.getAttribute("currentUsername");
             String fullName = request.getParameter("comedian");
             String[] comedianName = fullName.split(" ");
             String comedianFirstName = comedianName[0];
             String comedianLastName = comedianName[1];
             int id = comedianDAO.getComedianId(comedianFirstName, comedianLastName);
             favoriteDAO.delete(id, currentUser);
             favorite(request, response);
        }      
    }
    
    private void addFav(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException, ServletException {
    	session = request.getSession(false);
        if(session != null || request.isRequestedSessionIdValid())
        {          
             String currentUser = (String) session.getAttribute("currentUsername");
             int id = Integer.parseInt(request.getParameter("id"));
             if(favoriteDAO.ifComedianExistsInFavList(id, currentUser) == false)
            	 favoriteDAO.add(id, currentUser);
             favorite(request, response);
        }
    }
}