import java.awt.List;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Scanner;



public class HW3 
{
	static websitetree tree;
	
	private static ArrayList<String> list = new ArrayList<String>();
	protected static websites W = new websites();
	protected static SearchFromIpeen S = new SearchFromIpeen();
	
	
	public static int countWords(String word, String website)
	{
		
		String input;
		Scanner scanner;
		int count = 0;
		
		try
		{
			URL url = new URL(website);
			URLConnection uc = url.openConnection();
			BufferedReader in = new BufferedReader(new InputStreamReader(uc.getInputStream(), "utf-8"));

	    	//while迴圈一次讀一行出來處理
	    	while((input = in.readLine()) != null){
	    	scanner = new Scanner(input);

	    	//針對一行用scanner一次scan一個字出來 用空白字元做區別
	    	while(scanner.hasNext()){
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
			e.printStackTrace();
		}
		
		return count;
	}
	

	public static void main(String[] args) throws IOException 
	{
		SearchGoogle sgoogle= new SearchGoogle();
		
		while(true)
		{
			Scanner sc  = new Scanner(System.in);
			
			System.out.print("Please enter your keyword: ");
			String word = sc.next();
			
			sgoogle.LoadGoogleSearch(word);
			
			//tree = new websitetree("http://www.ipeen.com.tw/", countWords( word, "http://www.ipeen.com.tw/"));
			//ArrayList<String> _list = S.search(word);
/*			
			System.out.println("The result is :");
			
			try
			{
				//_list: all search URLS
				for(String s:_list)
				{
					tree.root.Add(s, countWords(word, s));
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
			
				System.out.print("\n");
				*/
		}
			
		
}}
