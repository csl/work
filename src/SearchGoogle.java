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
	
	public void LoadGoogleSearch(String keyword) 
	{
		URL url;
		try {
			String sword = java.net.URLEncoder.encode(keyword, "UTF-8");

			url = new URL("http://ajax.googleapis.com/ajax/services/search/web?rsz=8&v=1.0&hl=tw&q=" + sword);

			System.out.println("url:" + url);
			
			URLConnection connection = url.openConnection();
			connection.setRequestProperty("User-agent", "Chrome/7.0.517.44");

			String line;
			StringBuilder builder = new StringBuilder();
			BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream(), "UTF-8"));
			while((line = reader.readLine()) != null) 
			{
				builder.append(line);
			}

			JSONObject json = new JSONObject(builder.toString());
			JSONObject jsonObject = new JSONObject(builder.toString())
            .getJSONObject("responseData");

			JSONArray jsonArray = jsonObject.getJSONArray("cursor");
			for(int i=0;i<jsonArray.length();i++)
			{
				JSONObject jsonObject2 = (JSONObject)jsonArray.opt(i);
				System.out.println(jsonObject2.getString("pages"));
			}
			
			
			jsonArray = jsonObject.getJSONArray("results");
			for(int i=0;i<jsonArray.length();i++)
			{
				JSONObject jsonObject2 = (JSONObject)jsonArray.opt(i);
				System.out.println(jsonObject2.getString("url"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		/*
		String sCurrentLine="";
		
		try 
	    {
	        URL url = new URL("http://www.google.com.tw/search?q=" + keyword);
	        URLConnection yc = url.openConnection();
	        yc.setRequestProperty("User-agent", "Chrome/7.0.517.44");
	        BufferedReader in = new BufferedReader(new InputStreamReader(yc.getInputStream(),"big5")); //encode
	        while ((sCurrentLine = in.readLine()) != null) 
	        { 
		        System.out.println("tag: " + sCurrentLine);
	        } 
	        //printURLs(in);
	        in.close();
	    } 
	    catch (Exception e) 
	    {
	        e.printStackTrace();
	    }*/
	}
}
