/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ravehalcajpa.service.impl;

import com.ravehalcajpa.connection.conexion;
import com.ravehalcajpa.model.CategoriaProducto;
import com.ravehalcajpa.model.Producto;
import com.ravehalcajpa.service.DAO;
import com.ravehalcajpa.service.EstadoProducto;
import jakarta.persistence.EntityManager;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ProductoDAO extends conexion implements DAO<Producto> {

    @Override
    public List<Producto> getAll() {
        String sql = "SELECT p.id, p.nombre,c.nombrecategoria, p.estado,p.precio FROM producto p"
                + " JOIN categoriaproducto c ON p.id_Categoria_Producto = c.id;";
        List<Producto> lista = new ArrayList<>();
        try {
            conectar();
            PreparedStatement st = this.getCn().prepareStatement(sql);
            ResultSet rs = st.executeQuery();

            while (rs.next()) {
                Producto p = new Producto();
                p.setId(rs.getLong("id"));
                p.setNombre(rs.getNString("nombre"));
                CategoriaProducto cp = new CategoriaProducto();
                cp.setProductCategory(rs.getString("nombreCategoria"));
                p.setEstado(EstadoProducto.valueOf(rs.getNString("estado")));
                p.setPrecio(rs.getDouble("precio"));
                p.setIdcat(cp);
                lista.add(p);
            }

            return lista;
        } catch (Exception ex) {
            Logger.getLogger(ProductoDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                cerrar();
            } catch (Exception ex) {
                Logger.getLogger(ProductoDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return null;
    }

    @Override
    public Producto getById(Long id) {
        BaseDAO bd = new BaseDAO();
        EntityManager em = bd.getEntityManager();
        try {
            return em.find(Producto.class, id);
        } finally {
            em.close();
        }
    }

    @Override
    public Producto create(Producto entity) {
        String sql = "INSERT INTO producto (id_Categoria_Producto, nombre, estado, precio) VALUES (?, ?, ?, ?)";
        try {
            conectar();
            PreparedStatement st = this.getCn().prepareStatement(sql);

            st.setLong(1, entity.getIdcat().getId());
            st.setString(2, entity.getNombre());
            st.setString(3, entity.getEstado().name());
            st.setDouble(4, entity.getPrecio());
            st.executeUpdate();
        } catch (Exception ex) {
            Logger.getLogger(ProductoDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                cerrar();
            } catch (Exception ex) {
                Logger.getLogger(ProductoDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return null;
    }

    @Override
    public Producto update(Producto entity) {
        String sql = "UPDATE producto SET id_Categoria_Producto = ?, nombre = ?, estado = ?, precio = ? WHERE id = ?";
        try {
            conectar();
            PreparedStatement st = this.getCn().prepareStatement(sql);

            st.setLong(1, entity.getIdcat().getId());
            st.setString(2, entity.getNombre());
            st.setString(3, entity.getEstado().name());
            st.setDouble(4, entity.getPrecio());
            st.setLong(5, entity.getId());
            st.executeUpdate();
        } catch (Exception ex) {
            Logger.getLogger(ProductoDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                cerrar();
            } catch (Exception ex) {
                Logger.getLogger(ProductoDAO.class.getName()).log(Level.SEVERE, null, ex);
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
            Logger.getLogger(ProductoDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                cerrar();
            } catch (Exception ex) {
                Logger.getLogger(ProductoDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return false;
    }

}
