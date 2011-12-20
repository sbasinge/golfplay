package controllers;

import java.util.List;

import models.Course;
import play.data.validation.Valid;
import play.mvc.With;


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
        render(course);
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
    	course.save();
    }
    
    public static void add() {
    	Course course = new Course();
    	render(course);
    }

}
