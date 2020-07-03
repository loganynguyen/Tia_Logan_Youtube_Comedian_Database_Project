package youtube;

public class Tag  {
    protected String url;
    protected String tags;
 

    public Tag() {
    }
    
    public Tag(String url, String tags) 
    {
        this.url = url;
        this.tags = tags;
       
    }
 
    public void setUrl(String url)
    {
    	this.url = url;
    }
 
    public String getUrl() {
        return url;
    }
 
    public void setTags(String tags) {
        this.tags = tags;
    }
    
    public String getTags()
    {
    	return tags;
    }
    
    
}