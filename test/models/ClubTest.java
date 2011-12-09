package models;

import java.util.List;

import org.junit.Test;


public class ClubTest extends ModelTest {
	@Test
	public void retrieveTestData() {
		List<Club> allClubs = Club.findAll();
	    assertNotNull("Clubs should be found!",allClubs);
	    
	    
	    Club test = Club.find("byName", "Test").first();
	    assertNotNull("Test Club should be found!",test);
	    assertEquals("Test Club's name should be set","Test", test.name);
	    assertEquals("Test Club's websiteurl should be set","http://columbusmetrogolfclub.blogspot.com/", test.websiteUrl);
	    assertEquals("Test Club's rssFeedUrl should be set","http://columbusmetrogolfclub.blogspot.com/feeds/posts/default", test.rssFeedUrl);
	}

}
