package com.esei.model;

import java.util.List;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;

@Entity(name="Student")
@DiscriminatorValue("1")
public class Student extends User {
	
	private static final long serialVersionUID = 8475009995080328661L;
	
	private Project 		TFG;
	private Project 		TFM;
	private List<Offer>		registerOfferList;
	
	public Project getTFG() {
		return TFG;
	}
	
	public void setTFG(Project tFG) {
		TFG = tFG;
	}
	
	public Project getTFM() {
		return TFM;
	}
	
	public void setTFM(Project tFM) {
		TFM = tFM;
	}
	
	@ManyToMany(mappedBy="offerRegistrationList")
	public List<Offer> getRegisterOfferList() {
		return registerOfferList;
	}
	
	public void setRegisterOfferList(List<Offer> registerOfferList) {
		this.registerOfferList = registerOfferList;
	}	
}
