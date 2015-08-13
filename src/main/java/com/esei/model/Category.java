package com.esei.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Version;

@Entity(name="Category")
public class Category implements Serializable {
	
	private static final long serialVersionUID = -1377888962380332407L;
	
	@Column(name="categoryId")
	private Long 				categoryId;
	private String 				categoryName;
	private List<Subcategory>   subcategories;
	private Date 				savedTime;
	private Date 				creationTime;
	private Long				version;
	
	public Category() {}

	@Id
	@SequenceGenerator(name = "CategoryIdGenerator", sequenceName = "CategorySeq") //It only takes effect for databases providing identifier generators.
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "CategoryIdGenerator")	
	//@Override
	public Long getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(Long categoryId) {
		this.categoryId = categoryId;
	}

	public String getCategoryName() {
		return categoryName;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}
	
	@OneToMany
	@JoinColumn(name="categoryId", referencedColumnName="categoryId")
	public List<Subcategory> getSubcategories() {
		return subcategories;
	}

	public void setSubcategories(List<Subcategory> subcategories) {
		this.subcategories = subcategories;
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
