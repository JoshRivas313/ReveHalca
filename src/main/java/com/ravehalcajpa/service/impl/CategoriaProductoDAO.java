package com.ravehalcajpa.service.impl;

import com.ravehalcajpa.model.CategoriaProducto;
import com.ravehalcajpa.service.DAO;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;
import java.util.List;

public class CategoriaProductoDAO extends BaseDAO implements DAO<CategoriaProducto> {

    @Override
    public CategoriaProducto getById(Long  id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(CategoriaProducto.class, id);
        } finally {
            closeEntityManager();
        }
    }

    @Override
    public List<CategoriaProducto> getAll() {
        EntityManager em = getEntityManager();
        try {
            String sql = "SELECT u  FROM CategoriaProducto u";
            TypedQuery<CategoriaProducto> query = em.createQuery(sql, CategoriaProducto.class);
            List<CategoriaProducto> list = query.getResultList();
            return list.isEmpty() ? null : list;
        } finally {
            closeEntityManager();
        }
        
    }

    @Override
    @Transactional
    public CategoriaProducto create(CategoriaProducto entity) {
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
    public CategoriaProducto update(CategoriaProducto entity) {
        EntityManager em = getEntityManager();
        try {
            em.merge(entity);
            return entity;
        } finally {
            em.close();
        }
        
    }

    @Override
    public boolean delete(long  id) {
      
        return true;
    }

}
