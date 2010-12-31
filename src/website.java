
public class website 
{
	private String website;
	private int count;
	website(String n, int c){
		website = n;
		count = c;
	}
	public String getName(){
		return website;
	}
	public int getCount(){
		return count;
	}
	public void setName(String n){
		website = n;
	}
	public void setCount(int c){
		count = c;
	}
	public String toString(){		
		return("["+website+","+count+"]");
	}

}
