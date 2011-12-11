package bootstrap;

import models.User;
import play.Logger;
import play.jobs.Job;
import play.jobs.OnApplicationStart;
import play.test.Fixtures;

@OnApplicationStart
public class ApplicationInitPlayStyle extends Job {
	
    public void doJob() {
        // Load default data if the database is empty
        if(User.count() == 0) {
        	Logger.info("Loading seed data from data.yml");
            Fixtures.load("data.yml");
        } else {
        	Logger.info("Seed data already loaded... skipping.");
        }
    }

}
