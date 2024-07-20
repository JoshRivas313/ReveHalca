package com.ravehalcajpa.service.impl;

import com.ravehalcajpa.model.CategoriaProducto;
import com.ravehalcajpa.model.TipoUsuario;
import com.ravehalcajpa.service.DAOlist;
import com.ravehalcajpa.service.DAOuser;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;
import java.util.List;

public class TipoUsuarioDAO extends BaseDAO implements DAOuser<TipoUsuario> {

    @Override
    public TipoUsuario getById(int id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(TipoUsuario.class, id);
        } finally {
            closeEntityManager();
        }
    }

    @Override
    public List<TipoUsuario> getAll() {
        EntityManager em = getEntityManager();
        try {
            String sql = "SELECT u  FROM TipoUsuario u";
            TypedQuery<TipoUsuario> query = em.createQuery(sql, TipoUsuario.class);
            List<TipoUsuario> list = query.getResultList();
            return list.isEmpty() ? null : list;
        } finally {
            closeEntityManager();
        }

    }

    @Override
    @Transactional
    public TipoUsuario create(TipoUsuario entity) {
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
    public TipoUsuario update(TipoUsuario entity) {
        EntityManager em = getEntityManager();
        try {
            em.merge(entity);
            return entity;
        } finally {
            em.close();
        }
    }

    @Override
    public boolean delete(int id) {
        return true;
    }
}
