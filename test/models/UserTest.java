package models;

import org.junit.Test;

import play.test.UnitTest;

public class UserTest extends UnitTest {
	
	@Test
	public void createAndRetrieveUser() {
	    // Create a new user and save it
	    User bob = new User("bob flak", "bobUerName", "bob@gmail.com", "secret");
	    
	    // Retrieve the user with e-mail address bob@gmail.com
//	    User bob = User.find("byEmail", "bob@gmail.com").first();
	    
	    // Test 
	    assertNotNull(bob);
	    assertEquals("bobUerName", bob.username);
	}
}
