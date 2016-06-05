package com.esei.model;

import java.util.List;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.PrimaryKeyJoinColumn;

import com.fasterxml.jackson.annotation.*;

@Entity(name="Teacher")
@DiscriminatorValue("Teacher")
@PrimaryKeyJoinColumn(name="userId")
public class Teacher extends User {

	private static final long serialVersionUID = 2937085374262803187L;

	@OneToMany(mappedBy="teacher", fetch=FetchType.LAZY)
	@JsonIgnore
	private List<Offer>		offerList;
	@OneToMany(mappedBy="projectTeacher", fetch=FetchType.LAZY)
	@JsonIgnore
	private List<Project>  	projectList;


	public List<Offer> getOfferList() {
		return offerList;
	}

	public void setOfferList(List<Offer> offerList) {
		this.offerList = offerList;
	}


	public List<Project> getProjectList() {
		return projectList;
	}

	public void setProjectList(List<Project> projectList) {
		this.projectList = projectList;
	}

}
