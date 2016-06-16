package com.esei.rest;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import javax.ws.rs.ext.Provider;
import com.esei.model.Project;
import com.esei.model.Student;
import com.esei.model.Subcategory;
import com.esei.model.Teacher;

@Provider
public class ProjectReader extends MultipartMessageBodyReader<Project> {

	static final Charset UTF8_CHARSET = Charset.forName("UTF-8");
	private Project project;

	@Override
	protected void init() {
		project = new Project();
	}

	@Override
	protected void add(String name, byte[] bs){

		switch(name) {
		case "name":
			project.setProjectName(new String(bs,UTF8_CHARSET));
			break;
		case "code":
			project.setProjectCode(new String(bs,UTF8_CHARSET));
			break;
		case "career":
			project.setProjectCareer(new String(bs,UTF8_CHARSET));
			break;        
		case "year":
			project.setProjectYear(new String(bs));
			break;
		case "id":
			String projectIdStr = new String(bs);
			Long projectId = Long.valueOf(projectIdStr);
			project.setProjectId(projectId);
			break;
		case "student":
			Student student = new Student();
			String userIdStr = new String (bs);
			long userId = Long.valueOf(userIdStr).longValue();
			student.setUserId(userId);
			project.setProjectStudent(student);
			break;
		case "teacher":
			Teacher teacher = new Teacher();
			String teacherIdStr = new String (bs);
			long teacherId = Long.valueOf(teacherIdStr).longValue();
			teacher.setUserId(teacherId);
			project.setProjectTeacher(teacher);
			break;
		case "subcategories":
			String subcategoryIds = new String(bs);
			String[] subcategoryIdsSplit = subcategoryIds.split(",");
			List<Subcategory> subcategories = new ArrayList<Subcategory>();  
			for (String subcategoryIdStr : subcategoryIdsSplit) {
				Subcategory subcategory = new Subcategory();
				if (subcategoryIdStr.compareTo("") != 0 ) {
					long subcategoryId = Long.valueOf(subcategoryIdStr).longValue();
					subcategory.setSubcategoryId(subcategoryId);
					subcategories.add(subcategory);
				}
			}
			project.setProjectSubcategoryList(subcategories);
			break;
		case "links":
			List<String> links = new ArrayList<String>();
			String linksString = new String (bs,UTF8_CHARSET);
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