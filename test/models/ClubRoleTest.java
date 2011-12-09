package models;

import java.util.List;

import org.junit.Test;

public class ClubRoleTest extends ModelTest {
	@Test
	public void retrieveTestData() {
		List<ClubRole> allRoles = ClubRole.findAll();
	    assertNotNull("Roles should be found!",allRoles);
	    assertEquals("There should be 4 roles.",4, allRoles.size());
	    
	    
	    Role adminRole = Role.find("byName", "Admin").first();
	    ClubRole clubRole = ClubRole.find("byRole",adminRole).first();
	    assertNotNull("ClubAdmin role should be found!",clubRole);
//	    assertEquals("Admin Role's name should be set","Admin", test.name);
	}

}
