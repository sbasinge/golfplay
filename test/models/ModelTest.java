package models;

import org.junit.Before;

import play.test.Fixtures;
import play.test.UnitTest;

public abstract class ModelTest extends UnitTest {
	@Before
	public void setUp() {
	    Fixtures.deleteAll();
	    Fixtures.loadModels("test-data.yml");
	}
	

}
