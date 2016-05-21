package com.esei.rest;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.NumberFormat;
import java.util.Base64;
import java.util.Date;
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
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.glassfish.jersey.media.multipart.ContentDisposition;
import org.glassfish.jersey.media.multipart.FormDataBodyPart;
import org.glassfish.jersey.media.multipart.FormDataContentDisposition;
import org.glassfish.jersey.media.multipart.FormDataMultiPart;
import org.glassfish.jersey.media.multipart.FormDataParam;
import org.hibernate.HibernateException;

import com.esei.model.Offer;
import com.esei.model.Student;
import com.esei.model.Subcategory;
import com.esei.model.User;


@Path("offers")
public class OfferResource {


	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<Offer> getOffers() {
		try{
			EntityManager em = EntityManagerFactorySingleton.emf.createEntityManager();
			List<Offer> offers;
			try{
				em.getTransaction().begin();;
				TypedQuery<Offer> query = em.createQuery("SELECT o FROM Offer o ", Offer.class);
				offers = query.getResultList();
				System.out.println("ofertas " + offers);
				em.getTransaction().commit();
			}finally{
				em.close();
			}
			return offers;
		}catch(Exception e){
			e.printStackTrace();
			throw e;
		}
	}

	@GET
	@Path("activeoffers")
	@Produces(MediaType.APPLICATION_JSON)
	public List<Offer> getActiveOffers() {
		try{
			EntityManager em = EntityManagerFactorySingleton.emf.createEntityManager();
			List<Offer> activeOffers;
			try{
				em.getTransaction().begin();;
				TypedQuery<Offer> query = em.createQuery("SELECT o FROM Offer o WHERE o.offerClose = false", Offer.class);
				activeOffers = query.getResultList();
				System.out.println("ofertas " + activeOffers);
				em.getTransaction().commit();
			}finally{
				em.close();
			}
			return activeOffers;
		}catch(Exception e){
			e.printStackTrace();
			throw e;
		}
	}

	@GET
	@Path("recommendedoffers")
	@Produces(MediaType.APPLICATION_JSON)
	public List<Offer> getRecommendedOffers() {
		try{
			EntityManager em = EntityManagerFactorySingleton.emf.createEntityManager();
			List<Offer> recommendedOffers;
			try{
				em.getTransaction().begin();;
				TypedQuery<Offer> query = em.createQuery("SELECT o FROM Offer o WHERE o.offerClose = false AND o.offerRecommended = true", Offer.class);
				recommendedOffers = query.getResultList();
				System.out.println("ofertas " + recommendedOffers);
				em.getTransaction().commit();
			}finally{
				em.close();
			}
			return recommendedOffers;
		}catch(Exception e){
			e.printStackTrace();
			throw e;
		}
	}

	@GET
	@Path("homerecommendedoffers")
	@Produces(MediaType.APPLICATION_JSON)
	public List<Offer> getHomeRecommendedOffers() {
		try{
			EntityManager em = EntityManagerFactorySingleton.emf.createEntityManager();
			List<Offer> homeRecommendedOffers;
			try{
				em.getTransaction().begin();;
				TypedQuery<Offer> query = em.createQuery("SELECT o FROM Offer o WHERE o.offerClose = false AND o.offerHomeRecommended = true", Offer.class);
				homeRecommendedOffers = query.setMaxResults(3).getResultList();
				System.out.println("ofertas " + homeRecommendedOffers);
				em.getTransaction().commit();
			}finally{
				em.close();
			}
			return homeRecommendedOffers;
		}catch(Exception e){
			e.printStackTrace();
			throw e;
		}
	}

	@GET
	@Path("lastoffers")
	@Produces(MediaType.APPLICATION_JSON)
	public List<Offer> getLastOffers() {
		try{
			EntityManager em = EntityManagerFactorySingleton.emf.createEntityManager();
			List<Offer> lastOffers;
			try{
				em.getTransaction().begin();;
				TypedQuery<Offer> query = em.createQuery("SELECT o FROM Offer o ORDER BY o.offerId DESC", Offer.class);
				lastOffers = query.setMaxResults(3).getResultList();
				System.out.println("ofertas " + lastOffers);
				em.getTransaction().commit();
			}finally{
				em.close();
			}
			return lastOffers;
		}catch(Exception e){
			e.printStackTrace();
			throw e;
		}
	}

