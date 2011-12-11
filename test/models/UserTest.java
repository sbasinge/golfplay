package models;

import java.math.BigDecimal;
import java.util.List;

import org.junit.Test;

public class UserTest extends ModelTest {
	
	@Test
	public void createAndRetrieveUser() {
	    // Create a new user and save it
	    new User("bob flak", "bobUerName", "bob@gmail.com", "secret").save();
	    
	    // Retrieve the user with e-mail address bob@gmail.com
	    User bob = User.find("byEmail", "bob@gmail.com").first();
	    
	    // Test 
	    assertNotNull(bob);
	    assertEquals("bobUerName", bob.username);
	}
	
	@Test
	public void retrieveTestData() {
		List<User> allUsers = User.findAll();
	    assertNotNull("Users should be found!",allUsers);
	    
	    
	    User scott = User.find("byUsername", "sbasinge").first();
	    assertNotNull("sbasinge should be found!",scott);
	    assertEquals("sbasinge", scott.username);
	}

	@Test
	public void calculateHandicap() {
	    User scott = User.find("byUsername", "sbasinge").first();
	    assertNotNull("sbasinge should be found!",scott);
	    assertEquals("sbasinge", scott.username);
	    
	    boolean calculated = scott.calculateHandicap();
	    assertTrue("Handicap should be calculated.",calculated);
	    BigDecimal handicap = scott.getHandicap();
	    assertEquals("Handicap should be 14.7.",new BigDecimal(14.70).setScale(1,BigDecimal.ROUND_UP),handicap);
	}

	
}
