package controllers;

import java.util.ArrayList;
import java.util.List;

import models.Club;
import models.Course;
import models.Score;
import models.TeeTime;
import models.TeeTimeParticipant;
import notifiers.Notifier;
import play.Logger;
import play.data.validation.Valid;
import play.libs.F.Promise;
import play.mvc.With;


@With(Secure.class)
public class TeeTimes extends Application {

	public static void list() {
//		String selectedClub = (String) renderArgs.get("selectedClub");
		//TODO should get teetimes for current club only.
		List<TeeTime> teeTimes = new ArrayList<TeeTime>();
		teeTimes = TeeTime.all().fetch();
		render(teeTimes);
	}
	
    public static void edit(Long id) {
    	TeeTime teeTime = TeeTime.findById(id);
        render(teeTime);
    }

    public static void view(Long id) {
    	TeeTime teeTime = TeeTime.findById(id);
    	Logger.info("Found teeTime %s for %s", teeTime, id);
        render(teeTime);
    }

    public static void delete(Long id) {
    	TeeTime teeTime = TeeTime.findById(id);
    	teeTime.delete();
        list();
    }

    public static void save(@Valid TeeTime teeTime) {
    	String[] grossScores = params.getAll("grossScore");
    	String[] adjustedScores = params.getAll("adjustedScore");
    	String[] participants = params.getAll("participant");
    	boolean adding = false;
    	if (teeTime.id == null) {
    		adding = true;
    	}
    	Logger.info("TeeTime to save is %s",teeTime);
    	int i=0;
    	if (participants != null) {
    		for (String participantId: participants) {
    			TeeTimeParticipant participant = TeeTimeParticipant.findById(Long.parseLong(participants[i]));
    			if (participant != null) {
    				if (participant.score == null) {
    					Score score = new Score();
    					score.adjustedScore = Integer.parseInt(adjustedScores[i]);
    					score.date = teeTime.date;
    					score.grossScore = Integer.parseInt(grossScores[i]);
    					score.teeSet = teeTime.teeSet;
    					score.teeTime = teeTime;
    					score.user = participant.user;
    					participant.score = score;
    					sendScoreUpdatedMessage(score);
    					score.save();
    				} else {
    					Score score = participant.score;
    					score.adjustedScore = Integer.parseInt(adjustedScores[i]);
    					score.date = teeTime.date;
    					score.grossScore = Integer.parseInt(grossScores[i]);
    					sendScoreUpdatedMessage(score);
    					score.save();
    				}
    			}
    			i++;
    		}
    	}
    	teeTime.save();
    	if (adding) {
    		sendTeeTimeAdddedMessage(teeTime, null);	//TODO figure out where to get current selected club and/or assign all teetimes to a club
    	}
    	list();
    }

    public static void cancel() {
    	list();
    }

    public static void reserveSpot() {
    	//sendTeeTimeUpdatedMessage
    }
    
    
    public static void add() {
    	TeeTime teeTime = new TeeTime();
    	List<Course> courses = Course.findAll();
    	render(teeTime, courses);
    }

    private static Promise sendTeeTimeAdddedMessage(TeeTime teeTime, Club club) {
    	Notifier.instance().teetimeAdded(teeTime, club);
		return null;
    }

    private static Promise sendTeeTimeUpdatedMessage(TeeTimeParticipant participant, Club club) {
    	Notifier.instance().teetimeUpdated(participant, club);
		return null;
    }

    private static Promise sendScoreUpdatedMessage(Score score) {
    	Notifier.instance().scoreUpdated(score);
		return null;
    }

}
