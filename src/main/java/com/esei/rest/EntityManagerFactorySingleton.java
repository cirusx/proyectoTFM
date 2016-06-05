package com.esei.rest;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class EntityManagerFactorySingleton {
	public static EntityManagerFactory emf = Persistence.createEntityManagerFactory("proyectoTFM");	
}
