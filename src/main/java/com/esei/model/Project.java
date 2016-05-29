package com.esei.model;

import static javax.persistence.CascadeType.ALL;

import java.io.Serializable;


import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.Lob;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;

import javax.persistence.Version;

import com.fasterxml.jackson.annotation.*;

@Entity(name="Project")
public class Project implements Serializable{

	private static final long serialVersionUID = -542889196789595703L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long 				projectId;
	private String 				projectName;
	private String              projectCode;
	private String 				projectCareer;
	private String 				projectYear;
	@Lob
	@Column(length=100000000)
	private byte[]				projectDraft;
	@Lob
	@Column(length=100000000)
	private byte[]				projectDocumentation;
	@OneToOne(fetch=FetchType.LAZY, cascade ={CascadeType.PERSIST, CascadeType.REMOVE})
	@JoinColumn(name="teacherId")
	@JsonIgnore
	private Teacher				projectTeacher;
	@OneToOne(fetch=FetchType.LAZY, cascade ={CascadeType.PERSIST, CascadeType.REMOVE})
	@JoinColumn(name="studentId")
	@JsonIgnore
	private Student				projectStudent;
	@ManyToMany(cascade = ALL, fetch=FetchType.LAZY)
	@JoinTable(
	      joinColumns={@JoinColumn(name = "Project_projectId", referencedColumnName = "projectId")},
	      inverseJoinColumns={@JoinColumn(name = "projectSubcategoryList_subcategoryId", referencedColumnName = "subcategoryId")})
	@JsonIgnore
	private List<Subcategory>	projectSubcategoryList;
	@Column
    @ElementCollection(targetClass=String.class)
	@JsonIgnore
	private List<String>        projectLinks;
	@Version
	private Long				version; 

	public Project() {}

	public Long getProjectId() {
		return projectId;
	}

	public void setProjectId(Long projectId) {
		this.projectId = projectId;
	}

	public String getProjectName() {
		return projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}
	
	public String getProjectCode() {
		return projectCode;
	}

	public void setProjectCode(String projectCode) {
		this.projectCode = projectCode;
	}

	public String getProjectCareer() {
		return projectCareer;
	}

	public void setProjectCareer(String projectCareer) {
		this.projectCareer = projectCareer;
	}
	
	public String getProjectYear() {
		return projectYear;
	}

	public void setProjectYear(String projectYear) {
		this.projectYear = projectYear;
	}
	
	public byte[] getProjectDraft() {
		return projectDraft;
	}

	public void setProjectDraft(byte[] projectDraft) {
		this.projectDraft = projectDraft;
	}
	
	public byte[] getProjectDocumentation() {
		return projectDocumentation;
	}

	public void setProjectDocumentation(byte[] projectDocumentation) {
		this.projectDocumentation = projectDocumentation;
	}
	
	
	public Teacher getProjectTeacher() {
		return projectTeacher;
	}

	public void setProjectTeacher(Teacher projectTeacher) {
		this.projectTeacher = projectTeacher;
	}
	
	
	public Student getProjectStudent() {
		return projectStudent;
	}

	public void setProjectStudent(Student projectStudent) {
		this.projectStudent = projectStudent;
	}
	
	public List<Subcategory> getProjectSubcategoryList() {
		return projectSubcategoryList;
	}

	public void setProjectSubcategoryList(List<Subcategory> projectSubcategoryList) {
		this.projectSubcategoryList = projectSubcategoryList;
	}

	public List<String> getProjectLinks() {
		return projectLinks;
	}

	public void setProjectLinks(List<String> projectLinks) {
		this.projectLinks = projectLinks;
	}

	protected Long getVersion() {
		return version;
	}

	protected void setVersion(Long version) {
		this.version = version;
	}
}
