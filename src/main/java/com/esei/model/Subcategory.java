package com.esei.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Version;

@Entity(name="Subcategory")
public class Subcategory implements Serializable {
	
	private static final long serialVersionUID = 5222559638191674192L;
	
	private Long 				subcategoryId;
	private String 				subcategoryName;
	//private Category   			category;
	private Date 				savedTime;
	private Date 				creationTime;
	private Long				version;
	
	public Subcategory() {}

	@Id
	@SequenceGenerator(name = "SubcategoryIdGenerator", sequenceName = "SubcategorySeq") //It only takes effect for databases providing identifier generators.
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "SubcategoryIdGenerator")	
	
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

