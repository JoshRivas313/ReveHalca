package com.ravehalcajpa.service.impl;

import com.ravehalcajpa.model.Cliente;
import jakarta.transaction.Transactional;
import java.util.List;
import com.ravehalcajpa.service.DAOedit;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;

public class ClienteDAO extends BaseDAO implements DAOedit<Cliente> {

    @Override
    public Cliente getById(Long id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Cliente.class, id);
        } finally {
            closeEntityManager();
        }
    }

    @Override
    public List<Cliente> getAll() {
        EntityManager em = getEntityManager();
        try {
            String sql = "SELECT u FROM Cliente u";
            TypedQuery<Cliente> query = em.createQuery(sql, Cliente.class);
            List<Cliente> list = query.getResultList();
            return list.isEmpty() ? null : list;
        } finally {
            closeEntityManager();
        }
    }

    
    

    @Override
    @Transactional
    public Cliente update(Cliente entity) {
        
        EntityManager em = getEntityManager();
        try {
            em.merge(entity);
            return entity;
        } finally {
            em.close();
        }
        
    }
    
    
    
    
    
    
    

}
