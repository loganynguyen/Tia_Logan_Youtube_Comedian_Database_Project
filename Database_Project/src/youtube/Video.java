package youtube;

public class Video {
    protected String url;
    protected String title;
    protected String description;
    protected String date;
    protected String comedianId;
 
    public Video() {
    }

 

    public Video(String url, String title, String description, String date) 
    {
        this.url = url;
        this.title = title;
        this.description = description;
        this.date = date;
        //this.comedianId = comedianId;
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
    
    public void setComedianid(String comedianId)
    {
        this.comedianId = comedianId;
    }
    
    public String getComedianid()
    {
        return comedianId;
    }
}