package com.esei.model;

import java.util.List;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.OneToMany;

@Entity(name="Teacher")
@DiscriminatorValue("2")
public class Teacher extends User {

	private static final long serialVersionUID = 2937085374262803187L;
	
	private List<Offer>		offerList;
	private List<Project>  	projectList;
	
	@OneToMany(mappedBy="teacher")
	public List<Offer> getOfferList() {
		return offerList;
	}
	
	public void setOfferList(List<Offer> offerList) {
		this.offerList = offerList;
	}
	
	@OneToMany(mappedBy="projectTeacher")
	public List<Project> getProjectList() {
		return projectList;
	}
	
	public void setProjectList(List<Project> projectList) {
		this.projectList = projectList;
	}

}
