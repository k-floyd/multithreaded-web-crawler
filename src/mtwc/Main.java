package mtwc;

import java.util.ArrayList;

public class Main {

	public static void main(String[] args) {
		
		//crawl 3 pages
		ArrayList<webCrawler> bots = new ArrayList<>();
		bots.add(new webCrawler("https://www.georgiasouthern.edu/", 1));
		bots.add(new webCrawler("https://www.emory.edu/", 2));
		bots.add(new webCrawler("https://www.uga.edu/", 3));
		bots.add(new webCrawler("https://www.kennesaw.edu/", 4));
		
		//loop through each of the bots
		for(webCrawler w : bots) {
			try {
				w.getThread().join();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		
	}

}
