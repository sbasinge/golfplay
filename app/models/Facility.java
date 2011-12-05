package models;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import net.sf.oval.constraint.Size;
import play.data.validation.Required;


@Entity
public class Facility extends BaseEntity {

	@Column
	@Required
	public String name;
	
//    @NotNull
    @OneToOne(cascade={CascadeType.ALL})
    public Address address;

    @Size(max=20)
    public String phone;
    
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	public List<Course> courses = new ArrayList<Course>();
    
    public Facility() {};
    
    public Facility(String name, Address address) {this.name = name; this.address = address;};

    public Facility(String name, Address address, Course course) {
    	this(name,address); 
    	addCourse(course);
    };
    
	public void addCourse(Course course) {
		course.facility = this;
		courses.add(course);
	}
	
	public void removeCourse(Course course) {
		course.facility = null;
		courses.remove(course);
	}
}
