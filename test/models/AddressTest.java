package models;

import java.util.List;

import org.junit.Test;

import enums.State;

public class AddressTest extends ModelTest {
//	@Test
	public void createAndRetrieveAddress() {
		String addrLine1 = "123 Main Street";
	    // Create a new user and save it
	    new Address(addrLine1, "SomeCity", State.OH, "43004",0d,0d).save();
	    
	    // Retrieve the user with e-mail address bob@gmail.com
	    Address addr1 = Address.find("byAddressLine1", addrLine1).first();
	    
	    // Test 
	    assertNotNull(addr1);
	    assertEquals(addrLine1, addr1.addressLine1);
	}

	@Test
	public void retrieveTestData() {
		List<Address> allAddresses = Address.findAll();
	    assertNotNull("Addresses should be found!",allAddresses);
	    assertEquals("There should be 2 addresses.",2, allAddresses.size());
	    
	    Address address = allAddresses.get(0);
	    assertEquals("The addresses state should be OH.",State.OH, address.state);
	    assertEquals("The addresses zip code should be 43004.","43004", address.zipCode);
	    
	    
//	    Role adminRole = Role.find("byName", "Admin").first();
//	    ClubRole clubRole = ClubRole.find("byRole",adminRole).first();
//	    assertNotNull("ClubAdmin role should be found!",clubRole);
//	    assertEquals("Admin Role's name should be set","Admin", test.name);
	}

}
