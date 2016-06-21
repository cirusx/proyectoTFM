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
import com.esei.model.Category;
import com.esei.model.Subcategory;

@Path("categories")
public class CategoryResource {


	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<Category> getCategories() {
		try{
			EntityManager em = EntityManagerFactorySingleton.createEntityManager();
			List<Category> categories;
			try{
				em.getTransaction().begin();;
				TypedQuery<Category> query = em.createQuery("SELECT o FROM Category o ", Category.class);
				categories = query.getResultList();
				System.out.println("categorias " + categories);
				em.getTransaction().commit();
			}finally{
				em.close();
			}
			return categories;
		}catch(Exception e){
			e.printStackTrace();
			throw e;
		}
	}

	@GET
	@Path("subcategories")
	@Produces(MediaType.APPLICATION_JSON)
	public List<Subcategory> getSubcategoriesByCategory(@QueryParam("categoryId") Long categoryId) {
		try{
			EntityManager em = EntityManagerFactorySingleton.createEntityManager();
			List<Subcategory> subcategories;
			try{
				em.getTransaction().begin();
				String categoryIdStr = categoryId.toString();
				TypedQuery<Subcategory> query = em.createQuery("SELECT s FROM Subcategory s JOIN s.category o WHERE o.categoryId="+ categoryIdStr +"", Subcategory.class);
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
	@Path("/{categoryId}")
	@Produces(MediaType.APPLICATION_JSON)
	public Category getSubcategory(@PathParam("categoryId") Long categoryId) {
		EntityManager em = EntityManagerFactorySingleton.createEntityManager();
		Category category;
		try{
			em.getTransaction().begin();;
			category = em.find(Category.class, categoryId);
			em.getTransaction().commit();
		}finally{
			em.close();
		}
		return category;
	}

	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response createCategory(Category category){
		EntityManager em = EntityManagerFactorySingleton.createEntityManager();
		try {
			em.getTransaction().begin();
			em.persist(category);
			em.getTransaction().commit();

		}finally{
			em.close();
		}
		return Response.created(null).build();  
	}

	@DELETE
	@Path("/{categoryId}")
	public String deleteCategory(@PathParam("categoryId") int categoryId) {
		EntityManager em = EntityManagerFactorySingleton.createEntityManager();
		String out;
		try {
			em.getTransaction().begin();
			Category category = em.find(Category.class, categoryId);
			em.remove(category);
			em.getTransaction().commit();
			out = "Oferta eliminada correctamente";
		}finally{
			em.close();
		}
		return out;  
	}

	@PUT
	@Path("/{categoryId}")
	public String updateCategory(Category category) {
		EntityManager em = EntityManagerFactorySingleton.createEntityManager();
		String out;
		try {
			em.getTransaction().begin();
			Category category2 = em.find(Category.class, category.getCategoryId());
			em.merge(category2);
			em.getTransaction().commit();
			out = "Oferta actualizada correctamente";
		}finally{
			em.close();
		}
		return out;  
	}

}