package com.ravehalcajpa.service.impl;

import com.ravehalcajpa.model.Mesa;
import com.ravehalcajpa.service.DAO;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;

import java.util.List;

public class MesaDAO extends BaseDAO implements DAO<Mesa> {

    @Override
    public Mesa getById(Long id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Mesa.class, id);
        } finally {
            closeEntityManager();
        }
    }

    @Override
    public List<Mesa> getAll() {
        EntityManager em = getEntityManager();
        try {
            String sql = "SELECT u FROM Mesa u";
            TypedQuery<Mesa> query = em.createQuery(sql, Mesa.class);
            List<Mesa> list = query.getResultList();
            return list.isEmpty() ? null : list;
        } finally {
            closeEntityManager();
        }
    }

    @Override
    @Transactional
    public Mesa create(Mesa entity) {
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
    public Mesa update(Mesa entity) {
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
            Mesa mesa = em.find(Mesa.class, id);
            if (mesa != null) {
                mesa.setEstado("Baja");
                em.merge(mesa);
                return true;
            } else {
                return false;
            }
        } finally {
            em.close();
        }
    }
}
