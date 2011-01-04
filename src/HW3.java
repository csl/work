import java.awt.List;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Scanner;
import java.util.StringTokenizer;

public class HW3 
{
	static websitetree tree;
	
	private static ArrayList<String> list = new ArrayList<String>();
	protected static websites W = new websites();
	protected static SearchFromIpeen S = new SearchFromIpeen();
	
	
	public static int countWords(String word, String website, String coder)
	{
		
		String input;
		Scanner scanner;
		int count = 0;
		
		try
		{
			URL url = new URL(website);
			URLConnection uc = url.openConnection();
			uc.setRequestProperty("User-agent", "Chrome/7.0.517.44");
			BufferedReader in = new BufferedReader(new InputStreamReader(uc.getInputStream(), coder));

	    	//while迴圈一次讀一行出來處理
	    	while((input = in.readLine()) != null)
	    	{
		    	scanner = new Scanner(input);
	
		    	//針對一行用scanner一次scan一個字出來 用空白字元做區別
		    	while(scanner.hasNext())
		    	{
			    	String name = scanner.next();
			    	
			    	//如果該字是keyword則紀錄
			    	if(name.contains(word)){
			    		count++;
	
		    	}
	    }
	    	scanner.close();
	}
}
		catch(IOException e)
		{
			//e.printStackTrace();
		}
		
		return count;
	}
	

	public static void main(String[] args) throws IOException 
	{
		String word = "";
		String urls = "";
		
		while(true)
		{
			Scanner sc  = new Scanner(System.in);

			//Item 1
			System.out.println("Stage 0 :Keyword Counting");
			System.out.print("\n");
			System.out.print("Please enter your keyword: ");
			word = sc.next();
			System.out.println("The result is :");
			System.out.println("keyword appears: " + countWords( word, "http://www.ipeen.com.tw/","utf-8"));
			System.out.print("\n");
			
			//Item 2
			System.out.println("Stage 1: Single-Page Ranking");
			System.out.print("\n");
			System.out.print("Please enter your keywords (ex: food,abc...) : ");
			word = sc.next();
			System.out.print("Please enter urls (ex: http://www.ipeen.com.tw,http://www.food.com.tw...) : ");
			urls = sc.next();
			System.out.println("The result is :");
			
			StringTokenizer urlstoken = new StringTokenizer( urls, ",");
			while( urlstoken.hasMoreTokens() )
			{
				StringTokenizer wstoken = new StringTokenizer( word, ",");
				Map<String, Integer> map_Data = new HashMap<String, Integer>();
	            String myurl = urlstoken.nextToken();
				System.out.println("each URL: " + myurl);
				while( wstoken.hasMoreTokens() )
			    {
			            String subkeyords = wstoken.nextToken();
			            int counts = countWords( subkeyords, myurl, "utf-8");
			            System.out.println( subkeyords + " appears: " + counts);
			            map_Data.put(subkeyords, counts);
			    }
				
				ArrayList<Entry<String, Integer>> list_Data = new ArrayList<Map.Entry<String, Integer>>(map_Data.entrySet());
				Collections.sort(list_Data, new Comparator<Map.Entry<String, Integer>>(){
					 public int compare(Map.Entry<String, Integer> entry1,
						 Map.Entry<String, Integer> entry2){
						 return (entry2.getValue() - entry1.getValue());
					 }
				 });
			
				System.out.println("Rank: ");
				int i=1;
				 for (Map.Entry<String, Integer> entry:list_Data)
				 {
					 System.out.println(i + ". " + entry.getKey() + "\t" + map_Data.get(entry.getKey()));
					 i++;
				 }
			}
			
			System.out.print("\n");
			
			System.out.println("Stage 2 :Hierarchy Ranking");
			System.out.print("\n");
			System.out.print("Please enter your keywords (ex: food,abc...) : ");
			word = sc.next();
			System.out.println("The result is :");
			
			StringTokenizer stoken = new StringTokenizer( word, ",");
			while( stoken.hasMoreTokens() )
		    {
		            String subkeyords = stoken.nextToken();
					tree = new websitetree("http://www.ipeen.com.tw/", countWords( subkeyords, "http://www.ipeen.com.tw/", "utf-8"));
					//default: 4 page (avoid data)
					ArrayList<String> _list = S.search(subkeyords, 2);
					System.out.println("Keyord Scope: " + subkeyords);
					try
					{
						
						//_list: all search URLS
						for(String s:_list)
						{
							tree.root.Add(s, countWords(subkeyords, s, "utf-8"));
						}
							
						int i = 1;
						
						for(websitenode w:tree.root.children)
						{
							System.out.println(i + ". " + w.element.toString());
							i++;
						}
					}
					catch(Exception e)
					{
						e.printStackTrace();
					}
		            
		    }			
			
			System.out.print("\n");

			//Item 3
			System.out.println("Stage 3:Increase your rank on google");
			System.out.print("\n");
			System.out.print("Please enter your keywords (ex: food,abc...) : ");
			word = sc.next();
			stoken = new StringTokenizer( word, ",");

			while( stoken.hasMoreTokens() )
		    {
		            String subkeyords = stoken.nextToken();
					tree = new websitetree("http://www.google.com.tw/", countWords( subkeyords, "http://www.google.com.tw/", "utf-8"));
					//default: 16 (avoid data)
					SearchGoogle sgoogle= new SearchGoogle();
					ArrayList<String> _list = sgoogle.LoadGoogleSearch(subkeyords, 32);

					System.out.println("Keyord Scope: " + subkeyords);
					try
					{
						//_list: all search URLS
						for(String s:_list)
						{
							tree.root.Add(s, countWords(subkeyords, s, "utf-8"));
						}
						
						int i = 1;
						
						for(websitenode w:tree.root.children)
						{
							System.out.println(i + ". " + w.element.toString());
							i++;
						}
					}
					catch(Exception e)
					{
						e.printStackTrace();
					}
		    }
			
			System.out.print("\n");				
		}					
}}
