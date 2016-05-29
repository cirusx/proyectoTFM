package com.esei.rest;



import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.ext.Provider;


import com.esei.model.Project;
import com.esei.model.Student;


@Provider
public class ProjectReader extends MultipartMessageBodyReader<Project> {



	
	private Project project;

	@Override
	protected void init() {
		project = new Project();
	}

	@Override
	protected void add(String name, byte[] bs){

		switch(name) {
		case "name":
			project.setProjectName(new String(bs));
			break;
		case "code":
			project.setProjectCode(new String(bs));
			break;
		case "career":
			project.setProjectCareer(new String(bs));
			break;        
		case "year":
			project.setProjectYear(new String(bs));
			break;
		case "student":
			Student student = new Student();
			String userIdStr = new String (bs);
			long userId = Long.valueOf(userIdStr).longValue();
			student.setUserId(userId);
			project.setProjectStudent(student);
			break;
		case "links":
			List<String> links = new ArrayList<String>();
			String linksString = new String (bs);
			String[] linkArray = linksString.split(",");
			for ( String link : linkArray ) {
                  links.add(link);
			}
			project.setProjectLinks(links);
			break;
		case "documentation":
			project.setProjectDocumentation(bs);
			break; 
		case "draft":
			project.setProjectDraft(bs);
			break;
		}
	}

	@Override
	protected Project build() {
		return this.project;
	}
}