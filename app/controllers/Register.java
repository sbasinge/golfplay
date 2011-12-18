package controllers;

import models.User;
import play.Logger;
import play.data.validation.Valid;
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
    	flash.success("registration_registered");
    	user.create();
        session.put("user", user.username);
        Application.index();
    }

}
