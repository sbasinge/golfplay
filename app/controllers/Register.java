package controllers;

import models.Club;
import models.MembershipRequest;
import models.User;
import notifiers.Notifier;
import play.Logger;
import play.data.validation.Valid;
import play.libs.F.Promise;
import play.mvc.Controller;

public class Register extends Controller {
    public static void index() {
    	User user = new User();
        render(user);
    }

    public static void save(@Valid User user, String confirmpassword) {
    	Logger.info("Saving new user: %s", user.username);
        validation.required(confirmpassword);
        validation.equals(confirmpassword, user.password).message("Your password doesn't match");
        if(validation.hasErrors()) {
            render("@index", user, confirmpassword);
        }
    	
    	//TODO when and where does the newly registered user join clubs?
    	Club defaultClub = Club.find("byName", "CMGC").first();
    	user.selectedClub = defaultClub;
    	user.create();
    	sendRegistrationRequest(user);
        session.put("user", user.username);
        flash.success("registration_registered");
        Application.index();
    }
    
    private static void sendRegistrationRequest(User user) {
    	Club club = user.selectedClub;
    	Logger.info("Newly registered user asking for membership into club %s", club.name);
    	if (club != null) {
    		MembershipRequest membershipRequest = new MembershipRequest();
    		membershipRequest.user = user;
    		membershipRequest.club = club;
    		membershipRequest.save();
    		club.membershipRequests.add(membershipRequest);
    		Promise promise = Notifier.instance().registrationCompleted(membershipRequest);
    	}
    }

}
