package models;

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
	    
	    
	    User scott = User.find("byUsername", "sbasingetest").first();
	    assertNotNull("sbasingetest should be found!",scott);
	    assertEquals("sbasingetest", scott.username);
	}
}
