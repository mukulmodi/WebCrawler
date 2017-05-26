/**
 * Tester file for the Spider class to search a webpage and its links
 * Created by mukul on 5/25/2017.
 */
public class SpiderTest
{
	public static void main(String[] args)
	{
		Spider spider = new Spider();
		spider.search("http://www.cnn.com/", "India");
	}
}
