package models;

import java.util.List;

import org.junit.Test;

import enums.TeeType;

public class ScoreTest extends ModelTest {
	@Test
	public void retrieveTestData() {
		List<Score> scores = Score.findAll();
	    assertNotNull("Scores should be found!",scores);
	    
//	    Score scott1 = Score.find("byName", "Player's Club").first();
	    Score scott1 = scores.get(0);
	    assertNotNull("scott1 score should be found!",scott1);
	    assertEquals("The score should be at Player's Club WHITE.",TeeType.WHITE, scott1.teeSet.teeType);
	}
}
