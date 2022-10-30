import java.io.*;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
	/**
	 * Class assignment that retrieves a poem from a specified website.  Jsoup is used to get the content of the poem, the author and the title only 
	 * and input into a string.  The program then determines the word count for each word in the poem and outputs to the console the top 20 words
	 * and their frequency.
	 * @author ha33a
	 * 
	 */
public class SdlcAssOne {
	/**
	 * This is the main method.
	 * @param args  Strings passed into the main
	 */

	public static void main(String[] args)
	{
		
 		String text = "";  // variable to store the poem
		
		try
		{
			// using Jsoup to retrieve the poem from the website - 
			// no HTML tags, ignore everything before title and everything after the end of the poem
			
			Document doc = Jsoup.connect("https://www.gutenberg.org/files/1065/1065-h/1065-h.htm").get();
			
			// using the p tag to isolate the body of the poem			
			Elements newsHeadlines = doc.select("p");
			for (Element headline : newsHeadlines)
			{
				text += " " + headline.text();
			}
			// using the h1 tag to isolate the title of the poem
			newsHeadlines = doc.select("h1");
			for (Element headline : newsHeadlines) {
				text += " " + headline.text();

			}
			// using the h2 tag to isolate the author
			newsHeadlines = doc.select("h2");
			for (Element headline : newsHeadlines) {
				text += " " + headline.text();

			}
			
		}
		catch(IOException ex)
		{
			ex.printStackTrace();
		}
		// cleaning the data to get an accurate word count by
		// removing punctuation and changing everything to lowercase 
	
		String[] textArray = text.toLowerCase().split("\\W+");  	

		// creating three collections 
		// 2 hashmaps - one to get frequency of each word - one to hold the sorted words
		// 1 list to do the sorting/ranking top to bottom

		ArrayList<Integer> list = new ArrayList<>();
	    Map<String, Integer> freqMap = new HashMap<>();
	    LinkedHashMap<String, Integer> sortedMap = new LinkedHashMap<>();
	    
	    // loop to iterate through clean data 
	    // if word exists - ups the count by 1
	    // if word doesn't exist - adds word and sets count to 1
	    for (String s : textArray)
	    {	    	
	        if (freqMap.containsKey(s)) {
	            Integer count = freqMap.get(s);
	            freqMap.put(s, count + 1);
	        } else {
	            freqMap.put(s, 1);
	        }

	    }
	    // adds values to list
        for (Map.Entry<String, Integer> entry : freqMap.entrySet()) {
            list.add(entry.getValue());
        }
        // sorts list
        Collections.sort(list, new Comparator<Integer>() {
            public int compare(Integer str, Integer str1) {
                return (str1).compareTo(str);
            }
        });
        // uses list and keys from sorted data and
        // adds to new hashtable
        for (Integer str : list) {
            for (Map.Entry<String, Integer> entry : freqMap.entrySet()) {
                if (entry.getValue().equals(str)) {
                    sortedMap.put(entry.getKey(), str);
                }
            }
        }
        // iterates through sorted has table and outputs word and count
        // from highest count to lowest - top 20
        int count = 1;  // variable to count to 20
        for (Map.Entry<String, Integer> e: sortedMap.entrySet())
        {
        	
        	if (count < 21)
        	{
        	System.out.println(count + ". " + e.getKey() + " " + e.getValue());
        	count += 1;
        	}

        }
	}
	

}

	
