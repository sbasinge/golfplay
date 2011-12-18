package controllers;

import java.util.List;

import models.SiteMessage;
import models.User;
import play.Logger;
import play.mvc.Controller;
import play.mvc.With;


@With(Secure.class)
public class SiteMessages extends Controller {

//    @Before
//    static void setConnectedUser() {
//    	Logger.info("setConnectedUser ......");
//        if(Security.isConnected()) {
//            User user = User.find("byUsername", Security.connected()).first();
//    		if (user.clubs.size()==1) {
//    			user.selectedClub = user.clubs.get(0);
//    			renderArgs.put("selectedClub", user.selectedClub.name);
//    		}
//            renderArgs.put("user", user.name);
//            List<SiteMessage> siteMessages = SiteMessage.find("select m from SiteMessage m join fetch m.user u where m.messageRead=false and u.username=?", user.username).fetch();
//            Logger.info("Found %d unread site messages for %s.", siteMessages.size(), user.username);
//            renderArgs.put("siteMessages", siteMessages);
//        }
//    }

	public static void list(Long id) {
		SiteMessage message = SiteMessage.findById(id);
		message.messageRead = true;
		message.save();
        User user = User.find("byUsername", Security.connected()).first();
        List<SiteMessage> siteMessages = SiteMessage.find("select m from SiteMessage m join fetch m.user u where m.messageRead=false and u.username=?", user.username).fetch();
        Logger.info("Found %d unread site messages for %s.", siteMessages.size(), user.username);
        renderArgs.put("siteMessages", siteMessages);
		render();
	}
}
