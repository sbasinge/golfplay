package models;

import java.util.List;

import org.junit.Test;

public class CourseTest extends ModelTest {

	
	@Test
	public void retrieveTestData() {
		List<Course> courses = Course.findAll();
	    assertNotNull("Courses should be found!",courses);
	    
	    
	    Course foxfire = Course.find("byName", "Player's Club").first();
	    assertNotNull("Foxfire should be found!",foxfire);
	    assertEquals("There should be 2 teeSets at the Player's Club.",2, foxfire.teeSets.size());
	}

}
