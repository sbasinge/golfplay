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
		}
		return user != null;
	}
	
	static void onDisconnected() {
		Application.index();
	}
	
	static void onAuthenticated() {
		Application.index();
	}
	
	static boolean check(String profile) {
	    if("Admin".equals(profile)) {
	        return User.find("byUsername", connected()).<User>first().hasRole(profile);
	    } else if ("ClubListVisible".equals(profile)) {
	        return User.find("byUsername", connected()).<User>first().isClubListVisible();
	    } else if ("MemberListVisible".equals(profile)) {
	        return User.find("byUsername", connected()).<User>first().isMemberRequestsVisible();
	    } else if ("CourseListVisible".equals(profile)) {
	        return User.find("byUsername", connected()).<User>first().isCourseListVisible();
	    } else if ("ScoreListVisible".equals(profile)) {
	        return User.find("byUsername", connected()).<User>first().isScoreListVisible();
	    } else if ("TeeTimeListVisible".equals(profile)) {
	        return User.find("byUsername", connected()).<User>first().isTeeTimeListVisible();
	    } else if ("TournamentListVisible".equals(profile)) {
	        return User.find("byUsername", connected()).<User>first().isTournamentListVisible();
	    } else if ("AccountVisible".equals(profile)) {
	        return User.find("byUsername", connected()).<User>first().isAccountVisible();
	    } else if ("LoggedIn".equals(profile)) {
	    	return connected() != null;
	    } else if ("LoggedOut".equals(profile)) {
	    	return connected() == null;
	    }
	    return false;
	}
	
}
