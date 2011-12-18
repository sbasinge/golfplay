package controllers;

import models.User;
import play.Logger;
import play.data.validation.Required;
import play.libs.Crypto;
import play.mvc.Http;

public class Security extends Secure.Security {

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
	
    public static void authenticate(@Required String username, String password, boolean remember) throws Throwable {
    	Logger.info("Authenticating...");
        // Check tokens
        Boolean allowed = false;
        allowed = (Boolean)authenticate(username, password);
        if(validation.hasErrors() || !allowed) {
        	Logger.warn("Errors logging in", validation.errorsMap());
            flash.keep("url");
            flash.error("secure.error");
            flash.error("invalid id or password");
            params.flash();
            login();
        }
        // Mark user as connected
        session.put("username", username);
        // Remember if needed
        if(remember) {
            response.setCookie("rememberme", Crypto.sign(username) + "-" + username, "30d");
        }
        // Redirect to the original URL (or /)
        redirectToOriginalURL();
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
    	onAuthenticated();
        String url = flash.get("url");
        if(url == null) {
            url = "/";
        }
        redirect(url);
    }

	static void onDisconnected() {
		Application.index();
	}
	
	static void onAuthenticated() {
		flash.error("Successful login");
		Application.index();
	}
	
	static boolean check(String profile) {
		String connected = connected();
		Logger.info("Connected is %s", connected);
		User user =  User.find("byUsername", connected).first();
		if (user == null) {
			return false;
		}
	    if("Admin".equals(profile)) {
	        return user.hasRole(profile);
	    } else if ("ClubListVisible".equals(profile)) {
	        return user.isClubListVisible();
	    } else if ("MemberListVisible".equals(profile)) {
	        return user.isMemberRequestsVisible();
	    } else if ("CourseListVisible".equals(profile)) {
	        return user.isCourseListVisible();
	    } else if ("ScoreListVisible".equals(profile)) {
	        return user.isScoreListVisible();
	    } else if ("TeeTimeListVisible".equals(profile)) {
	        return user.isTeeTimeListVisible();
	    } else if ("TournamentListVisible".equals(profile)) {
	        return user.isTournamentListVisible();
	    } else if ("AccountVisible".equals(profile)) {
	        return user.isAccountVisible();
	    }
	    return false;
	}
	
}
