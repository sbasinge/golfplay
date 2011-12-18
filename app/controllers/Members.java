package controllers;

import java.util.List;

import models.Club;
import models.User;
import play.mvc.With;
import controllers.CRUD.ObjectType;

@With(Secure.class)
public class Members extends Application {

    public static void index() {
    	list();
    }

	public static void list() {
		Club club = Club.find("byName", renderArgs.get("selectedClub")).first();
		if (club != null) {
			List<User> members = club.members;
			render(members);
		}
	}

	public static void listForClub(Long id) {
		Club club = Club.findById(id);
		if (club != null) {
			List<User> members = club.members;
			render(members);
		}
	}

    public static void calculateHandicap(String id) {
    	User member = User.findById(id);
    	member.calculateHandicap();
        index();
    }

    public static void view(String id) {
    	User member = User.findById(id);
        ObjectType type = ObjectType.get(getControllerClass());
        renderArgs.put("member", member);
        Scores.list();
    }

}
