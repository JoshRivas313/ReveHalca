
package com.ravehalcajpa.service.impl;

import com.ravehalcajpa.connection.conexion;
import com.ravehalcajpa.model.Comprobante;
import com.ravehalcajpa.model.Ingreso;
import com.ravehalcajpa.service.DAO;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class IngresoDAO extends conexion implements DAO<Ingreso> {

    @Override
    public List<Ingreso> getAll() {
        String sql = "SELECT * from Ingreso ";
        List<Ingreso> lista = new ArrayList<>();
        try {
            conectar();
            PreparedStatement st = this.getCn().prepareStatement(sql);
            ResultSet rs = st.executeQuery();

            while (rs.next()) {
                Ingreso i = new Ingreso();
                i.setId(rs.getLong("id"));
                ComprobanteDAO daocom = new ComprobanteDAO();
                Comprobante c = daocom.getById(rs.getLong("id_comprobante"));
                i.setComprobante(c);
                i.setFecha(rs.getDate("fecha_ingreso"));
                i.setTotal(rs.getDouble("Total"));
                lista.add(i);
            }

            return lista;
        } catch (Exception ex) {
            Logger.getLogger(IngresoDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                cerrar();
            } catch (Exception ex) {
                Logger.getLogger(IngresoDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return null;
    }

    @Override
    public Ingreso getById(Long id) {
        BaseDAO bd = new BaseDAO();
        EntityManager em = bd.getEntityManager();
        try {
            return em.find(Ingreso.class, id);
        } finally {
            em.close();
        }
    }
    
    @Transactional
    public Ingreso getIngresoByComprobante(Long id){
        String sql = "Select * from Ingreso i where i.id_comprobante = ?;";
        try {
            conectar();
            PreparedStatement st = this.getCn().prepareStatement(sql);
            st.setLong(1, id);
            ResultSet rs = st.executeQuery();
            if (rs.next()) {
                Ingreso i = new Ingreso();
                
                i.setId(rs.getLong("id"));
                ComprobanteDAO comdao = new ComprobanteDAO();
                Comprobante com = comdao.getById(rs.getLong("id_comprobante"));
                i.setComprobante(com);
                i.setFecha(rs.getDate("fecha_ingreso"));
                i.setTotal(rs.getDouble("monto_ingreso"));
                
                return i;
            }
        } catch (Exception ex) {
            Logger.getLogger(IngresoDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                cerrar();
            } catch (Exception ex) {
                Logger.getLogger(IngresoDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return null;
    }
    
    
    

    @Override
    @Transactional
    public Ingreso create(Ingreso entity) {
        String sql = "INSERT INTO Ingreso (id_comprobante, fecha_ingreso,monto_ingreso) VALUES (?, ?, ?)";
        try {
            conectar();
            PreparedStatement st = this.getCn().prepareStatement(sql);
            st.setLong(1, entity.getComprobante().getId());
            st.setDate(2, (Date) entity.getFecha());
            st.setDouble(3, entity.getTotal());
            st.executeUpdate();
        } catch (Exception ex) {
            Logger.getLogger(IngresoDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                cerrar();
            } catch (Exception ex) {
                Logger.getLogger(IngresoDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return null;
    }

    @Override
    
    public Ingreso update(Ingreso entity) {
        
        return entity;
    }

    @Override
    public boolean delete(long id) {
        
        return false;
    }

    
}
