package youtube;

public class Favorite {
    protected String favoriteId;
    protected String username;
    protected String comedianId;

    public Favorite() {
    }
    
    public Favorite(String favoriteId, String username, String comedianId) 
    {
        this.favoriteId = favoriteId;
        this.username = username;
        this.comedianId = comedianId;
    }
 
    public void setFavoriteid(String favoriteId)
    {
    	this.favoriteId = favoriteId;
    }
 
    public String getFavoriteid() {
        return favoriteId;
    }
 
    public void setUsername(String username) {
        this.username = username;
    }
    
    public String getUsername()
    {
    	return username;
    }
    
    public void setComedianid(String comedianId) {
        this.comedianId = comedianId;
    }
    
    public String getComedianid()
    {
    	return comedianId;
    }
}