	@GET
	@Path("subcategories")
	@Produces(MediaType.APPLICATION_JSON)
	public List<Subcategory> getSubcategoriesByOffer(@QueryParam("offerId") Long offerId) {
		try{
			EntityManager em = EntityManagerFactorySingleton.emf.createEntityManager();
			List<Subcategory> subcategories;
			try{
				em.getTransaction().begin();
				String offerIdStr = offerId.toString();
				TypedQuery<Subcategory> query = em.createQuery("SELECT s FROM Subcategory s JOIN s.subcategoryOfferList o WHERE o.offerId="+ offerIdStr +"", Subcategory.class);
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
	@Path("users")
	@Produces(MediaType.APPLICATION_JSON)
	public List<Student> getStudentsByOffer(@QueryParam("offerId") Long offerId) {
		try{
			EntityManager em = EntityManagerFactorySingleton.emf.createEntityManager();
			List<Student> students;
			try{
				em.getTransaction().begin();
				String offerIdStr = offerId.toString();
				TypedQuery<Student> query = em.createQuery("SELECT s FROM Student s JOIN s.registerOfferList o WHERE o.offerId="+ offerIdStr +"", Student.class);
				students = query.getResultList();
				System.out.println("categorias " + students);
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
	@Path("/{offerId}")
	@Produces(MediaType.APPLICATION_JSON)
	public Offer getOffer(@PathParam("offerId") Long offerId) {
		EntityManager em = EntityManagerFactorySingleton.emf.createEntityManager();
		Offer offer;
		try{
			em.getTransaction().begin();
			offer = em.find(Offer.class, offerId);
			em.getTransaction().commit();
		}finally{
			em.close();
		}
		return offer;
	}

/*	public static void main(String[] args) {
		EntityManager em = EntityManagerFactorySingleton.emf.createEntityManager();
		Offer offer;
		User user = findUser(em, "alumno@esei.uvigo.es", "alumno");
		
		
		try{
			em.getTransaction().begin();;
			System.err.println(user.getClass());
			offer = em.find(Offer.class, 1l);
			System.err.println("oferta "+offer);
			offer.getOfferRegistrationList().add((Student) user);
			((Student) user).getRegisterOfferList().add(offer);
			em.getTransaction().commit();
			System.err.println("COMMITEADO");
		}finally{
			em.close();
		}
	}*/
	
	@POST
	@Path("/{offerId}/students")
	public Offer addStudent(@PathParam("offerId") Long offerId, @HeaderParam("Authorization") String authHeader) {
		System.err.println("añadiendo a offer "+offerId);
		EntityManager em = EntityManagerFactorySingleton.emf.createEntityManager();
		Offer offer;
		User user = requireUser(authHeader, em);
		
		try{
			em.getTransaction().begin();;
			System.err.println(user.getClass());
			offer = em.find(Offer.class, offerId);
			System.err.println("oferta "+offer);
			boolean userInList = false;
			for(Student student : offer.getOfferRegistrationList()){
				if (student.getUserId() == user.getUserId()){
					userInList = true;
				}
			}
			if (userInList == false){
				offer.getOfferRegistrationList().add((Student) user);
				((Student) user).getRegisterOfferList().add(offer);
			}
			em.getTransaction().commit();
			System.err.println("COMMITEADO");
		}finally{
			em.close();
		}
		return offer;
	}
	@POST
	@Path("create")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response createOffer(@HeaderParam("Authorization") String authHeader, Offer offer){
		System.out.println(offer.getOfferDescription());
		EntityManager em = EntityManagerFactorySingleton.emf.createEntityManager();
		try {
			em.getTransaction().begin();
			/*var datetimeStr1 = "2020/01/19 07:00";
			var datetimeStr2 = "2020/07/19 08:00";
			var datetime;*/
/*
			if(!offer.isOfferWithLimit()) {
				offer.setOfferWithLimit(false);
			}else{
				offer.setOfferWithLimit(true);
			}
			
			Date lol = offer.getOfferTimeLimit();*/
			/*if(offer.geofferTimeLimit == 1) {
				datetime = new Date(datetimeStr1);
				$scope.offer.offerTimeLimit = datetime;
			}else if($scope.offer.offerTimeLimit == 2){
				datetime = new Date(datetimeStr2);
				$scope.offer.offerTimeLimit = datetime;
			}else{
				$scope.offer.offerTimeLimit = null;
				$scope.offer.offerWithLimit = false;
			}*/
			String x = null;
			em.persist(offer);
			em.getTransaction().commit();

		}finally{
			em.close();
		}
		return Response.created(null).build();  
	}

	/*@POST
	@Path("/images")
	@Consumes(MediaType.MULTIPART_FORM_DATA)
	public Response uploadFile(
			@FormDataParam("file") InputStream uploadedInputStream,
			@FormDataParam("file") FormDataContentDisposition fileDetail) {

		String uploadedFileLocation = "c://Users/Mario/git/proyectoTFM/src/main/webapp/resources/img/" + fileDetail.getFileName();

		// save it
		writeToFile(uploadedInputStream, uploadedFileLocation);

		String output = "File uploaded to : " + uploadedFileLocation;

		return Response.status(200).entity(output).build();

	}

	// save uploaded file to new location
	private void writeToFile(InputStream uploadedInputStream,
			String uploadedFileLocation) {

		try {
			OutputStream out = new FileOutputStream(new File(
					uploadedFileLocation));
			int read = 0;
			byte[] bytes = new byte[1024];

			out = new FileOutputStream(new File(uploadedFileLocation));
			while ((read = uploadedInputStream.read(bytes)) != -1) {
				out.write(bytes, 0, read);
			}
			out.flush();
			out.close();
		} catch (IOException e) {

			e.printStackTrace();
		}

	}*/
	
	@POST
	@Path("/photo")
	@Consumes(MediaType.MULTIPART_FORM_DATA)
	public Response postImage( @HeaderParam("Authorization") String authHeader, Offer offer) throws MessagingException {
	
		
	//byte[] prueba = offer.getContent();
	//offer.setOfferImage(prueba);
		Response res = null;
		
		
	        return createOffer(authHeader, offer);
//	        postSimplePost(authHeader, offer);
	}

	@DELETE
	@Path("/{offerId}")
	public String deleteOffer(@PathParam("offerId") int offerId) {
		EntityManager em = EntityManagerFactorySingleton.emf.createEntityManager();
		String out;
		try {
			em.getTransaction().begin();
			Offer offer = em.find(Offer.class, offerId);
			em.remove(offer);
			em.getTransaction().commit();
			out = "Oferta eliminada correctamente";
		}finally{
			em.close();
		}
		return out;  
	}

	@PUT
	@Path("/{offerId}")
	@Consumes(MediaType.APPLICATION_JSON)
	public String updateOffer(Offer offer) {
		EntityManager em = EntityManagerFactorySingleton.emf.createEntityManager();
		String out;
		try {
			em.getTransaction().begin();
			Offer offer2 = em.find(Offer.class, offer.getOfferId());
			em.merge(offer2);
			em.getTransaction().commit();
			out = "Oferta actualizada correctamente";
		}finally{
			em.close();
		}
		return out;  
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


}