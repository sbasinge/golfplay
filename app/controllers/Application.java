package controllers;

import models.User;
import play.mvc.Controller;

public class Application extends Controller {

    public static void index() {
		String username = session.get("username");
		User user = User.find("byUserName", username).first();
		if (user.clubs.size()==1) {
			user.selectedClub = user.clubs.get(0);
			renderArgs.put("selectedClub", user.selectedClub.name);
		}
		renderArgs.put("user", user.name);
        render();
    }

}