/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ravehalcajpa.service.impl;

import com.ravehalcajpa.connection.conexion;
import com.ravehalcajpa.model.Comprobante;
import com.ravehalcajpa.model.Pedido;
import com.ravehalcajpa.model.Usuario;
import com.ravehalcajpa.service.DAO;
import com.ravehalcajpa.service.MetodoPago;
import jakarta.persistence.EntityManager;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ComprobanteDAO extends conexion implements DAO<Comprobante> {

    @Override
    public List<Comprobante> getAll() {
        String sql = "SELECT c.id, p.id as idpedido, c.nombrecliente, c.apellidocliente,u.username, c.Metodo_pago, c.hora_comprobante, c.total_comprobante FROM comprobante c JOIN usuario u ON c.id_usuario = u.id JOIN pedido p ON c.id_pedido = p.id;";
        List<Comprobante> lista = new ArrayList<>();
        try {
            conectar();
            PreparedStatement st = this.getCn().prepareStatement(sql);
            ResultSet rs = st.executeQuery();

            while (rs.next()) {
                Comprobante c = new Comprobante();
                c.setId(rs.getLong("id"));
                PedidoDAO pedidoDAO = new PedidoDAO();
                Pedido p = pedidoDAO.getById(rs.getLong("idpedido"));
                c.setIdped(p);
                c.setNombrecliente(rs.getString("nombrecliente"));
                c.setApellidocliente(rs.getString("apellidocliente"));
                Usuario u = new Usuario();
                u.setUsername(rs.getString("username"));
                c.setIduser(u);
                c.setPago(MetodoPago.valueOf(rs.getNString("Metodo_pago")));
                c.setHora(rs.getTime("hora_comprobante"));
                c.setTotal(rs.getDouble("total_comprobante"));

                lista.add(c);
            }

            return lista;
        } catch (Exception ex) {
            Logger.getLogger(ComprobanteDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                cerrar();
            } catch (Exception ex) {
                Logger.getLogger(ComprobanteDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return null;
    }

    @Override
    public Comprobante getById(Long id) {
        BaseDAO bd = new BaseDAO();
        EntityManager em = bd.getEntityManager();
        try {
            return em.find(Comprobante.class, id);
        } finally {
            em.close();
        }
    }

    @Override
    public Comprobante create(Comprobante entity) {
        String sql = "INSERT INTO comprobante (id_pedido, nombrecliente, apellidocliente, id_usuario,Metodo_pago, hora_comprobante, total_comprobante) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try {
            conectar();
            PreparedStatement st = this.getCn().prepareStatement(sql);
            st.setLong(1, entity.getIdped().getId());
            st.setString(2, entity.getNombrecliente());
            st.setString(3, entity.getApellidocliente());
            st.setLong(4, entity.getIduser().getId());
            st.setString(5, entity.getPago().name());
            st.setTime(6, entity.getHora());
            st.setDouble(7, entity.getTotal());

            st.executeUpdate();
        } catch (Exception ex) {
            Logger.getLogger(ComprobanteDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                cerrar();
            } catch (Exception ex) {
                Logger.getLogger(ComprobanteDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return null;
    }

    @Override
    public Comprobante update(Comprobante entity) {
        String sql = "UPDATE producto SET id_Categoria_Producto = ?, nombre = ?, estado = ?, precio = ? WHERE id = ?";
        try {
            conectar();
            PreparedStatement st = this.getCn().prepareStatement(sql);

            st.executeUpdate();
        } catch (Exception ex) {
            Logger.getLogger(ComprobanteDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                cerrar();
            } catch (Exception ex) {
                Logger.getLogger(ComprobanteDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return entity;
    }

    @Override
    public boolean delete(long id) {
        String sql = "UPDATE producto SET estado = 'Culminado' WHERE id = ?";
        try {
            conectar();
            PreparedStatement st = this.getCn().prepareStatement(sql);
            st.setLong(1, id);
            int rowsUpdated = st.executeUpdate();
            return rowsUpdated > 0;
        } catch (Exception ex) {
            Logger.getLogger(ComprobanteDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                cerrar();
            } catch (Exception ex) {
                Logger.getLogger(ComprobanteDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return false;
    }
    

}
