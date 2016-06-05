package com.esei.model;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.Serializable;
import java.net.URLConnection;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Transient;
import javax.persistence.Version;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity(name="Subcategory")
public class Subcategory implements Serializable {
	
	private static final long serialVersionUID = 5222559638191674192L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long 				subcategoryId;
	private String 				subcategoryName;
	@Lob
	@Column(length=100000000)
	private byte[]				subcategoryIcon;
	@ManyToOne			
	private Category   			category;
	@ManyToMany(mappedBy="offerSubcategoryList", fetch=FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
	@JsonIgnore
	private List<Offer>			subcategoryOfferList;
	@ManyToMany(mappedBy="projectSubcategoryList", fetch=FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
	@JsonIgnore
	private List<Project>		subcategoryProjectList;
	@Transient
	private String contentMime;
	@Version
	private Long				version;
	
	public Subcategory() {}

	
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
	
	public byte[] getSubcategoryIcon() {
		return subcategoryIcon;
	}

	public void setSubcategoryIcon(byte[] subcategoryIcon) {
		this.subcategoryIcon = subcategoryIcon;
	}
	
	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}
	
	public List<Offer> getSubcategoryOfferList() {
		return subcategoryOfferList;
	}

	public void setSubcategoryOfferList(List<Offer> subcategoryOfferList) {
		this.subcategoryOfferList = subcategoryOfferList;
	}
	
	
	public List<Project> getSubcategoryProjectList() {
		return subcategoryProjectList;
	}

	public void setSubcategoryProjectList(List<Project> subcategoryProjectList) {
		this.subcategoryProjectList = subcategoryProjectList;
	}

	public void setContentMime(String contentMime) {
		this.contentMime = contentMime;
	}
	
	public String getContentMime() throws IOException {
		if (this.contentMime!=null){
			return this.contentMime;
		}
		if(subcategoryIcon != null){
			ByteArrayInputStream bais = new ByteArrayInputStream(this.subcategoryIcon);
			return URLConnection.guessContentTypeFromStream(bais);
		}else return "";	
	}
	
	protected Long getVersion() {
		return version;
	}

	protected void setVersion(Long version) {
		this.version = version;
	}
}

