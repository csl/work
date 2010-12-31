import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Scanner;


public class SearchFromIpeen {
	
	protected static ArrayList<String> list;
	
	//public SearchFromIpeen(){
		//list = new ArrayList<website>();
	//}

	public ArrayList<String> search(String word) throws IOException{
		
		
		list = new ArrayList<String>();
		try{
		URL _url = new URL("http://www.ipeen.com.tw/search/store_search.php?kw=" + word);
		URLConnection uc = _url.openConnection();
		uc.setRequestProperty("User-agent", "Chrome/7.0.517.44");
		BufferedReader in = new BufferedReader(new InputStreamReader(uc.getInputStream(),"BIG5"));
		Scanner scanner = new Scanner(in);
		scanner.useDelimiter("like href=");
		String website;
		int start = -1;
		int end = -1;
		boolean flag = true;
		
		while(scanner.hasNext()){
			String name = scanner.next();
			for(int i = 0;i<name.length();i++){
				if(name.charAt(i)=='"'){
					if(flag){
						start = i;
						flag = false;
					}
					else{
						end = i;
						break;
					}
				}
			}
			flag = true;
			if((start>-1)&&(end>-1)&&(start<end)){
				String s = name.substring(start+1, end);
				if(s.contains("http://")){
					list.add(s);
				}
				start = -1;
				end = -1;
				
			}
	
		}
		
	}
	
	
	catch(Exception e)
	{
		e.printStackTrace();
	}
		return list;
	
	
	
}
}
