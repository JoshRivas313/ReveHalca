package com.ravehalcajpa.service.impl;

import com.ravehalcajpa.connection.conexion;
import com.ravehalcajpa.model.Cliente;
import jakarta.transaction.Transactional;
import java.util.List;
import com.ravehalcajpa.service.DAOedit;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.logging.Level;
import java.util.logging.Logger;

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
    
    public Cliente getByNombre(String nombre, String apellido){
        String sql = "SELECT * FROM cliente WHERE nombreCliente = ? and apellidoCliente = ?";
        conexion cn = new conexion();
        try {
            cn.conectar();
            PreparedStatement st = cn.getCn().prepareStatement(sql);
            st.setString(1, nombre);
            st.setString(2, apellido);
            
            ResultSet rs = st.executeQuery();
            if (rs.next()) {
                Cliente cliente = new Cliente();
                cliente.setId(rs.getLong("id"));
                cliente.setNombreCliente(rs.getString("nombreCliente"));
                cliente.setApellidoCliente(rs.getString("apellidoCliente"));
                return cliente;
            }
        } catch (Exception ex) {
            Logger.getLogger(ClienteDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                cn.cerrar();
            } catch (Exception ex) {
                Logger.getLogger(ClienteDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return null;
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
    
    @Transactional
    public Cliente create(Cliente entity) {
        EntityManager em = getEntityManager();
        try {
            em.persist(entity);
            return entity;
        } finally {
            em.close();
        }
    }
    
    
    
    
    

}
