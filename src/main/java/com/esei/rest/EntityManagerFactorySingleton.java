package com.esei.rest;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class EntityManagerFactorySingleton {
	public static EntityManagerFactory emf = Persistence.createEntityManagerFactory("proyectoTFM");	
	
	public static EntityManager createEntityManager() {
		return emf.createEntityManager();
	}
}
