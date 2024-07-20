package com.ravehalcajpa.service.impl;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

public class JPADAOFactory {
	private static final String PERSISTENCE_UNIT_NAME = "bdjpa";

	private static EntityManagerFactory entityManagerFactory;
	private static EntityManager entityManager;

	public static EntityManager createEntityManager() {

		if (entityManager == null || !entityManager.isOpen()) {
			entityManagerFactory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
			entityManager = entityManagerFactory.createEntityManager();
		}
		return entityManager;
	}

	public static void close() {
		entityManager.close();
		entityManagerFactory.close();
	}

}
