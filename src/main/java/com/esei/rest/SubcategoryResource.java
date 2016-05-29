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
import com.esei.model.Subcategory;


@Path("subcategories")
public class SubcategoryResource {
	
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<Subcategory> getSubcategories() {
		try{
		EntityManager em = EntityManagerFactorySingleton.emf.createEntityManager();
		List<Subcategory> subcategories;
		try{
			em.getTransaction().begin();;
			TypedQuery<Subcategory> query = em.createQuery("SELECT o FROM Subcategory o ", Subcategory.class);
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
	@Path("/{subcategoryId}")
	@Produces(MediaType.APPLICATION_JSON)
	public Subcategory getSubcategory(@PathParam("subcategoryId") Long subcategoryId) {
		EntityManager em = EntityManagerFactorySingleton.emf.createEntityManager();
		Subcategory subcategory;
		try{
			em.getTransaction().begin();;
			subcategory = em.find(Subcategory.class, subcategoryId);
			em.getTransaction().commit();
			}finally{
				em.close();
			}
		return subcategory;
	}
	
	@POST
	@Path("create")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response createSubcategory(Subcategory subcategory){
		EntityManager em = EntityManagerFactorySingleton.emf.createEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(subcategory);
            em.getTransaction().commit();
           
        }finally{
        	em.close();
        }
        return Response.created(null).build();  
     }
	
	@DELETE
	@Path("/{subcategoryId}")
	public String deleteSubcategory(@PathParam("subcategoryId") int subcategoryId) {
		EntityManager em = EntityManagerFactorySingleton.emf.createEntityManager();
        String out;
        try {
            em.getTransaction().begin();
            Subcategory subcategory = em.find(Subcategory.class, subcategoryId);
            em.remove(subcategory);
            em.getTransaction().commit();
            out = "Oferta eliminada correctamente";
        }finally{
        	em.close();
        }
        return out;  
	}
	
	@PUT
	@Path("/{subcategoryId}")
	public String updateSubcategory(Subcategory subcategory) {
		EntityManager em = EntityManagerFactorySingleton.emf.createEntityManager();
        String out;
        try {
            em.getTransaction().begin();
            Offer subcategory2 = em.find(Offer.class, subcategory.getSubcategoryId());
            em.merge(subcategory2);
            em.getTransaction().commit();
            out = "Oferta actualizada correctamente";
        }finally{
        	em.close();
        }
        return out;  
	}

}