package com.esei.model;

import java.io.Serializable;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;

import javax.persistence.Version;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity(name="Subcategory")
public class Subcategory implements Serializable {
	
	private static final long serialVersionUID = 5222559638191674192L;
	
	private Long 				subcategoryId;
	private String 				subcategoryName;
	private String 				subcategoryIcon;
	//private Category   			category;
	private Long				version;
	private List<Offer>			subcategoryOfferList;
	private List<Project>		subcategoryProjectList;
	

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
	
	@ManyToMany(mappedBy="projectSubcategoryList")
	@JsonIgnore
	public List<Project> getSubcategoryProjectList() {
		return subcategoryProjectList;
	}

	public void setSubcategoryProjectList(List<Project> subcategoryProjectList) {
		this.subcategoryProjectList = subcategoryProjectList;
	}

	@Version
	protected Long getVersion() {
		return version;
	}

	protected void setVersion(Long version) {
		this.version = version;
	}
}

