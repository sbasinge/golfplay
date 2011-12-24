package controllers;

import models.User;
import play.data.validation.Valid;

public class Accounts extends Application {

	public static void index() {
		User user = (User) renderArgs.get("user");
		render(user);
	}
	
	public static void save(@Valid User user) {
        if(validation.hasErrors()) {
            render("@index", user);
        }
        user.save();
        index();
	}

    public static void cancel() {
    	index();
    }
    
    
    public static void changePassword() {
    	index();
    }

}
