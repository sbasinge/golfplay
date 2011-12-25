package controllers;

import models.User;
import play.data.validation.Valid;

public class Accounts extends Application {
	public static int IMAGE_WIDTH = 80;
	
	public static void index() {
		User user = (User) renderArgs.get("user");
		render(user);
	}
	
	public static void save(@Valid User user) {
        if(validation.hasErrors()) {
            render("@index", user);
        }
        if (user.image.getUUID() != null) {
        	 play.libs.Images.resize(user.image.getFile(), user.image.getFile(), IMAGE_WIDTH, -1);
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

    public static void getPicture() {
		User user = (User) renderArgs.get("user");
		if (user.image.getUUID() != null) {
			response.setContentTypeIfNotSet(user.image.type());
			renderBinary(user.image.get());
		}
    }
    
    public static void resetPicture() {
		User user = (User) renderArgs.get("user");
		user.image = null;
		user.save();
		index();
    }

}
