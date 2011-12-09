package models;

import java.util.List;

import org.junit.Test;

public class RoleTest extends ModelTest {

	@Test
	public void retrieveTestData() {
		List<Role> allRoles = Role.findAll();
	    assertNotNull("Roles should be found!",allRoles);
	    assertEquals("There should be 4 roles.",4, allRoles.size());
	    
	    
	    Role test = Role.find("byName", "Admin").first();
	    assertNotNull("Admin role should be found!",test);
	    assertEquals("Admin Role's name should be set","Admin", test.name);
	}

}
