/**
 * Created by mukul on 5/24/2017.
 */

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

/**
 * Phase 1 - Retrive web page (Doc A)
 * Phase 2 - Collect all links on Doc A --> keep track of pages already visited, put a limit on number of pages visited
 * Phase 3 - Collect all words on Doc A
 * Phase 4 - Search if the word is in the list of words
 * Phase 5 - Visit net link
 *
 * Helper Class SpiderLeg will:
 *  crawl(nextURL) -- takes URL and makes HTTP request for page
 *  searchForWord(word) -- finds word on page
 *  getLink() -- give list of all URLs on the current page
 */

public class Spider
{
	private static final int MAX_PAGES_VISIT = 10;
	//use Set because sets have unique entries -- no duplicates
	private Set<String> pagesVisited = new HashSet<String>();
	//each URL is added to this List, allows it to be constantly added -- LL has breadth first  which is nice
	private List<String> pagesToVisit = new LinkedList<String>();

	/**
	 * Searches for a given string, starting with the given URL
	 * uses SpiderLeg helper class to search through the text of the page, and the pages the given URL points to, with a max number of pages being 10
	 * Will print to command line if word is found or not within 10 pages
	 * @param url Starting url of page
	 * @param searchWord Word to search for
	 */
	public void search(String url, String searchWord)
	{
		while(pagesVisited.size() < MAX_PAGES_VISIT) //only looking at that max number of pages set
		{
			String currentURL;
			SpiderLeg leg = new SpiderLeg(); // this class will do lots of heavy lifting
			if(pagesToVisit.isEmpty()) //no pages are in the List currently
			{
				currentURL = url; // make the currentURL the one given
				pagesVisited.add(url);//keep track of it in pagesVisted
			}
			else //pages have been visited
			{
				currentURL = this.nextURL(); //grab the nextURL method url
			}
			leg.crawl(currentURL); // the leg actually crawls that url
			if(leg.searchForWord(searchWord)) //searchForWord gives a boolean if the word is found in that particular url
			{
				System.out.println(String.format("**Success** Word %s found at %s",searchWord, currentURL));
				break; //stop the loop
			}
			pagesToVisit.addAll(leg.getLinks()); //get all the links to the current page we're on, make that the
		}
		System.out.println(String.format("**Done** Visited %s web page(s) ",pagesVisited.size()));
	}

	/**
	 * nextURL looks through the pagesToVisit collected, finds an unvisited URL (after comparing to pagesVisited Set
	 * @return String URL of next page to visit
	 */
	private String nextURL()
	{
		String nextURL;
		do
		{
			nextURL = pagesToVisit.remove(0); // get first entry
		}
		while(pagesVisited.contains(nextURL)); // make sure it's not already visited, if it is, then while and get the next one, until it's an unvisited page
		pagesVisited.add(nextURL); // exit loop, have new page, add it to the pages visited
		return nextURL; //return the nextURL, an unvisited page
	}
}