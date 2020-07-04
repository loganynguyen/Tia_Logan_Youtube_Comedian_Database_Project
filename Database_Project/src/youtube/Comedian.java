package youtube;

public class Comedian {
    protected String firstname;
    protected String lastname;
    protected String birthdate;
    protected String birthplace;
    protected int comedianid;
 
    public Comedian() {
    }
    
    public Comedian(String firstname, String lastname, String birthdate, String birthplace) 
    {
        this.firstname = firstname;
        this.lastname = lastname;
        this.birthdate = birthdate;
        this.birthplace = birthplace;
    }
    
    public void setComedianid(int comedianid)
    {
    	this.comedianid = comedianid;
    }
    
    public int getComedianid()
    {
    	return comedianid;
    }
 
    public void setFirstname(String firstname)
    {
    	this.firstname = firstname;
    }
 
    public String getFirstname() {
        return firstname;
    }
 
    public void setLastname(String lastname) {
        this.lastname = lastname;
    }
    
    public String getLastname()
    {
    	return lastname;
    }
    
    public void setBirthdate(String birthdate) {
        this.birthdate = birthdate;
    }
    
    public String getBirthdate()
    {
    	return birthdate;
    }
    
    public void setBirthplace(String birthplace)
    {
    	this.birthplace = birthplace;
    }
    
    public String getBirthplace()
    {
    	return birthplace;
    }
}