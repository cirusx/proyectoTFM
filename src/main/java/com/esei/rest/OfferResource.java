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

@Path("offers")
public class OfferResource {
	
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<Offer> getOffers() {
		try{
		/*List<Offer> offers = new ArrayList<Offer>();
		Offer offer = new Offer();
		offer.setOfferName("proyecto interesante");
		offers.add(offer);
		return offers;*/
		EntityManager em = EntityManagerFactorySingleton.emf.createEntityManager();
		List<Offer> offers;
		try{
			em.getTransaction().begin();;
			TypedQuery<Offer> query = em.createQuery("SELECT o FROM Offer o", Offer.class);
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
	@Path("{offerId}")
	@Produces(MediaType.APPLICATION_JSON)
	public Offer getOffer(@PathParam("offerId") Long offerId) {
		EntityManager em = EntityManagerFactorySingleton.emf.createEntityManager();
		Offer offer;
		try{
			em.getTransaction().begin();;
			offer = em.find(Offer.class, offerId);
			em.getTransaction().commit();
			}finally{
				em.close();
			}
		return offer;
	}
	
	@POST
	@Path("create")
	//@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response createOffer(Offer offer){
		EntityManager em = EntityManagerFactorySingleton.emf.createEntityManager();
        
        try {
            em.getTransaction().begin();
            em.persist(offer);
            em.getTransaction().commit();
           
        }finally{
        	em.close();
        }
        return Response.created(null).build();  
     }
	
	@DELETE
	@Path("/delete/{offerId}")
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
	@Path("/update/{offerId}")
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

}
