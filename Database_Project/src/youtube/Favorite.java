package youtube;

public class Favorite {
    protected int favoriteId;
    protected String username;
    protected int comedianId;

    public Favorite() {
    }
    
    public Favorite(int favoriteId, String username, int comedianId) 
    {
        this.favoriteId = favoriteId;
        this.username = username;
        this.comedianId = comedianId;
    }
 
    public void setFavoriteid(int favoriteId)
    {
    	this.favoriteId = favoriteId;
    }
 
    public int getFavoriteid() {
        return favoriteId;
    }
 
    public void setUsername(String username) {
        this.username = username;
    }
    
    public String getUsername()
    {
    	return username;
    }
    
    public void setComedianid(int comedianId) {
        this.comedianId = comedianId;
    }
    
    public int getComedianid()
    {
    	return comedianId;
    }
}