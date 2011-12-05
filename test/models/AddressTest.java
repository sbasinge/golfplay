package models;

import org.junit.Test;

import play.test.UnitTest;
import enums.State;

public class AddressTest extends UnitTest {
	@Test
	public void createAndRetrieveAddress() {
		String addrLine1 = "123 Main Street";
	    // Create a new user and save it
	    new Address(addrLine1, "SomeCity", State.OH, "43000",0d,0d).save();
	    
	    // Retrieve the user with e-mail address bob@gmail.com
	    Address addr1 = Address.find("byAddressLine1", addrLine1).first();
	    
	    // Test 
	    assertNotNull(addr1);
	    assertEquals(addrLine1, addr1.addressLine1);
	}

}
