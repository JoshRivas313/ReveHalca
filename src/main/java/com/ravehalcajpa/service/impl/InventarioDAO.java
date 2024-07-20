/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ravehalcajpa.service.impl;

import com.ravehalcajpa.connection.conexion;
import com.ravehalcajpa.model.Inventario;
import com.ravehalcajpa.model.Producto;
import com.ravehalcajpa.service.EstadoProducto;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class InventarioDAO extends conexion {

    @Transactional
    public void create(Inventario i) throws SQLException, Exception {
        String sql = "INSERT INTO inventario (id_Producto, cantidad_disponible) VALUES (?, ?)";
        try {
            conectar();
            PreparedStatement st = this.getCn().prepareStatement(sql);

            st.setLong(1, i.getIdprod().getId());
            st.setInt(2, i.getCantidad());
            st.executeUpdate();
        } finally {
            cerrar();
        }
    }

    @Transactional
    public List<Inventario> getAll() throws SQLException, Exception {

        String sql = "SELECT i.id, p.nombre,i.cantidad_disponible, p.estado FROM inventario i"
                + " JOIN producto p ON i.id_Producto = p.id;";
        List<Inventario> lista = new ArrayList<>();
        try {
            conectar();
            PreparedStatement st = this.getCn().prepareStatement(sql);
            ResultSet rs = st.executeQuery();

            while (rs.next()) {
                Inventario i = new Inventario();
                i.setId(rs.getLong("id"));
                Producto p = new Producto();
                p.setNombre(rs.getString("nombre"));
                i.setCantidad(rs.getInt("cantidad_disponible"));
                p.setEstado(EstadoProducto.valueOf(rs.getString("estado")));
                i.setIdprod(p);
                lista.add(i);
            }

            return lista;
        } finally {
            cerrar();
        }
    }
    
    

        
        
    
    
    
    
}
