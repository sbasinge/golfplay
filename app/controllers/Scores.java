package controllers;

import java.util.ArrayList;
import java.util.List;

import models.Score;
import models.User;
import play.data.validation.Valid;
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

    public static void edit(Long id) {
    	Score score = Score.findById(id);
        render(score);
    }

    public static void view(Long id) {
    	Score score = Score.findById(id);
        render(score);
    }

    public static void delete(Long id) {
    	Score score = Score.findById(id);
    	score.delete();
        list();
    }

    public static void save(@Valid Score score) {
    	score.save();
    }

    public static void cancel() {
    	list();
    }

}
