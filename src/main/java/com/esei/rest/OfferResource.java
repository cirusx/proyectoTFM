package com.esei.rest;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.NumberFormat;
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
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.glassfish.jersey.media.multipart.ContentDisposition;
import org.glassfish.jersey.media.multipart.FormDataBodyPart;
import org.glassfish.jersey.media.multipart.FormDataContentDisposition;
import org.glassfish.jersey.media.multipart.FormDataMultiPart;
import org.glassfish.jersey.media.multipart.FormDataParam;


import com.esei.model.Offer;
import com.esei.model.Subcategory;

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
	@Path("/{offerId}")
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
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response createOffer(Offer offer){
		System.out.println(offer.getOfferDescription());
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
	
	@POST
	@Path("/images")
	@Consumes({MediaType.MULTIPART_FORM_DATA})
	public Response uploadPdfFile(  @FormDataParam("file") InputStream fileInputStream,
	                                @FormDataParam("file") FormDataContentDisposition fileMetaData) throws Exception
	{
	    String UPLOAD_PATH = "c://Users/Cirusx/workspace/proyectoTFM/src/main/webapp/resources/img/";
	    try
	    {
	        int read = 0;
	        byte[] bytes = new byte[1024];
	 
	        OutputStream out = new FileOutputStream(new File(UPLOAD_PATH + fileMetaData.getFileName()));
	        while ((read = fileInputStream.read(bytes)) != -1) 
	        {
	            out.write(bytes, 0, read);
	        }
	        out.flush();
	        out.close();
	    } catch (IOException e) 
	    {
	        throw new WebApplicationException("Error while uploading file. Please try again !!");
	    }
	    return Response.ok("Data uploaded successfully !!").build();
	}
	
	@POST
	@Path("/images2")
	@Consumes(MediaType.MULTIPART_FORM_DATA)
	public Response uploadFile(
		@FormDataParam("file") InputStream uploadedInputStream,
		@FormDataParam("file") FormDataContentDisposition fileDetail) {

		String uploadedFileLocation = "c://Users/Cirusx/workspace/proyectoTFM/src/main/webapp/resources/img/" + fileDetail.getFileName();

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

	}
	
	private static final String SERVER_UPLOAD_LOCATION_FOLDER = "c://Users/Cirusx/workspace/proyectoTFM/src/main/webapp/resources/img/";

	    @POST
	    @Path("/images3")
	    @Consumes(MediaType.MULTIPART_FORM_DATA)
	    public Response uploadFile(FormDataMultiPart form) {

	         FormDataBodyPart filePart = form.getField("file");
	         ContentDisposition headerOfFilePart =  filePart.getContentDisposition();
	         InputStream fileInputStream = filePart.getValueAs(InputStream.class);
	         String filePath = SERVER_UPLOAD_LOCATION_FOLDER + headerOfFilePart.getFileName();
	        saveFile(fileInputStream, filePath);
	        String output = "File saved to server location using FormDataMultiPart : " + filePath;
	        return Response.status(200).entity(output).build();
	    }

	    private void saveFile(InputStream uploadedInputStream, String serverLocation) {
	
	        try {
	
	            OutputStream outpuStream = new FileOutputStream(new File(serverLocation));
	
	            int read = 0;
	            byte[] bytes = new byte[1024];
	            outpuStream = new FileOutputStream(new File(serverLocation));
	
	            while ((read = uploadedInputStream.read(bytes)) != -1) {
	
	                outpuStream.write(bytes, 0, read);
	            }

	            outpuStream.flush();
	            outpuStream.close();
	            uploadedInputStream.close();
	
	        } catch (IOException e) {

	            e.printStackTrace();
	
	        }
	
	    }
	  
	    @POST
	    @Path("/images4")
	    @Consumes(MediaType.MULTIPART_FORM_DATA)
	    @Produces("text/html")
	    public Response uploadFiles(
	        @FormDataParam("file") InputStream fileInputString,
	        @FormDataParam("file") FormDataContentDisposition fileInputDetails) {
	    
	      String fileLocation = SERVER_UPLOAD_LOCATION_FOLDER + fileInputDetails.getFileName();
	      String status = null;
	      NumberFormat myFormat = NumberFormat.getInstance();
	      myFormat.setGroupingUsed(true);
	       
	      // Save the file 
	      try {
	       OutputStream out = new FileOutputStream(new File(fileLocation));
	       byte[] buffer = new byte[1024];
	       int bytes = 0;
	       long file_size = 0; 
	       while ((bytes = fileInputString.read(buffer)) != -1) {
	        out.write(buffer, 0, bytes);
	        file_size += bytes;
	       }
	       out.flush();  
	       out.close();
	               
	       /*logger.info(String.format("Inside uploadFile==> fileName: %s,  fileSize: %s", 
	            fileInputDetails.getFileName(), myFormat.format(file_size)));*/
	               
	       status = "File has been uploaded to:" + fileLocation 
	                   + ", size: " + myFormat.format(file_size) + " bytes";
	      } catch (IOException ex) {
	        /*logger.error("Unable to save file: "  + fileLocation);*/
	        ex.printStackTrace();
	      }
	   
	      return Response.status(200).entity(status).build();
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

}