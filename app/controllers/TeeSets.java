package controllers;

import java.util.List;

import models.Course;
import models.TeeSet;
import play.Logger;
import play.mvc.With;


@With(Secure.class)
public class TeeSets extends CRUD {

	
	public static void selectList(Long id) {
		Logger.info("Getting teeSets for %d",id);
		Course course = Course.findById(id);
		List<TeeSet> teeSets = course.teeSets;
		render(teeSets);
	}
}
