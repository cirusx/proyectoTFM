package com.esei.model;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.Serializable;
import java.net.URLConnection;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinTable;
import javax.persistence.Lob;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;

import javax.persistence.Transient;
import javax.persistence.Version;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.JoinColumn;

import static javax.persistence.CascadeType.ALL;




@Entity(name="Offer")
public class Offer implements Serializable{

	private static final long serialVersionUID = -7829559240923524678L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long 				offerId;
	private String 				offerName;
	private String				offerDescription;
	private boolean             offerWithLimit;
	private Date                offerTimeLimit;

	@ManyToMany(cascade = ALL, fetch=FetchType.LAZY)
	@JoinTable(
	      joinColumns={@JoinColumn(name = "Offer_offerId", referencedColumnName = "offerId")},
	      inverseJoinColumns={@JoinColumn(name = "offerRegistrationList_userId", referencedColumnName = "userId")})
	@JsonIgnore
	private List<Student>		offerRegistrationList;

	
	@ManyToMany(cascade = ALL, fetch=FetchType.LAZY)
	@JoinTable(
	      joinColumns={@JoinColumn(name = "Offer_offerId", referencedColumnName = "offerId")},
	      inverseJoinColumns={@JoinColumn(name = "offerSubcategoryList_subcategoryId", referencedColumnName = "subcategoryId")})
	@JsonIgnore
	private List<Subcategory>	offerSubcategoryList;
	
	@Column(nullable=false, columnDefinition="boolean default false")
	private boolean				offerClose;
	
	@Column(nullable=false, columnDefinition="boolean default false")
	private boolean				offerRecommended;

	@Column(nullable=false, columnDefinition="boolean default false")
	private boolean				offerHomeRecommended;

	@Version
	private Long				version; 
	
	@ManyToOne
	private Teacher				teacher;

	@Lob
	@Column(length=100000000)
	private byte[]				offerImage;

	@Transient
	private String contentMime;

	public Offer() {}

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


	public List<Student> getOfferRegistrationList() {
		return offerRegistrationList;
	}

	public void setOfferRegistrationList(List<Student> offerRegistrationList) {
		this.offerRegistrationList = offerRegistrationList;
	}
	
	public List<Subcategory> getOfferSubcategoryList() {
		return offerSubcategoryList;
	}

	public void setOfferSubcategoryList(List<Subcategory> offerSubcategoryList) {
		this.offerSubcategoryList = offerSubcategoryList;
	}
	
	public boolean isOfferClose() {
		return offerClose;
	}

	public void setOfferClose(boolean offerClose) {
		this.offerClose = offerClose;
	}
	
	public boolean isOfferRecommended() {
		return offerRecommended;
	}

	public void setOfferRecommended(boolean offerRecommended) {
		this.offerRecommended = offerRecommended;
	}
	
	public boolean isOfferHomeRecommended() {
		return offerHomeRecommended;
	}

	public void setOfferHomeRecommended(boolean offerHomeRecommended) {
		this.offerHomeRecommended = offerHomeRecommended;
	}

	protected Long getVersion() {
		return version;
	}

	protected void setVersion(Long version) {
		this.version = version;
	}
	
	public Teacher getTeacher() {
		return teacher;
	}

	public void setTeacher(Teacher teacher) {
		this.teacher = teacher;
	}
	
	public void setOfferImage(byte[] offerImage) {
		this.offerImage = offerImage;
	}

	public byte[] getOfferImage() {
		return offerImage;
	}

	public String getContentMime() throws IOException {
		if (this.contentMime!=null){
			return this.contentMime;
		}
		ByteArrayInputStream bais = new ByteArrayInputStream(this.offerImage);
		return URLConnection.guessContentTypeFromStream(bais);
				/*"png";*/
		
	}
	
	public void setContentMime(String contentMime) {
		this.contentMime = contentMime;
	}
}