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
	
	public ArrayList<String> search(String word, int pages) throws IOException
	{
		//init variable
		boolean rep = true;
		list = new ArrayList<String>();
		//start page
		page = 2;
		
		//Query Urls all search
		String sword = java.net.URLEncoder.encode(word, "UTF-8");
		String url = "http://www.ipeen.com.tw/search/store_search.php?kw=" + sword;
		System.out.println(url);
		//String url = new String(surl.getBytes("big5"), "UTF-8"); 
		//System.out.println(url);
		
		System.out.println("Searching...");
		while (true)
		{
			rep = urlsearch(sword, url, pages);
			if (rep == false) break;
			url = "http://www.ipeen.com.tw/search/store_search.php?p=" + page + "&kw=" + sword;
			page++;
		}
		System.out.println("Search ok...");		
		
		return list;
	}

	public boolean urlsearch(String word, String url, int pages) throws IOException
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
					System.out.println("find it" + "/search/store_search.php?p=" + page + "&kw=" + word);
					if (name.contains("/search/store_search.php?p=" + page + "&kw=" + word))
					{
						System.out.println("perpare next page.");
						if (page == pages) return false;
						return true;
					}
				}
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		
		return false;
	}
	
	
}
