package controllers;

import java.util.ArrayList;
import java.util.List;

import models.TeeTime;
import play.Logger;
import play.data.validation.Valid;
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
    	Logger.info("TeeTime to save is %s",teeTime);
    	teeTime.save();
    }

    public static void cancel() {
    	list();
    }

}
