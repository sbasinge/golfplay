package models;

import java.util.List;

import org.junit.Test;

public class FacilityTest extends ModelTest {

	@Test
	public void retrieveTestData() {
		List<Facility> facilities = Facility.findAll();
	    assertNotNull("Facilities should be found!",facilities);
	    
	    
	    Facility foxfire = Facility.find("byName", "FoxFire").first();
	    assertNotNull("Foxfire should be found!",foxfire);
	    assertEquals("The facility name should be FoxFire.","FoxFire", foxfire.name);
	}

}
