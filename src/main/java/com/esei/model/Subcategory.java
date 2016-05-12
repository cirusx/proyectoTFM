package com.esei.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Version;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity(name="Subcategory")
public class Subcategory implements Serializable {
	
	private static final long serialVersionUID = 5222559638191674192L;
	
	private Long 				subcategoryId;
	private String 				subcategoryName;
	private String 				subcategoryIcon;
	//private Category   			category;
	private Date 				savedTime;
	private Date 				creationTime;
	private Long				version;
	private List<Offer>			subcategoryOfferList;
	

	public Subcategory() {}

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	public Long getSubcategoryId() {
		return subcategoryId;
	}

	public void setSubcategoryId(Long subcategoryId) {
		this.subcategoryId = subcategoryId;
	}

	public String getSubcategoryName() {
		return subcategoryName;
	}

	public void setSubcategoryName(String subcategoryName) {
		this.subcategoryName = subcategoryName;
	}
	
	public String getSubcategoryIcon() {
		return subcategoryIcon;
	}

	public void setSubcategoryIcon(String subcategoryIcon) {
		this.subcategoryIcon = subcategoryIcon;
	}
	
	@ManyToMany(mappedBy="offerSubcategoryList")
	@JsonIgnore
	public List<Offer> getSubcategoryOfferList() {
		return subcategoryOfferList;
	}

	public void setSubcategoryOfferList(List<Offer> subcategoryOfferList) {
		this.subcategoryOfferList = subcategoryOfferList;
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
}

