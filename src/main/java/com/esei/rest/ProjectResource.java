package com.esei.rest;

import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import javax.mail.MessagingException;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.esei.model.Offer;
import com.esei.model.Project;
import com.esei.model.Student;
import com.esei.model.Subcategory;
import com.esei.model.Teacher;
import com.esei.model.User;

@Path("projects")
public class ProjectResource {

	@Path("/pdf/draft/{projectId}")
	@GET
	public Response getDraftPDF(@PathParam("projectId") Long projectId) throws Exception {
		EntityManager em = EntityManagerFactorySingleton.emf.createEntityManager();
		Project project= em.find(Project.class, projectId);

		return Response
				.ok()
				.type("application/pdf")
				.entity(project.getProjectDraft()) // Assumes document is a byte array in the domain object.
				.build();
	}

	@Path("/pdf/documentation/{projectId}")
	@GET
	public Response getDocumentationPDF(@PathParam("projectId") Long projectId) throws Exception {
		EntityManager em = EntityManagerFactorySingleton.emf.createEntityManager();
		Project project= em.find(Project.class, projectId);

		return Response
				.ok()
				.type("application/pdf")
				.entity(project.getProjectDocumentation()) // Assumes document is a byte array in the domain object.
				.build();
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<Project> getProjects() {
		try{
			EntityManager em = EntityManagerFactorySingleton.emf.createEntityManager();
			List<Project> projects;
			try{
				em.getTransaction().begin();;
				TypedQuery<Project> query = em.createQuery("SELECT o FROM Project o ", Project.class);
				projects = query.getResultList();
				System.out.println("proyectos " + projects);
				em.getTransaction().commit();
			}finally{
				em.close();
			}
			return projects;
		}catch(Exception e){
			e.printStackTrace();
			throw e;
		}
	}

	@GET
	@Path("subcategories")
	@Produces(MediaType.APPLICATION_JSON)
	public List<Subcategory> getSubcategoriesByProject(@QueryParam("projectId") Long projectId) {
		try{
			EntityManager em = EntityManagerFactorySingleton.emf.createEntityManager();
			List<Subcategory> subcategories;
			try{
				em.getTransaction().begin();
				String projectIdStr = projectId.toString();
				TypedQuery<Subcategory> query = em.createQuery("SELECT s FROM Subcategory s JOIN s.subcategoryProjectList p WHERE p.projectId="+ projectIdStr +"", Subcategory.class);
				subcategories = query.getResultList();
				System.out.println("categorias " + subcategories);
				em.getTransaction().commit();
			}finally{
				em.close();
			}
			return subcategories;
		}catch(Exception e){
			e.printStackTrace();
			throw e;
		}
	}

	@GET
	@Path("links")
	@Produces(MediaType.APPLICATION_JSON)
	public List<String> getLinksByProject(@QueryParam("projectId") Long projectId) {
		try{
			EntityManager em = EntityManagerFactorySingleton.emf.createEntityManager();
			List<String> links;
			Project project;
			try{
				em.getTransaction().begin();
				String projectIdStr = projectId.toString();
				TypedQuery<Project> query = em.createQuery("SELECT p FROM Project p WHERE p.projectId="+ projectIdStr +"", Project.class);
				project = query.getSingleResult();
				links = project.getProjectLinks();
				System.out.println("categorias " + links);
				em.getTransaction().commit();
			}finally{
				em.close();
			}
			return links;
		}catch(Exception e){
			e.printStackTrace();
			throw e;
		}
	}

	@GET
	@Path("student")
	@Produces(MediaType.APPLICATION_JSON)
	public Student getStudentByProject(@QueryParam("projectId") Long projectId) {
		try{
			EntityManager em = EntityManagerFactorySingleton.emf.createEntityManager();
			Student student;
			try{
				em.getTransaction().begin();
				String projectIdStr = projectId.toString();
				TypedQuery<Student> query = em.createQuery("SELECT s FROM Student s JOIN s.userProjectList p WHERE p.projectId="+ projectIdStr +"", Student.class);
				student = query.getSingleResult();
				System.out.println("alumno " + student);
				em.getTransaction().commit();
			}finally{
				em.close();
			}
			return student;
		}catch(Exception e){
			e.printStackTrace();
			throw e;
		}
	}

	@GET
	@Path("teacher")
	@Produces(MediaType.APPLICATION_JSON)
	public Teacher getTeacherByProject(@QueryParam("projectId") Long projectId) {
		try{
			EntityManager em = EntityManagerFactorySingleton.emf.createEntityManager();
			Teacher teacher;
			try{
				em.getTransaction().begin();
				String projectIdStr = projectId.toString();
				TypedQuery<Teacher> query = em.createQuery("SELECT t FROM Teacher t JOIN t.projectList p WHERE p.projectId="+ projectIdStr +"", Teacher.class);
				teacher = query.getSingleResult();
				System.out.println("profesor " + teacher);
				em.getTransaction().commit();
			}finally{
				em.close();
			}
			return teacher;
		}catch(Exception e){
			e.printStackTrace();
			throw e;
		}
	}

	@GET
	@Path("{projectId}")
	@Produces(MediaType.APPLICATION_JSON)
	public Project getProject(@PathParam("projectId") Long projectId) {
		EntityManager em = EntityManagerFactorySingleton.emf.createEntityManager();
		Project project;
		try{
			em.getTransaction().begin();;
			project = em.find(Project.class, projectId);
			em.getTransaction().commit();
		}finally{
			em.close();
		}
		return project;
	}

	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response createProject(@HeaderParam("Authorization") String authHeader, Project project){
		EntityManager em = EntityManagerFactorySingleton.emf.createEntityManager();
		User user = requireUser(authHeader, em);
		Teacher teacher = (Teacher) user;
		Long userId = project.getProjectStudent().getUserId();
		Student student = (Student) getStudent(userId, em);
		List<Subcategory> subcategoryIds = project.getProjectSubcategoryList();
		List<Subcategory> subcategories = new ArrayList<Subcategory>();
		for (Subcategory subcategoryIdObject : subcategoryIds) {
			Long subcategoryId = subcategoryIdObject.getSubcategoryId();
			Subcategory subcategory = getSubcategory(subcategoryId, em);
			subcategory.getSubcategoryProjectList().add(project);
			subcategories.add(subcategory);
		}
		try {
			em.getTransaction().begin();
			teacher.getProjectList().add(project);
			student.getUserProjectList().add(project);
			project.setProjectTeacher(teacher);
			project.setProjectStudent(student);
			project.setProjectSubcategoryList(subcategories);
			em.persist(project);
			em.getTransaction().commit();
		}finally{
			em.close();
		}
		return Response.created(null).build();  
	}

	@POST
	@Path("project")
	@Consumes(MediaType.MULTIPART_FORM_DATA)
	public Response postProject( @HeaderParam("Authorization") String authHeader, Project project) throws MessagingException {
		return createProject(authHeader, project);

	}

	@DELETE
	@Path("/{projectId}")
	public String deleteProject(@HeaderParam("Authorization") String authHeader, @PathParam("projectId") Long projectId) {
		EntityManager em = EntityManagerFactorySingleton.emf.createEntityManager();
		String out;
		try {
			em.getTransaction().begin();
			Project project = em.find(Project.class, projectId);
			em.remove(project);
			em.getTransaction().commit();
			out = "Proyecto eliminado correctamente";
		}finally{
			em.close();
		}
		return out;  
	}

	@PUT
	@Path("/{projectId}")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response editProject(@HeaderParam("Authorization") String authHeader, Project project){
		EntityManager em = EntityManagerFactorySingleton.emf.createEntityManager();
		Teacher teacher = (Teacher) requireUser(authHeader, em);

		try {
			em.getTransaction().begin();	  
			Project editProject = em.find(Project.class, project.getProjectId());
			em.merge(editProject);
			em.getTransaction().commit();

		}finally{
			em.close();
		}
		return Response.created(null).build();  
	}

	@PUT
	@Path("/{projectId}")
	@Consumes(MediaType.MULTIPART_FORM_DATA)
	public Response putProject(@HeaderParam("Authorization") String authHeader, Project project) throws MessagingException {
		return editProject(authHeader, project);
	}

	private User requireUser(String authHeader, EntityManager em) {
		String authString = new String(Base64.getDecoder().decode(authHeader.replace("Basic ","").getBytes()));
		String login = authString.split(":")[0];
		String pass = authString.split(":")[1];
		try {
			return findUser(em, login, pass);
		} catch(NoResultException e) {
			e.printStackTrace();
			throw new SecurityException("user is not correct");
		}
	}

	private static User findUser(EntityManager em, String login, String pass) {
		return em.createQuery("SELECT u FROM User u WHERE u.email = '"+ login +"' and u.password='"+pass+"'", User.class).getSingleResult();
	}

	private User getStudent(Long userId, EntityManager em) {
		return em.find(User.class, userId);
	}

	private Subcategory getSubcategory(Long subcategoryId, EntityManager em) {
		return em.find(Subcategory.class, subcategoryId);
	}
}