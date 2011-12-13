package controllers;

import java.util.List;

import models.Club;
import play.mvc.With;


@With(Secure.class)
public class Clubs extends Application {

    public static void index() {
    	list();
    }

    public static void list() {
    	List<Club> clubs = Club.findAll();
        render(clubs);
    }

    public static void edit(Long id) {
    	Club club = Club.findById(id);
        render(club);
    }

    public static void view(Long id) {
    	Club club = Club.findById(id);
        render(club);
    }

}
