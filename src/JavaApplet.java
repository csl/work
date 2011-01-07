import javax.swing.JApplet;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Label;
import java.awt.Rectangle;
import java.awt.TextArea;
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
		//resize
		setSize(800, 600);
		
		result="";
		keyword = JOptionPane.showInputDialog("Stage 0 :Keyword Counting, Keyword:");
		result = result + "Stage 0, keyword appears:  " + countWords( keyword, "http://www.ipeen.com.tw/","utf-8") + "\n\n";

		//Item 2
		word = JOptionPane.showInputDialog("Stage 1: Single-Page Ranking, Please enter your keywords (ex: food,abc...) : ");
		urls = JOptionPane.showInputDialog("urls: "); 
		result = result + "Stage 1, The result is :\n";
		
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
			            result = result + subkeyords + " appears: " + counts + "\n";
			            map_Data.put(subkeyords, counts);
			    }
				
				ArrayList<Entry<String, Integer>> list_Data = new ArrayList<Map.Entry<String, Integer>>(map_Data.entrySet());
				Collections.sort(list_Data, new Comparator<Map.Entry<String, Integer>>(){
					 public int compare(Map.Entry<String, Integer> entry1,
						 Map.Entry<String, Integer> entry2){
						 return (entry2.getValue() - entry1.getValue());
					 }
				 });
			
				result = result + "Rank:\n";
				int i=1;
				 for (Map.Entry<String, Integer> entry:list_Data)
				 {
					 result = result + i + ". " + entry.getKey() + "\t" + map_Data.get(entry.getKey()) + "\n";
					 i++;
				 }
			}
			
			result = result + "\n";
			word = JOptionPane.showInputDialog("Stage 2 :Hierarchy Ranking, Please enter your keywords (ex: food,abc...) : ");
			result = result + "Stage 2, The result is :\n";
			
			StringTokenizer stoken = new StringTokenizer( word, ",");
			ArrayList<String> _list = null;
			while( stoken.hasMoreTokens() )
		    {
		            String subkeyords = stoken.nextToken();
					tree = new websitetree("http://www.ipeen.com.tw/", countWords( subkeyords, "http://www.ipeen.com.tw/", "utf-8"));

					//default: 4 page (avoid data)
					try {
						_list = S.search(subkeyords, 3);

						//_list: all search URLS
						for(String s:_list)
						{
							int count = countWords(subkeyords, s, "utf-8");
							tree.root.Add(s, count);
							System.out.println(s);
							result = result + s + " , appear:  " + count + "\n";
						}
							
						int i = 1;
						
						for(websitenode w:tree.root.children)
						{
							result = result + i + ". " + w.element.toString()+ "\n";
							i++;
						}
					}
					catch(Exception e)
					{
						e.printStackTrace();
					}
		            
		    }			
			
			result = result + "\n";
			//Item 3
			result = result + "Stage 3:Increase your rank on google\n";
			word = JOptionPane.showInputDialog("Stage 3, Please enter your keywords (ex: food,abc...) : ");
			stoken = new StringTokenizer( word, ",");

			while( stoken.hasMoreTokens() )
		    {
		            String subkeyords = stoken.nextToken();
					tree = new websitetree("http://www.google.com.tw/", countWords( subkeyords, "http://www.google.com.tw/", "utf-8"));
					//default: 16 (avoid data)
					SearchGoogle sgoogle= new SearchGoogle();
					_list = sgoogle.LoadGoogleSearch(subkeyords, 32);

					try
					{
						//_list: all search URLS
						for(String s:_list)
						{
							int count = countWords(subkeyords, s, "utf-8");
							tree.root.Add(s, count);
							result = result + s + " , appear:  " + count + "\n";
						}
						
						int i = 1;
						result = result + "rank: \n";
						for(websitenode w:tree.root.children)
						{
							result = result + i + ". " + w.element.toString() + "\n";
							i++;
						}
					}
					catch(Exception e)
					{
						e.printStackTrace();
					}
		    }

		TextArea content = new TextArea(result, 500, 250, TextArea.SCROLLBARS_VERTICAL_ONLY);
		Font bigFont = new Font("SanSerif", Font.BOLD, 16);
		content.setFont(bigFont);
	    add(content);
	}
	
	public void paint(Graphics g)
	{
		super.paint(g);
	}
}