package bootstrap;

import models.User;
import play.jobs.OnApplicationStart;
import play.test.Fixtures;

@OnApplicationStart
public class ApplicationInitPlayStyle {
    public void doJob() {
        // Load default data if the database is empty
        if(User.count() == 0) {
            Fixtures.load("data.yml");
        }
    }

}
