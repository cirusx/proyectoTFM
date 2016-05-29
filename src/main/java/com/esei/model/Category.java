package com.esei.model;

import java.io.Serializable;

import java.util.List;


import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;

import javax.persistence.Version;

@Entity(name="Category")
public class Category implements Serializable {
	
	private static final long serialVersionUID = -1377888962380332407L;
	
	
	private Long 				categoryId;
	private String 				categoryName;
	private List<Subcategory>   subcategories;
	private Long				version;
	
	public Category() {}

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
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

	@Version
	protected Long getVersion() {
		return version;
	}

	protected void setVersion(Long version) {
		this.version = version;
	}
}
