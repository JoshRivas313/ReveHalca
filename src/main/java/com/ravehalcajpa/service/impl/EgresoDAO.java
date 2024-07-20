package com.ravehalcajpa.service.impl;

import com.ravehalcajpa.model.Egreso;
import com.ravehalcajpa.service.DAO;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;

import java.util.List;

public class EgresoDAO extends BaseDAO implements DAO<Egreso> {

    @Override
    public Egreso getById(Long id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Egreso.class, id);
        } finally {
            closeEntityManager();
        }
    }

    @Override
    public List<Egreso> getAll() {
        EntityManager em = getEntityManager();
        try {
            String sql = "SELECT u FROM Egreso u";
            TypedQuery<Egreso> query = em.createQuery(sql, Egreso.class);
            List<Egreso> list = query.getResultList();
            return list.isEmpty() ? null : list;
        } finally {
            closeEntityManager();
        }
    }

    @Override
    @Transactional
    public Egreso create(Egreso entity) {
        EntityManager em = getEntityManager();
        try {
            em.persist(entity);
            return entity;
        } finally {
            em.close();
        }
    }

    @Override
    @Transactional
    public Egreso update(Egreso entity) {

        EntityManager em = getEntityManager();
        try {
            em.merge(entity);
            return entity;
        } finally {
            em.close();
        }
    }

    @Override
    @Transactional
    public boolean delete(long id) {
        EntityManager em = getEntityManager();
        try {
            Egreso egreso = em.find(Egreso.class, id);
            if (egreso != null) {
                em.remove(egreso);
                return true;
            } else {
                return false;
            }
        } finally {
            em.close();
        }

    }
}
