package controllers;

import java.util.List;

import models.Club;
import play.Logger;
import play.mvc.With;
import controllers.CRUD.ObjectType;


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
        ObjectType type = ObjectType.get(getControllerClass());

        render(type, club);
    }

    public static void delete(Long id) {
    	Club club = Club.findById(id);
    	club.delete();
        list();
    }

    public static void save(Club club) {
    	Logger.info("Called save club with %s", club);
        if(validation.hasErrors()) {
            render("@index", club);
        }
    	club.save();
    	flash.success("club_saved");
    	list();
    }
    
    public static void add() {
    	Club club = new Club();
    	render(club);
    }
    
    public static void cancel() {
    	index();
    }
}
