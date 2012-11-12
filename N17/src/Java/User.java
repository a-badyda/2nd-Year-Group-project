package root;

public class User {

	public String username;
	public String password;
	public String key;
	public int cash;
	
	public User(String username, String password){
		
		this.username = username;
		this.password = password;
	}
	
	public void SetKey(String key){
		
		this.key = key;
	}
	
	public void SetCash(int cash){
		this.cash = cash;
	}
}
