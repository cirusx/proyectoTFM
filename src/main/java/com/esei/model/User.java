package com.esei.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.SequenceGenerator;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Version;

@Entity(name="User")
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(name="USER_TYPE", discriminatorType=DiscriminatorType.INTEGER)
public class User implements Serializable {

	private static final long serialVersionUID = -4722216552949310274L;
	
	private Long 				userId;
	private String 				userName;
	private String 				name;
	private String				password;
	private String				email;
	private boolean				enable;
	private Date 				savedTime;
	private Date 				creationTime;
	private Long				version; 
	
	public User() {}
	
	@Id
	@SequenceGenerator(name = "UserIdGenerator", sequenceName = "UserSeq") //It only takes effect for databases providing identifier generators.
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "UserIdGenerator")	
	//@Override
	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	public  String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	
	public boolean isEnable() {
		return enable;
	}

	public void setEnable(boolean enable) {
		this.enable = enable;
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
