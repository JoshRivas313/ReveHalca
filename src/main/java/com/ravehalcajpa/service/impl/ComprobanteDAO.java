/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ravehalcajpa.service.impl;

import com.ravehalcajpa.connection.conexion;
import com.ravehalcajpa.model.Comprobante;
import com.ravehalcajpa.model.DetallePedido;
import com.ravehalcajpa.model.Mesa;
import com.ravehalcajpa.model.Pedido;
import com.ravehalcajpa.model.Usuario;
import com.ravehalcajpa.service.DAO;
import com.ravehalcajpa.service.MetodoPago;
import jakarta.transaction.Transactional;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ComprobanteDAO extends conexion implements DAO<Comprobante> {

    @Override
    @Transactional
    public List<Comprobante> getAll() {
        String sql = "SELECT c.id, p.id as idpedido, c.nombrecliente, "
                + "c.apellidocliente,u.id as iduser, u.username, c.Metodo_pago, "
                + "c.hora_comprobante, c.total_comprobante FROM comprobante c "
                + "JOIN usuario u ON c.id_usuario = u.id "
                + "JOIN pedido p ON c.id_pedido = p.id;";
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
    @Transactional
    public Comprobante getById(Long id) {
        String sql = "SELECT c.*, u.username, p.id_mesa  FROM comprobante c "
                + "JOIN usuario u ON c.id_usuario = u.id "
                + "JOIN pedido p ON c.id_pedido = p.id "
                + "JOIN mesa m ON p.id_mesa = m.id "
                + "WHERE c.id= ?";
        try {
            conectar();
            PreparedStatement st = this.getCn().prepareStatement(sql);

            st.setLong(1, id);
            System.out.println(id);
            System.out.println(id);
            System.out.println(id);
            System.out.println(id);
            System.out.println(id);
            System.out.println(id);
            System.out.println(id);
            System.out.println(id);

            ResultSet rs = st.executeQuery();
            if (rs.next()) {
                Comprobante c = new Comprobante();
                c.setId(rs.getLong("id"));
                Pedido p = new Pedido();
                p.setId(rs.getLong("id_pedido"));
                c.setNombrecliente(rs.getNString("nombrecliente"));
                c.setApellidocliente(rs.getNString("apellidocliente"));
                Usuario usu;
                UsuarioDAO usudao = new UsuarioDAO();

                usu = usudao.getById(rs.getInt("id_usuario"));
                c.setIduser(usu);

                c.setPago(MetodoPago.valueOf(rs.getNString("Metodo_pago")));
                c.setHora(rs.getTime("hora_comprobante"));
                c.setTotal(rs.getDouble("total_comprobante"));

                Mesa me = new Mesa();
                me.setId(rs.getLong("id_mesa"));
                p.setMesa(me);

                // Cargar detalles del pedido
                DetallePedidoDAO detallePedidoDAO = new DetallePedidoDAO();
                List<DetallePedido> detalles = detallePedidoDAO.getDetallesByPedidoId(p.getId());
                p.setDetalles(detalles);

                System.out.println("usuario recuperado: " + usu);
                System.out.println("Pedido recuperado: " + p);
                System.out.println("Detalles del pedido: " + detalles);
                c.setIdped(p);
                return c;
            }
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
    @Transactional
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
    @Transactional
    public Comprobante update(Comprobante entity) {
        String sql = "UPDATE comprobante SET id_pedido = ?, nombrecliente = ?, apellidocliente = ?, "
                + "id_usuario = ?, Metodo_pago = ?, hora_comprobante = ?, total_comprobante = ? "
                + "WHERE id = ?";

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
            st.setLong(8, entity.getId());

            int rowsAffected = st.executeUpdate();
            if (rowsAffected > 0) {
                return entity;
            } else {
                throw new Exception("No se pudo actualizar el comprobante con ID: " + entity.getId());
            }
        } catch (Exception ex) {
            Logger.getLogger(ComprobanteDAO.class.getName()).log(Level.SEVERE, "Error al actualizar el comprobante", ex);
            return null;
        } finally {
            try {
                cerrar();
            } catch (Exception ex) {
                Logger.getLogger(ComprobanteDAO.class.getName()).log(Level.SEVERE, "Error al cerrar la conexión", ex);
            }
        }
    }

    @Override
    @Transactional
    public boolean delete(long id) {

        return false;
    }

}
