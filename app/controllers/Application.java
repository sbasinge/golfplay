package controllers;

import models.User;
import play.Logger;
import play.data.validation.Required;
import play.libs.Crypto;
import play.mvc.Before;
import play.mvc.Controller;
import play.mvc.Http;

public class Application extends Controller {

    @Before(unless={"login", "authenticate", "logout", "register"})
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

    public static void login() throws Throwable {
        Http.Cookie remember = request.cookies.get("rememberme");
        if(remember != null && remember.value.indexOf("-") > 0) {
            String sign = remember.value.substring(0, remember.value.indexOf("-"));
            String username = remember.value.substring(remember.value.indexOf("-") + 1);
            if(Crypto.sign(username).equals(sign)) {
                session.put("username", username);
                redirectToOriginalURL();
            }
        }
        flash.keep("url");
        Application.index();
    }

    static void redirectToOriginalURL() throws Throwable {
//    	onAuthenticated();
        String url = flash.get("url");
        if(url == null) {
            url = "/";
        }
        redirect(url);
    }
    
    public static void index() {
        render();
    }

    public static void authenticate(@Required String username, String password, boolean remember) throws Throwable {
    	Secure.authenticate(username, password, remember);
    }

	static boolean authenticate(String username, String password) {
		User user = User.connect(username, password);
		if (user != null) {
			if (user.clubs.size()==1) {
				user.selectedClub = user.clubs.get(0);
				renderArgs.put("selectedClub", user.selectedClub.name);
			}
			renderArgs.put("user", user.name);
			Logger.info("User logged in: %s", user.name);
		} else {
			//add some errors for the page to display?
		}
		return user != null;
	}

    public static void registerNewUser(User user) {
        render();
    }

}