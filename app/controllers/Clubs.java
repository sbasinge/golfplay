package controllers;

import models.User;
import play.mvc.Before;
import play.mvc.With;


@With(Secure.class)
public class Clubs extends CRUD {

    @Before
    static void setConnectedUser() {
        if(Security.isConnected()) {
            User user = User.find("byUsername", Security.connected()).first();
            renderArgs.put("user", user.name);
        }
    }
 
    public static void index() {
        render();
    }

}
