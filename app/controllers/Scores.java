package controllers;

import java.util.ArrayList;
import java.util.List;

import models.Course;
import models.Score;
import models.User;
import notifiers.Notifier;
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
    	Notifier.instance().scoreUpdated(score);
        list();
    }

    public static void save(@Valid Score score) {
    	if (score.user==null)
    		validation.addError("score.user.username","Required");
    	if (score.teeSet==null)
    		validation.addError("score.teeSet.id","Required");
        if(validation.hasErrors()) {
        	List<User> members = User.findAll(); //TODO should be for the current club
        	List<Course> courses = Course.findAll();
            render("@add", score, courses, members);
        }
    	flash.success("score_saved");
    	score.save();
    	sendScoreUpdatedMessage(score);
    	flash.success("score_saved");
    	list();
    }

    public static void cancel() {
    	list();
    }

    public static void cancel(String clubId) {
    	list();
    }

    public static void add() {
    	//TODO need to sort members and courses so they are easier to find.
    	List<User> members = User.findAll(); //TODO should be for the current club
    	List<Course> courses = Course.findAll();
    	Score score = new Score();
    	render(score, courses, members);
    }
    
    private static void sendScoreUpdatedMessage(Score score) {
    	Notifier.instance().scoreUpdated(score);
    }
}
