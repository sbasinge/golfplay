package controllers;

import models.User;
import play.Logger;

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
	
	static void onDisconnected() {
		Application.index();
	}
	
	static void onAuthenticated() {
		flash.success("Successful login");
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
