package controllers;

import models.User;
import play.mvc.Before;
import play.mvc.Controller;

//@With(Secure.class)
public class Application extends Controller {

    @Before
    static void setConnectedUser() {
        if(Security.isConnected()) {
            User user = User.find("byUsername", Security.connected()).first();
    		if (user.clubs.size()==1) {
    			user.selectedClub = user.clubs.get(0);
    			renderArgs.put("selectedClub", user.selectedClub.name);
    		}
            renderArgs.put("user", user.name);
        }
    }

    public static void index() {
        render();
    }


}