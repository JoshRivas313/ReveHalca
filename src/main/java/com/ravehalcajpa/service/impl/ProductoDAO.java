
package com.ravehalcajpa.service.impl;

import com.ravehalcajpa.connection.conexion;
import com.ravehalcajpa.model.CategoriaProducto;

import com.ravehalcajpa.model.Producto;
import com.ravehalcajpa.service.EstadoProducto;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProductoDAO extends conexion {

    @Transactional
    public void create(Producto p) throws SQLException, Exception {
        String sql = "INSERT INTO producto (id_Categoria_Producto, nombre, estado, precio) VALUES (?, ?, ?, ?)";
        try {
            conectar();
            PreparedStatement st = this.getCn().prepareStatement(sql);

            st.setLong(1, p.getIdcat().getId());
            st.setString(2, p.getNombre());
            st.setString(3, p.getEstado().name());
            st.setDouble(4, p.getPrecio());
            st.executeUpdate();
        } finally {
            cerrar();
        }
    }

    @Transactional
    public Producto getById(Long id) throws SQLException, Exception {
        BaseDAO bd = new BaseDAO();
        EntityManager em = bd.getEntityManager();
        try {
            return em.find(Producto.class, id);
        } finally {
            em.close();
        }

    }

    @Transactional
    public List<Producto> getAll() throws SQLException, Exception {

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
        } finally {
            cerrar();
        }

    }

    @Transactional
    public Producto update(Producto entity) throws Exception {
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
        } finally {
            cerrar();
        }
        return entity;
    }

    @Transactional
    public boolean delete(long id) throws Exception {

        String sql = "UPDATE producto SET estado = 'Culminado' WHERE id = ?";
        try {
            conectar();
            PreparedStatement st = this.getCn().prepareStatement(sql);
            st.setLong(1, id);
            int rowsUpdated = st.executeUpdate();
            return rowsUpdated > 0;
        } finally {
            cerrar();
        }
    }

}
