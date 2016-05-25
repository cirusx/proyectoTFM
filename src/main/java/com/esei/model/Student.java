package com.esei.model;

import java.util.List;


import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.PrimaryKeyJoinColumn;

import com.fasterxml.jackson.annotation.*;

@Entity(name="Student")
@DiscriminatorValue("1")
@PrimaryKeyJoinColumn(name="userId")
public class Student extends User {
	
	private static final long serialVersionUID = 8475009995080328661L;
	
	private List<Project>	userProjectList;
	private List<Offer>		registerOfferList;
	
	
	
	@OneToMany(mappedBy="projectStudent" )
	@JsonIgnore
	public List<Project> getUserProjectList() {
		return userProjectList;
	}
	
	public void setUserProjectList(List<Project> userProjectList) {
		this.userProjectList = userProjectList;
	}	
	
	@ManyToMany(mappedBy="offerRegistrationList")
	@JsonIgnore
	public List<Offer> getRegisterOfferList() {
		return registerOfferList;
	}
	
	public void setRegisterOfferList(List<Offer> registerOfferList) {
		this.registerOfferList = registerOfferList;
	}	
}
