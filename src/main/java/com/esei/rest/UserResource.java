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
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.esei.model.Offer;
import com.esei.model.Project;
import com.esei.model.Teacher;
import com.esei.model.User;

@Path("users")
public class UserResource {
	
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<User> getUsers() {
		try{
		EntityManager em = EntityManagerFactorySingleton.emf.createEntityManager();
		List<User> users;
		try{
			em.getTransaction().begin();;
			TypedQuery<User> query = em.createQuery("SELECT o FROM User o ", User.class);
			users = query.getResultList();
			System.out.println("usuarioss " + users);
			em.getTransaction().commit();
			}finally{
				em.close();
			}
		return users;
		}catch(Exception e){
			e.printStackTrace();
			throw e;
		}
	}
	 
	@GET
	@Path("{userId}")
	@Produces(MediaType.APPLICATION_JSON)
	public User getUser(@PathParam("userId") Long userId) {
		EntityManager em = EntityManagerFactorySingleton.emf.createEntityManager();
		User user;
		try{
			em.getTransaction().begin();;
			user = em.find(User.class, userId);
			em.getTransaction().commit();
			}finally{
				em.close();
			}
		return user;
	}
	
	@GET
	@Path("students")
	@Produces(MediaType.APPLICATION_JSON)
	public List<User> getStudents() {
		try{
		EntityManager em = EntityManagerFactorySingleton.emf.createEntityManager();
		List<User> students;
		try{
			em.getTransaction().begin();;
			TypedQuery<User> query = em.createQuery("SELECT o FROM User o INNER JOIN Student s ON o.userId = s.userId", User.class);
			students = query.getResultList();
			System.out.println("estudiantes " + students);
			em.getTransaction().commit();
			}finally{
				em.close();
			}
		return students;
		}catch(Exception e){
			e.printStackTrace();
			throw e;
		}
	}
	
	@GET
	@Path("teachers")
	@Produces(MediaType.APPLICATION_JSON)
	public List<User> getTeachers() {
		try{
		EntityManager em = EntityManagerFactorySingleton.emf.createEntityManager();
		List<User> teachers;
		try{
			em.getTransaction().begin();;
			TypedQuery<User> query = em.createQuery("SELECT o FROM User o INNER JOIN Teacher t ON o.userId = t.userId", User.class);
			teachers = query.getResultList();
			System.out.println("profesores " + teachers);
			em.getTransaction().commit();
			}finally{
				em.close();
			}
		return teachers;
		}catch(Exception e){
			e.printStackTrace();
			throw e;
		}
	}
	
	@POST
	@Path("create")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response createProject(User user){
		EntityManager em = EntityManagerFactorySingleton.emf.createEntityManager();
        
        try {
            em.getTransaction().begin();
            em.persist(user);
            em.getTransaction().commit();
           
        }finally{
        	em.close();
        }
        return Response.created(null).build();  
     }
	
	@DELETE
	@Path("/delete/{userId}")
	public String deleteUser(@PathParam("userId") int userId) {
		EntityManager em = EntityManagerFactorySingleton.emf.createEntityManager();
        String out;
        try {
            em.getTransaction().begin();
            User user = em.find(User.class, userId);
            em.remove(user);
            em.getTransaction().commit();
            out = "Usuario eliminado correctamente";
        }finally{
        	em.close();
        }
        return out;  
	}
	
	@PUT
	@Path("/update/{userId}")
	public String updateUser(User user) {
		EntityManager em = EntityManagerFactorySingleton.emf.createEntityManager();
        String out;
        try {
            em.getTransaction().begin();
            User userModified = em.find(User.class, user.getUserId());
            em.merge(userModified);
            em.getTransaction().commit();
            out = "Usuario actualizado correctamente";
        }finally{
        	em.close();
        }
        return out;  
	}

}