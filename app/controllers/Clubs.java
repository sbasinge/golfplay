package controllers;

import java.util.List;

import models.Club;
import play.mvc.With;


@With(Secure.class)
public class Clubs extends Application {

    public static void index() {
        render();
    }

    public static void list() {
    	List<Club> clubs = Club.findAll();
        render(clubs);
    }

}
