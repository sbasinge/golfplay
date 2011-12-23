package models;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import play.data.validation.Required;

@Entity
public class Course extends BaseEntity {

	@Column
	@Required
	public String name;

	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "FACILITY_ID", nullable = true)
	public Facility facility;

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval=true, mappedBy="course")
	public List<TeeSet> teeSets = new ArrayList<TeeSet>();
	
	public Course() {};
	public Course(String name) { this.name=name;};
	public Course(String name, TeeSet tees) { 
		this(name);
		addTeeSet(tees);
	};
	
	public void addTeeSet(TeeSet teeSet) {
		teeSet.course = this;
		teeSets.add(teeSet);
	}
	@Override
	public String toString() {
		return "Course [id="+id+", name=" + name + "]";
	}
}
