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
	private int page;
	
	//public SearchFromIpeen(){
		//list = new ArrayList<website>();
	//}
	
	public ArrayList<String> search(String word) throws IOException
	{
		//init variable
		boolean rep = true;
		list = new ArrayList<String>();
		page = 2;
		
		//Query Urls all search
		String surl = "http://www.ipeen.com.tw/search/store_search.php?kw=" + word;
		String url = new String(surl.getBytes("big5"), "UTF-8"); 
		System.out.println(url);
		while (true)
		{
			rep = urlsearch(word, url);
			if (rep == false) break;
			url = "http://www.ipeen.com.tw/search/store_search.php?p=" + page + "&kw=" + word;
			page++;
		}
		
		return list;
	}

	public boolean urlsearch(String word, String url) throws IOException
	{
		int start = -1;
		int end = -1;
		boolean flag = true;
		
		try{
			URL _url = new URL(url);
			URLConnection uc = _url.openConnection();
			uc.setRequestProperty("User-agent", "Chrome/7.0.517.44");
			
			BufferedReader in = new BufferedReader(new InputStreamReader(uc.getInputStream(), "utf-8"));
	
			Scanner scanner = new Scanner(in);
			scanner.useDelimiter("like href=");
			String website;
			
			System.out.println("Searching...");
			
			while(scanner.hasNext())
			{
				String name = scanner.next();
				for(int i = 0;i < name.length();i++)
				{
					if(name.charAt(i)=='"')
					{
						if(flag)
						{
							start = i;
							flag = false;
						}
						else
						{
							end = i;
							break;
						}
					}
				}				
				flag = true;
				
				if( (start>-1) && (end>-1) && (start<end))
				{
					String s = name.substring(start+1, end);
					
					System.out.println("name: " + s);
					
					if(s.contains("http://"))
					{
						list.add(s);
					}
					start = -1;
					end = -1;
				}
				
				//html end
				if (scanner.hasNext() == false)
				{
					if (name.contains("/search/store_search.php?p=" + page + "&kw=" + word))
					{
						System.out.println("find it" + "/search/store_search.php?p=" + page + "&kw=" + word);
						return true;
					}
				}

				
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		
		System.out.println("Search ok...");		
		return false;
	}
	
	
}
