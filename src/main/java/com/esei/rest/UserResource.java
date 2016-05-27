package com.esei.rest;

import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

import javax.inject.Singleton;
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
import com.esei.model.Student;
import com.esei.model.Subcategory;
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
		//return users;
		return new ArrayList<>();
		}catch(Exception e){
			e.printStackTrace();
			throw e;
		}
	}
	
	@GET
	@Path("/search/{userId}")
	@Produces(MediaType.APPLICATION_JSON)
	public User getUserById(@PathParam("userId") Long userId) {
		EntityManager em = EntityManagerFactorySingleton.emf.createEntityManager();
		User user;
		try{
			em.getTransaction().begin();
			user = em.find(User.class, userId);
			em.getTransaction().commit();
		}finally{
			em.close();
		}
		return user;
	}
	 
	@GET
	@Path("{email}")
	@Produces(MediaType.APPLICATION_JSON)
	public User getUser(@PathParam("email") String email, @HeaderParam("Authorization") String authHeader) {
		EntityManager em = EntityManagerFactorySingleton.emf.createEntityManager();
		User user = requireUser(authHeader, em);
		return user;
	}
	
	private User requireUser(String authHeader, EntityManager em) {
		String authString = new String(Base64.getDecoder().decode(authHeader.replace("Basic ","").getBytes()));
		String login = authString.split(":")[0];
		String pass = authString.split(":")[1];
		try {
			return em.createQuery("SELECT u FROM User u WHERE u.email = '"+ login +"' and u.password='"+pass+"'", User.class).getSingleResult();
		} catch(NoResultException e) {
			e.printStackTrace();
			throw new SecurityException("user is not correct");
		}
	}
	
	@GET
	@Path("check")
	@Produces(MediaType.APPLICATION_JSON)
	public User getUserByEmail(@QueryParam("email") String email) {
		try{
			EntityManager em = EntityManagerFactorySingleton.emf.createEntityManager();
			User user;
			try{
				em.getTransaction().begin();
				TypedQuery<User> query = em.createQuery("SELECT u FROM User  u WHERE u.email='"+ email +"'", User.class);
				user = query.getSingleResult();
				System.out.println("usuario " + user.getEmail());
				em.getTransaction().commit();
			}finally{
				em.close();
			}
			return user;
		}catch(Exception e){
			e.printStackTrace();
			throw e;
		}
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
	@Path("myregistrations")
	@Produces(MediaType.APPLICATION_JSON)
	public List<Offer> getRegistrations(@HeaderParam("Authorization") String authHeader) {
		try{
			EntityManager em = EntityManagerFactorySingleton.emf.createEntityManager();
			Student student = (Student) requireUser(authHeader, em);
			List<Offer> myregistrations;
			try{
				em.getTransaction().begin();;
				String userId = student.getUserId().toString();
				TypedQuery<Offer> query = em.createQuery("SELECT o FROM Offer o JOIN o.offerRegistrationList s WHERE s.userId="+ userId +"", Offer.class);
				myregistrations = query.getResultList();
				System.out.println("ofertas " + myregistrations);
				em.getTransaction().commit();
			}finally{
				em.close();
			}
			return myregistrations;
		}catch(Exception e){
			e.printStackTrace();
			throw e;
		}
	}
	
	@GET
	@Path("myoffers")
	@Produces(MediaType.APPLICATION_JSON)
	public List<Offer> getMyOffers(@HeaderParam("Authorization") String authHeader) {
		try{
			EntityManager em = EntityManagerFactorySingleton.emf.createEntityManager();
			Teacher teacher = (Teacher) requireUser(authHeader, em);
			List<Offer> myOffers;
			try{
				em.getTransaction().begin();;
				String userId = teacher.getUserId().toString();
				TypedQuery<Offer> query = em.createQuery("SELECT o FROM Offer o JOIN o.teacher s WHERE s.userId="+ userId +"", Offer.class);
				myOffers = query.getResultList();
				System.out.println("ofertas " + myOffers);
				em.getTransaction().commit();
			}finally{
				em.close();
			}
			return myOffers;
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
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response createUser(User user){
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
	@Path("/{userId}")
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
	@Path("/{userId}")
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

