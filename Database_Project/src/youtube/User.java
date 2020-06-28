package youtube;

public class User {
    protected String email;
    protected String password;
    protected String firstname;
    protected String lastname;
    protected int age;
 
    public User() {
    }
    
    public User(String email, String password, String firstname, String lastname, int age) 
    {
        this.email = email;
        this.password = password;
        this.firstname = firstname;
        this.lastname = lastname;
        this.age = age;
    }
 
    public void setEmail(String email)
    {
    	this.email = email;
    }
 
    public String getEmail() {
        return email;
    }
 
    public void setFirstName(String firstname) {
        this.firstname = firstname;
    }
    
    public String getFirst()
    {
    	return firstname;
    }
    
    public void setlastName(String lastname) {
        this.lastname = lastname;
    }
    
    public String getlast()
    {
    	return lastname;
    }
    
    public void setPassword(String password)
    {
    	this.password = password;
    }
    
    public String getPassword()
    {
    	return password;
    }
    
    public void setAge(int age) {
        this.age = age;
    }
    
    public int getAge()
    {
    	return age;
    }
}