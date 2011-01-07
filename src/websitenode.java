import java.util.ArrayList;


public class websitenode 
{
	public website element;
	public websitenode parent;
	public ArrayList<websitenode> children;
	
	public websitenode(String url,int count)
	{
		element = new website(url,count);
		children = new ArrayList<websitenode>();
	}
	
	public void Add(String url,int count)
	{
		websitenode _new = new websitenode(url, count);
		//System.out.println(_new.element.toString());
		if(this.children.isEmpty())
		{
			this.children.add(_new);
		}
		else{
			for(int i = 0;i<this.children.size();i++)
			{
				if(_new.element.getCount()>=this.children.get(i).element.getCount())
				{
					this.children.add(i, _new);
					break;
				}
				else if(i == this.children.size()-1){
					this.children.add(i+1, _new);
					break;
				}
			}
		}
		
		_new.parent = this;
	}
	
	public String ToString(){
		return element.toString();
	}
	

}
