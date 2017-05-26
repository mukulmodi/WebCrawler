# WebCrawler
Java based web crawler, searches a given page and its linked pages for a given word.

This web crawler was made after following the guidance of http://www.netinstructions.com/how-to-make-a-simple-web-crawler-in-java/

SpiderTest.java is the main tester class, in which a url can be manually inputted and the word to search for.
Current version only looks at a max of 10 linked pages, a limit that can be changed by changing the MAX_PAGES_VISIT class constant in Spider.java
