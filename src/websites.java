import java.awt.List;
import java.util.ArrayList;


public class websites {
	
	public static ArrayList<website> list;
	
	
	public websites(){
		list = new ArrayList<website>();
		}
	
	
	
	public void add(website w){
		
		if(list.isEmpty()){
			list.add(w);
		}
		else{
			for(int i = 0;i<list.size();i++){
				if(w.getCount()>=list.get(i).getCount()){
					list.add(i, w);
				break;
				}
				else if(i == list.size()-1){
					list.add(i+1,w);
				break;
				}
			}
		}
		
	}
	
	
	

}
