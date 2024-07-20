package com.ravehalcajpa.service.impl;
import jakarta.persistence.EntityManager;

public class BaseDAO extends JPADAOFactory {
	public EntityManager getEntityManager() {
		return JPADAOFactory.createEntityManager();
	}

	public void closeEntityManager() {
		JPADAOFactory.close();
	}

}
