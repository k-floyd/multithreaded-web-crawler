package mtwc;

import java.io.IOException;
import java.util.ArrayList;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

public class webCrawler implements Runnable {
	
	//determines the max depth the crawler can go
	private static final int MAX_DEPTH = 4;	
	private Thread thread;
	private String first_link;
	private ArrayList<String> visitedLinks = new ArrayList<String>(); // keeps the number of links visited
	private int ID;
	
	public webCrawler (String link, int num) {
		System.out.print("WebCrawler created");
		first_link = link;
		ID = num;
		
		//create a thread for the bot to run
		//passes the parameter of the object
		thread = new Thread(this);
		thread.start();
	}
	
	@Override
	public void run() {
		crawl(1, first_link);
	}
	
	private void crawl(int level, String url) {
		if(level<= MAX_DEPTH) {
			Document doc = request(url);
			
			if (doc != null) {
				//remove the href from the url
				for (Element link : doc.select("a[href]")) {
					String next_link = link.absUrl("href");
					
					//check if we've been to this website
					if(visitedLinks.contains(next_link) == false) {
						crawl(level++, next_link);
					}
				}
			}
		}
	}
	
	private Document request (String url) {
		//throw error when something goes wrong when you're trying to make a connection
		try {
			Connection con = Jsoup.connect(url);
			
			//create a document
			Document doc = con.get();
			
			if(con.response().statusCode() == 200) {
				//print the url and bot ID
				System.out.println("\n**Bot ID:" + ID + " Received Webpage at " + url);
				
				String title = doc.title();
				System.out.println(title);
				visitedLinks.add(url);
				
				return doc;
			}
			
			//if not able to receive the doc
			return null;
		} catch (IOException e) {
			return null;
		}
		
	}
	
	public Thread getThread() {
		return thread;
	}

}
