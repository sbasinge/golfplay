package controllers;

import java.util.List;

import models.Course;
import models.GeoLocation;
import models.TeeSet;
import play.data.validation.Valid;
import play.mvc.With;
import enums.State;
import enums.TeeType;


@With(Secure.class)
public class Courses extends Application {

	public static void list() {
		List<Course> courses = Course.findAll();
		render(courses);
	}

	public static void cancel() {
		list();
	}

    public static void edit(Long id) {
    	Course course = Course.findById(id);
    	State[] states = State.values();
    	render(course, states);
    }

    public static void view(Long id) {
    	Course course = Course.findById(id);
        render(course);
    }

    public static void delete(Long id) {
    	Course course = Course.findById(id);
    	course.delete();
        list();
    }

    public static void save(@Valid Course course) {
    	String[] courseRatings = params.getAll("courseRating");
    	String[] slopeRatings = params.getAll("slopeRating");
    	String[] teeTypes = params.getAll("teeType");
    	
    	//when editing you must lookup the teeSets and assign new values.
    	// when adding, you add the one's that have values.
    	int i =0;
    	for (String teeTypeStr: teeTypes) {
    		TeeType teeType = TeeType.valueOf(teeTypeStr);
    		double courseRating = Double.parseDouble(courseRatings[i]);
    		int slopeRating = Integer.parseInt(slopeRatings[i]);
    		if (courseRating <= 0 && slopeRating <= 0) {
    			//see if the course has a matching teeset and remove
    			int j = 0;
    			for (TeeSet teeSet: course.teeSets) {
    				if (teeSet.teeType.equals(teeType)) {
    					course.teeSets.remove(j);
    					break;
    				}
    				j++;
    			}
    		} else {
    			//now see if the course has a matching teeSet and add if not
    			int j = 0;
    			TeeSet foundTeeSet = null;
    			for (TeeSet teeSet: course.teeSets) {
    				if (teeSet.teeType.equals(teeType)) {
    					foundTeeSet = teeSet;
    					break;
    				}
    			}
    			if (foundTeeSet == null) {
    				TeeSet teeSet = new TeeSet();
    	    		teeSet.teeType = teeType;
    	    		teeSet.courseRating = courseRating;
    	    		teeSet.slopeRating = slopeRating;
    	    		teeSet.course = course;
    	    		course.teeSets.add(teeSet);
    				
    			} else {
    				//update
    				foundTeeSet.courseRating = courseRating;
    				foundTeeSet.slopeRating = slopeRating;
    			}
    		}
    		i++;
    	}
    	if (course.facility != null && course.facility.address != null && course.facility.address.geoLocation == null) {
    		course.facility.address.geoLocation = new GeoLocation();
    	}
    	course.save();
    	list();
    }
    
    public static void add() {
    	Course course = new Course();
    	for (TeeType teeType : TeeType.values()) {
    		TeeSet teeSet = new TeeSet();
    		teeSet.teeType = teeType;
    		course.teeSets.add(teeSet);
    	}
    	State[] states = State.values();
    	render(course, states);
    }

}
