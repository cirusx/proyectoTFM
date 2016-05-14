package com.esei.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Version;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.JoinColumn;

import static javax.persistence.CascadeType.ALL;

import javax.persistence.OrderBy;


@Entity(name="Offer")
public class Offer implements Serializable{

	private static final long serialVersionUID = -7829559240923524678L;
	
	private Long 				offerId;
	private String 				offerName;
	private String				offerDescription;
	private String				offerImage;
	private boolean             offerWithLimit;
	private Date                offerTimeLimit;
	private List<Student>		offerRegistrationList;
	private List<Subcategory>	offerSubcategoryList;
	private boolean				offerClose;
	private boolean				offerRecommended;
	private boolean				offerHomeRecommended;
	private Date 				savedTime;
	private Date 				creationTime;
	private Long				version; 
	private Teacher				teacher;

	

	public Offer() {}

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	public Long getOfferId() {
		return offerId;
	}

	public void setOfferId(Long offerId) {
		this.offerId = offerId;
	}

	public String getOfferName() {
		return offerName;
	}

	public void setOfferName(String offerName) {
		this.offerName = offerName;
	}

	public String getOfferDescription() {
		return offerDescription;
	}

	public void setOfferDescription(String offerDescription) {
		this.offerDescription = offerDescription;
	}
	
	public String getOfferImage() {
		return offerImage;
	}

	public void setOfferImage(String offerImage) {
		this.offerImage = offerImage;
	}

	public boolean isOfferWithLimit() {
		return offerWithLimit;
	}

	public void setOfferWithLimit(boolean offerWithLimit) {
		this.offerWithLimit = offerWithLimit;
	}

	public Date getOfferTimeLimit() {
		return offerTimeLimit;
	}

	public void setOfferTimeLimit(Date offerTimeLimit) {
		this.offerTimeLimit = offerTimeLimit;
	}


	@ManyToMany(cascade = ALL, fetch=FetchType.LAZY)

	@JoinTable(
	      joinColumns={@JoinColumn(name = "Offer_offerId", referencedColumnName = "offerId")},
	      inverseJoinColumns={@JoinColumn(name = "offerRegistrationList_userId", referencedColumnName = "userId")})
	@JsonIgnore
	public List<Student> getOfferRegistrationList() {
		return offerRegistrationList;
	}

	public void setOfferRegistrationList(List<Student> offerRegistrationList) {
		this.offerRegistrationList = offerRegistrationList;
	}
	
	@ManyToMany(cascade = ALL, fetch=FetchType.LAZY)
	@JoinTable(
	      joinColumns={@JoinColumn(name = "Offer_offerId", referencedColumnName = "offerId")},
	      inverseJoinColumns={@JoinColumn(name = "offerSubcategoryList_subcategoryId", referencedColumnName = "subcategoryId")})
	@JsonIgnore
	public List<Subcategory> getOfferSubcategoryList() {
		return offerSubcategoryList;
	}

	public void setOfferSubcategoryList(List<Subcategory> offerSubcategoryList) {
		this.offerSubcategoryList = offerSubcategoryList;
	}
	
	@Column(nullable=false, columnDefinition="boolean default false")
	public boolean isOfferClose() {
		return offerClose;
	}

	public void setOfferClose(boolean offerClose) {
		this.offerClose = offerClose;
	}
	
	@Column(nullable=false, columnDefinition="boolean default false")
	public boolean isOfferRecommended() {
		return offerRecommended;
	}

	public void setOfferRecommended(boolean offerRecommended) {
		this.offerRecommended = offerRecommended;
	}
	
	@Column(nullable=false, columnDefinition="boolean default false")
	public boolean isOfferHomeRecommended() {
		return offerHomeRecommended;
	}

	public void setOfferHomeRecommended(boolean offerHomeRecommended) {
		this.offerHomeRecommended = offerHomeRecommended;
	}

	@Temporal(TemporalType.TIMESTAMP)
	//@Override
	public Date getSavedTime() {
		return savedTime;
	}

	//@Override
	public void setSavedTime(Date savedTime) {
		this.savedTime = savedTime;
	}

	@Temporal(TemporalType.TIMESTAMP)
	//@Override
	public Date getCreationTime() {
		return creationTime;
	}

	//@Override
	public void setCreationTime(Date creationTime) {
		this.creationTime = creationTime;
	}

	@Version
	protected Long getVersion() {
		return version;
	}

	protected void setVersion(Long version) {
		this.version = version;
	}
	
	@ManyToOne
	public Teacher getTeacher() {
		return teacher;
	}

	public void setTeacher(Teacher teacher) {
		this.teacher = teacher;
	}
}