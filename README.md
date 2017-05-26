# WebCrawler
Java based web crawler that searches a given website for a particular word. If the word is not found on the immediate page, the spider will search through any pages that the original website links to find the given word.

This web crawler was made after following the guidance of:
http://www.netinstructions.com/how-to-make-a-simple-web-crawler-in-java/

The Spider Class uses an external java class <b> jsoup </b> which can be downloaded here: http://java2s.com/Code/Jar/j/Downloadjsoup160jar.htm

SpiderTest.java is the main tester class, in which a url can be manually inputted and the word to search for.
Current version only looks at a max of 10 linked pages, a limit that can be changed by changing the MAX_PAGES_VISIT class constant in Spider.java
