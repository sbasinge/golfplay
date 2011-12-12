package service;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import com.sun.syndication.feed.synd.SyndFeed;
import com.sun.syndication.io.FeedException;
import com.sun.syndication.io.SyndFeedInput;
import com.sun.syndication.io.XmlReader;

public class FeedReader {


    public List parse(String url, int size) throws IllegalArgumentException, FeedException, IOException {
    	if (url == null)
    		return new ArrayList();
        List entries = new ArrayList();
        URL feedSource = new URL(url);
        SyndFeedInput input = new SyndFeedInput();
        SyndFeed feed = input.build(new XmlReader(feedSource));
        int i = 0;
        
        for(Object f : feed.getEntries()) {
            if(i == size)
                break;

            entries.add(f);
            i++;
        }
        
        return entries;
    }

}
