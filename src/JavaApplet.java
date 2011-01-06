import javax.swing.JApplet;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Graphics;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.StringTokenizer;
import java.util.Map.Entry;

public class JavaApplet extends JApplet 
{
	static websitetree tree;
	
	private static ArrayList<String> list = new ArrayList<String>();
	protected static websites W = new websites();
	protected static SearchFromIpeen S = new SearchFromIpeen();

	private static final long serialVersionUID = 1L;
	
	String address,keyword, result;
	String item1result;
	String item2result;
	String item3result;
	String item4result;
	
	String word;
	String urls;
	
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

	    	while((input = in.readLine()) != null)
	    	{
		    	scanner = new Scanner(input);
	
		    	while(scanner.hasNext())
		    	{
			    	String name = scanner.next();
			    	
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
	
	
	public void init() 
	{
		result="";
		keyword = JOptionPane.showInputDialog("Stage 0 :Keyword Counting, Keyword:");
		item1result = item1result + "Stage 0, keyword appears: " + countWords( keyword, "http://www.ipeen.com.tw/","utf-8");

		//Item 2
		word = JOptionPane.showInputDialog("Stage 1: Single-Page Ranking, Please enter your keywords (ex: food,abc...) : ");
		urls = JOptionPane.showInputDialog("urls: "); 
		System.out.print("Please enter urls (ex: http://www.ipeen.com.tw,http://www.food.com.tw...) : ");
		item2result = item2result + "Stage 1, The result is :\n";
		
			StringTokenizer urlstoken = new StringTokenizer( urls, ",");
			while( urlstoken.hasMoreTokens() )
			{
				StringTokenizer wstoken = new StringTokenizer( word, ",");
				Map<String, Integer> map_Data = new HashMap<String, Integer>();
	            String myurl = urlstoken.nextToken();
				while( wstoken.hasMoreTokens() )
			    {
			            String subkeyords = wstoken.nextToken();
			            int counts = countWords( subkeyords, myurl, "utf-8");
			            item2result = item2result + subkeyords + " appears: " + counts + "\n";
			            map_Data.put(subkeyords, counts);
			    }
				
				ArrayList<Entry<String, Integer>> list_Data = new ArrayList<Map.Entry<String, Integer>>(map_Data.entrySet());
				Collections.sort(list_Data, new Comparator<Map.Entry<String, Integer>>(){
					 public int compare(Map.Entry<String, Integer> entry1,
						 Map.Entry<String, Integer> entry2){
						 return (entry2.getValue() - entry1.getValue());
					 }
				 });
			
				item2result = item2result + "Rank:\n";
				int i=1;
				 for (Map.Entry<String, Integer> entry:list_Data)
				 {
					 item2result = item2result + i + ". " + entry.getKey() + "\t" + map_Data.get(entry.getKey()) + "\n";
					 i++;
				 }
			}
			
			word = JOptionPane.showInputDialog("Stage 2 :Hierarchy Ranking, Please enter your keywords (ex: food,abc...) : ");
			item3result = item3result + "Stage 2, The result is :\n";
			
			StringTokenizer stoken = new StringTokenizer( word, ",");
			while( stoken.hasMoreTokens() )
		    {
		            String subkeyords = stoken.nextToken();
					tree = new websitetree("http://www.ipeen.com.tw/", countWords( subkeyords, "http://www.ipeen.com.tw/", "utf-8"));
					//default: 4 page (avoid data)
					ArrayList<String> _list = null;
					try {
						_list = S.search(subkeyords, 2);
					} catch (Exception e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					
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
							item3result = item3result + i + ". " + w.element.toString()+ "\n";
							i++;
						}
					}
					catch(Exception e)
					{
						e.printStackTrace();
					}
		            
		    }			
			
			//Item 3
			item4result = item4result + "Stage 3:Increase your rank on google\n";
			word = JOptionPane.showInputDialog("Stage 3, Please enter your keywords (ex: food,abc...) : ");
			stoken = new StringTokenizer( word, ",");

			while( stoken.hasMoreTokens() )
		    {
		            String subkeyords = stoken.nextToken();
					tree = new websitetree("http://www.google.com.tw/", countWords( subkeyords, "http://www.google.com.tw/", "utf-8"));
					//default: 16 (avoid data)
					SearchGoogle sgoogle= new SearchGoogle();
					ArrayList<String> _list = sgoogle.LoadGoogleSearch(subkeyords, 32);

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
							item4result = item4result + i + ". " + w.element.toString();
							i++;
						}
					}
					catch(Exception e)
					{
						e.printStackTrace();
					}
		    }
	}
	
	public void paint(Graphics g)
	{
		super.paint(g);
		g.drawString(item1result, 10, 20);
		g.drawString(item2result, 10, 40);
		g.drawString(item3result, 10, 60);
		g.drawString(item4result, 10, 80);

	}
}