package controllers;

import java.util.List;

import models.SiteMessage;
import models.User;
import play.Logger;
import play.data.validation.Required;
import play.mvc.Before;
import play.mvc.Controller;

public class Application extends Controller {

    @Before(unless={"login", "authenticate", "logout", "register"})
    static void setConnectedUser() {
    	Logger.info("setConnectedUser ......");
        if(Security.isConnected()) {
            User user = User.find("byUsername", Security.connected()).first();
    		if (user.clubs.size()==1) {
    			user.selectedClub = user.clubs.get(0);
    			renderArgs.put("selectedClub", user.selectedClub.name);
    		}
            renderArgs.put("user", user.name);
            List<SiteMessage> siteMessages = SiteMessage.find("select m from SiteMessage m join fetch m.user u where m.messageRead=false and u.username=?", user.username).fetch();
            Logger.info("Found %d unread site messages for %s.", siteMessages.size(), user.username);
            renderArgs.put("siteMessages", siteMessages);
        }
    }

    public static void index() {
        render();
    }

    public static void authenticate(@Required String username, String password, boolean remember) throws Throwable {
    	Secure.authenticate(username, password, remember);
    }

}