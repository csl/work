import java.io.BufferedReader;

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Scanner;

import org.json.JSONArray;
import org.json.JSONObject; 


public class SearchGoogle {
	
	protected static ArrayList<String> gooleurllist;

	public SearchGoogle()
	{
		gooleurllist = new ArrayList<String>();
	}
	
	public void printURLs(BufferedReader in)
	{
	    Scanner scanner = new Scanner(in); 
	    int end = -1;
		int start = -1;
		ArrayList<String> list;
		list = new ArrayList<String>();
		boolean flag=true;
	    
	    //TODO以 class=l>進行scanner的切字，取出想要的字符範圍
		for(String t:list)
		{
			System.out.println(t);
			System.out.println();
		}
		scanner.close();		
	}
	
	public ArrayList<String> LoadGoogleSearch(String keyword, int amountOfResults) 
	{
		JSONArray jsonArray;
		System.out.println("google Searching...");
		//fetch pages
		for (int start=0; start < amountOfResults; start = start + 8)
		{
			try {
				String sword = java.net.URLEncoder.encode(keyword, "UTF-8");
				URL url = new URL("http://ajax.googleapis.com/ajax/services/search/web?rsz=8&v=1.0&hl=zh-TW&tbs=ctr:countryTW&cr=countryTW&q=" + sword + "&start=" + start);
	
				URLConnection connection = url.openConnection();
				connection.setRequestProperty("User-agent", "Chrome/7.0.517.44");
				
				String line="";
				StringBuilder builder = new StringBuilder();
				BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream(), "UTF-8"));
				while((line = reader.readLine()) != null) 
				{
					builder.append(line);
				}
				
				JSONObject jsonObject = new JSONObject(builder.toString())
	            .getJSONObject("responseData");
	
				jsonArray = jsonObject.getJSONArray("results");
				for(int i=0;i<jsonArray.length();i++)
				{
					JSONObject jsonObject2 = (JSONObject)jsonArray.opt(i);
					gooleurllist.add(jsonObject2.getString("url"));
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		return gooleurllist;
	}
	
}
