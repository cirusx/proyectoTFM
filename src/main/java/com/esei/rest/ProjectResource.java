package com.esei.rest;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.esei.model.Project;
import com.esei.model.Student;
import com.esei.model.Subcategory;
import com.esei.model.Teacher;

@Path("projects")
public class ProjectResource {
	
	
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
	@Path("lastprojects")
	@Produces(MediaType.APPLICATION_JSON)
	public List<Project> getLastProjects() {
		try{
		EntityManager em = EntityManagerFactorySingleton.emf.createEntityManager();
		List<Project> lastProjects;
		try{
			em.getTransaction().begin();;
			TypedQuery<Project> query = em.createQuery("SELECT o FROM Project o ORDER BY o.projectId DESC", Project.class);
			lastProjects = query.setMaxResults(4).getResultList();
			System.out.println("proyectos " + lastProjects);
			em.getTransaction().commit();
			}finally{
				em.close();
			}
		return lastProjects;
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
	@Path("create")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response createProject(Project project){
		EntityManager em = EntityManagerFactorySingleton.emf.createEntityManager();
        
        try {
            em.getTransaction().begin();
            em.persist(project);
            em.getTransaction().commit();
           
        }finally{
        	em.close();
        }
        return Response.created(null).build();  
     }
	
	@DELETE
	@Path("/{projectId}")
	public String deleteProject(@PathParam("projectId") int projectId) {
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
	public String updateProject(Project project) {
		EntityManager em = EntityManagerFactorySingleton.emf.createEntityManager();
        String out;
        try {
            em.getTransaction().begin();
            Project project2 = em.find(Project.class, project.getProjectId());
            em.merge(project2);
            em.getTransaction().commit();
            out = "Proyecto actualizado correctamente";
        }finally{
        	em.close();
        }
        return out;  
	}

}