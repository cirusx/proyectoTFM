package com.esei.model;

import static javax.persistence.CascadeType.ALL;

import java.io.Serializable;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;
import javax.persistence.OrderBy;
import javax.persistence.SequenceGenerator;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Version;

import com.fasterxml.jackson.annotation.*;

@Entity(name="Project")
public class Project implements Serializable{

	private static final long serialVersionUID = -542889196789595703L;
	
	private Long 				projectId;
	private String 				projectName;
	private String 				projectCareer;
	private int 				projectYear;
	private byte[]				projectDraft;
	private byte[]				projectDocumentation;
	private Teacher				projectTeacher;
	private Student				projectStudent;
	private List<Subcategory>	projectSubcategoryList;
	private Long				version; 

	public Project() {}

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
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
	
	public String getProjectCareer() {
		return projectCareer;
	}

	public void setProjectCareer(String projectCareer) {
		this.projectCareer = projectCareer;
	}
	
	public int getProjectYear() {
		return projectYear;
	}

	public void setProjectYear(int projectYear) {
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
	
	@OneToOne(fetch=FetchType.LAZY, cascade ={CascadeType.PERSIST, CascadeType.REMOVE})
	@JoinColumn(name="teacherId")
	@JsonIgnore
	public Teacher getProjectTeacher() {
		return projectTeacher;
	}

	public void setProjectTeacher(Teacher projectTeacher) {
		this.projectTeacher = projectTeacher;
	}
	
	@OneToOne(fetch=FetchType.LAZY, cascade ={CascadeType.PERSIST, CascadeType.REMOVE})
	//targetEntity=Student.class
	@JoinColumn(name="studentId")
	@JsonIgnore
	public Student getProjectStudent() {
		return projectStudent;
	}

	public void setProjectStudent(Student projectStudent) {
		this.projectStudent = projectStudent;
	}
	
	@ManyToMany(cascade = ALL, fetch=FetchType.LAZY)
	@JoinTable(
	      joinColumns={@JoinColumn(name = "Project_projectId", referencedColumnName = "projectId")},
	      inverseJoinColumns={@JoinColumn(name = "projectSubcategoryList_subcategoryId", referencedColumnName = "subcategoryId")})
	@JsonIgnore
	public List<Subcategory> getProjectSubcategoryList() {
		return projectSubcategoryList;
	}

	public void setProjectSubcategoryList(List<Subcategory> projectSubcategoryList) {
		this.projectSubcategoryList = projectSubcategoryList;
	}

	@Version
	protected Long getVersion() {
		return version;
	}

	protected void setVersion(Long version) {
		this.version = version;
	}
}
