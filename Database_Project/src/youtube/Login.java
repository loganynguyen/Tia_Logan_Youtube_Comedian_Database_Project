package youtube;


public class Login {
    protected String email;
    protected String password;
    
    public Login() {
    }
    
    public Login(String email, String password) 
    {
        this.email = email;
        this.password = password;
       
    }
  
 
    public void setEmail(String email)
    {
    	this.email = email;
    }
 
    public String getEmail() {
        return email;
    }

    public void setPassword(String password)
    {
    	this.password = password;
    }
    
    public String getPassword()
    {
    	return password;
    }
 
}


//comment