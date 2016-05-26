package com.esei.model;


import java.io.Serializable;

import javax.persistence.Access;
import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;

import javax.persistence.Version;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

@Entity(name="User")
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(name="userType", discriminatorType=DiscriminatorType.STRING)
public class User implements Serializable {

	private static final long serialVersionUID = -4722216552949310274L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long 				userId;
	private String 				name;
	@JsonIgnore
	private String				password;
	private String				email;
	private boolean				enable;
	@Version
	private Long				version;

	public User() {}
	
	
	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	@JsonIgnore
	public String getPassword() {
		return password;
	}
	
	@JsonProperty
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
	
	public String getUserType() {
		return this.getClass().getSimpleName();
	}

	
	protected Long getVersion() {
		return version;
	}

	protected void setVersion(Long version) {
		this.version = version;
	}

}
