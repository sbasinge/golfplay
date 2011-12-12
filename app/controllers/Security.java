package controllers;

import models.User;

public class Security extends Secure.Security {

	static boolean authenticate(String username, String password) {
		User user = User.connect(username, password);
		if (user != null) {
			if (user.clubs.size()==1) {
				user.selectedClub = user.clubs.get(0);
			}
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
	    } else if ("CourseListVisible".equals(profile)) {
	        return User.find("byUsername", connected()).<User>first().isCourseListVisible();
	    }
	    return false;
	}
	
}
