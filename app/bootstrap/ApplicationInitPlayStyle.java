package bootstrap;

import models.User;
import notifiers.EmailNotifier;
import notifiers.Notifier;
import notifiers.SiteMessageNotifier;
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
        Notifier.instance().addRegistrant(new EmailNotifier()); 
        Notifier.instance().addRegistrant(new SiteMessageNotifier()); 
    }

}
