package controllers;

import java.util.ArrayList;
import java.util.List;

import models.Score;
import models.User;
import play.mvc.With;


@With(Secure.class)
public class Scores extends Application {

	
	public static void list() {
		User member = (User) renderArgs.get("member");
		List<Score> scores = new ArrayList<Score>();
		if (member != null) {
			scores = member.scores;
		} else {
			scores = Score.all().fetch();
		}
		render(scores);
	}
	
	public static void listForMember(String id) {
		User member = User.findById(id);
		List<Score> scores = new ArrayList<Score>();
		if (member != null) {
			scores = member.scores;
		}
		render(scores);
	}

}
