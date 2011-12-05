package controllers;

import java.util.List;

import models.User;
import play.mvc.Controller;

public class Application extends Controller {

    public static void index() {
    	List<User> users = User.all().fetch();
        render(users);
    }

}