package youtube;

public class Video {
    protected String url;
    protected String title;
    protected String description;
    protected String date;
    protected int comedianId;
    protected String postUser;
    protected String fullName;
 
    public Video() {
    }

    public Video(String url, String title, String description, String date, int comedianId, String postUser) 
    {
        this.url = url;
        this.title = title;
        this.description = description;
        this.date = date;
        this.comedianId = comedianId;
        this.postUser = postUser;
    }
 
    public void setUrl(String url)
    {
        this.url = url;
    }
 
    public String getUrl() {
        return url;
    }
 
    public void setTitle(String title) {
        this.title = title;
    }
    
    public String getTitle()
    {
        return title;
    }
    
    public void setDescription(String description) {
        this.description = description;
    }
    
    public String getDescription()
    {
        return description;
    }
    
    public void setDate(String date)
    {
        this.date = date;
    }
    
    public String getDate()
    {
        return date;
    }
    
    public void setComedianid(int comedianId)
    {
        this.comedianId = comedianId;
    }
    
    public int getComedianid()
    {
        return comedianId;
    }
    
    public void setPostUser(String postUser)
    {
    	this.postUser = postUser;
    }
    public String getPostUser()
    {
    	return postUser;
    }
    
    public void setFullName(String fullName)
    {
    	this.fullName = fullName;
    }
    public String getFullName()
    {
    	return fullName;
    }
}