package controllers;

import java.util.ArrayList;
import java.util.List;

import models.Tournament;
import play.data.validation.Valid;
import play.mvc.With;


@With(Secure.class)
public class Tournaments extends Application {
	
	public static void list() {
		List<Tournament> tournaments = new ArrayList<Tournament>();
		tournaments = Tournament.all().fetch();
		render(tournaments);
	}
	
    public static void edit(Long id) {
    	Tournament tournament = Tournament.findById(id);
        render(tournament);
    }

    public static void view(Long id) {
    	Tournament tournament = Tournament.findById(id);
        render(tournament);
    }

    public static void delete(Long id) {
    	Tournament tournament = Tournament.findById(id);
    	tournament.delete();
        list();
    }

    public static void save(@Valid Tournament tournament) {
    	tournament.save();
    }

    public static void cancel() {
    	list();
    }

}
