package models;

import java.util.List;

import org.junit.Test;

public class TeeSetTest extends ModelTest {

	@Test
	public void retrieveTestData() {
		List<TeeSet> teeSets = TeeSet.findAll();
	    assertNotNull("teeSets should be found!",teeSets);
	    
	    
//	    TeeSet foxfire = TeeSet.find("byName", "FoxFire").first();
//	    assertNotNull("Foxfire should be found!",foxfire);
//	    assertEquals("The facility name should be FoxFire.","FoxFire", foxfire.name);
	}

}
