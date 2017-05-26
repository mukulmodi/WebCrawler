/**
 * Created by mukul on 5/25/2017.
 *
 *
 * Helper class for Spider, does most of the heavy lifting
 * 3 methods
 *  crawl(nextURL) -- takes URL and makes HTTP request for page
 *  searchForWord(word) -- finds word on page
 *  getLink() -- give list of all URLs on the current page
 */

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

//jsoup imports for anything networking related


public class SpiderLeg
{
	private List<String> links = new LinkedList<String>(); // linkedlist to hold the strings of urls
	private Document htmlDoc; //the actual webpage from a given url

	//USER_AGENT will be a fake browser so server doesn't recognize robot
	private static String USER_AGENT = "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/535.1 (KHTML, like Gecko) Chrome/13.0.782.112 Safari/535.1";


	/**
	 * Connects to the given html page, recieves a Document from it
	 * Finds all links on the given htmlDoc, places those into the LinkedList containing all links
	 * @param url
	 */
	public boolean crawl(String url)
	{
		try
		{
			//making the connection and getting the html page
			Connection connection = Jsoup.connect(url).userAgent(USER_AGENT);
			Document html = connection.get();
			htmlDoc = html;

			if(connection.response().statusCode() == 200) // 200 is the HTTP OK status code, we know we connected
			{
				System.out.println("\n**Visiting** Received web page at " + url);
			}
			if(!connection.response().contentType().contains("text/html"))
			{
				System.out.println("**Failure** Retrieved something other than HTML");
				return false;
			}

			//collect on the links on the html page, always defined by href's
			Elements linksOnPage = htmlDoc.select("a[href]");
			System.out.println("Found " + linksOnPage.size() + " links");
			//each link collected, the url gets added to the LinkedList, in the order it was found
			for(Element link: linksOnPage)
			{
				this.links.add(link.absUrl("href"));
			}
			return true;
		}
		//just in case of errors
		catch(IOException ioe)
		{
			//HTTP request didn't work
			System.out.println( "Error in HTTP request " + ioe);
			return false;
		}
	}

	/**
	 * Takes the given html document, converts it to text and searches for a given word
	 * @param wordToFind String word to be found in the webpage
	 * @return boolean true if word is found
	 */
	public boolean searchForWord(String wordToFind)
	{
		// just in case check to verify that the page has been crawled first
		if(htmlDoc == null)
		{
			System.out.println("ERROR! Call crawl() before performing analysis on the document");
			return false;
		}

		System.out.println("Searching for " + wordToFind + "...");
		String bodyText = htmlDoc.body().text(); //takes the body of the doc converts it into String
		return bodyText.toUpperCase().contains(wordToFind.toUpperCase()); //use one consistent case, either upper or lower, doesn't matter because this doesn't change the page
	}

	/**
	 * Retrieves LinkedList that contains the list of websites the given page links to
	 * @return List<String> links containing Strings of urls that the current page links to
	 */
	public List<String> getLinks()
	{
		return this.links;
	}
}
