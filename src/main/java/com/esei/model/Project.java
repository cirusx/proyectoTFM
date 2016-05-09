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
	private boolean				projectTFG;
	private Date 				savedTime;
	private Date 				creationTime;
	private Long				version; 

	public Project() {}

	@Id
	@SequenceGenerator(name = "ProjectIdGenerator", sequenceName = "ProjectSeq") //It only takes effect for databases providing identifier generators.
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "ProjectIdGenerator")	
	//@Override
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
	
	@OneToOne(fetch=FetchType.EAGER, cascade ={CascadeType.PERSIST, CascadeType.REMOVE})
	@JoinColumn(name="teacherId")
	public Teacher getProjectTeacher() {
		return projectTeacher;
	}

	public void setProjectTeacher(Teacher projectTeacher) {
		this.projectTeacher = projectTeacher;
	}
	
	@OneToOne(fetch=FetchType.EAGER, cascade ={CascadeType.PERSIST, CascadeType.REMOVE})
	//targetEntity=Student.class
	@JoinColumn(name="studentId")
	public Student getProjectStudent() {
		return projectStudent;
	}

	public void setProjectStudent(Student projectStudent) {
		this.projectStudent = projectStudent;
	}
	
	@ManyToMany(cascade = ALL, fetch=FetchType.EAGER)
	@JoinTable(
	      joinColumns={@JoinColumn(name = "Project_projectId", referencedColumnName = "projectId")},
	      inverseJoinColumns={@JoinColumn(name = "projectSubcategoryList_subcategoryId", referencedColumnName = "subcategoryId")})
	@OrderBy
	public List<Subcategory> getProjectSubcategoryList() {
		return projectSubcategoryList;
	}

	public void setProjectSubcategoryList(List<Subcategory> projectSubcategoryList) {
		this.projectSubcategoryList = projectSubcategoryList;
	}
	
	public boolean isProjectTFG() {
		return projectTFG;
	}

	public void setProjectTFG(boolean projectTFG) {
		this.projectTFG = projectTFG;
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
