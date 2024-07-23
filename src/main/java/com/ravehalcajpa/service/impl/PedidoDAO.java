package com.ravehalcajpa.service.impl;

import com.ravehalcajpa.connection.conexion;
import com.ravehalcajpa.model.DetallePedido;
import com.ravehalcajpa.model.Mesa;
import com.ravehalcajpa.model.Pedido;
import com.ravehalcajpa.model.Usuario;
import com.ravehalcajpa.service.DAO;
import com.ravehalcajpa.service.EstadoPedido;
import jakarta.transaction.Transactional;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class PedidoDAO extends conexion implements DAO<Pedido> {

    @Override
    @Transactional
    public Pedido create(Pedido p) {
        String sql = "INSERT INTO Pedido (estado, hora, fecha, id_usuario, id_mesa) VALUES (?, ?, ?, ?, ?)";
        try {
            conectar();
            PreparedStatement st = this.getCn().prepareStatement(sql);

            st.setString(1, p.getEstado().name());
            st.setTime(2, p.getHora());
            st.setDate(3, new java.sql.Date(p.getFecha().getTime()));
            st.setLong(4, p.getUsuario().getId());
            st.setLong(5, p.getMesa().getId());

            st.executeUpdate();

            // Obtener la clave generada automáticamente
            ResultSet rs = st.getGeneratedKeys();
            if (rs.next()) {
                long generatedId = rs.getLong(1);
                p.setId(generatedId);
            }

            // Insertar detalles del pedido
            if (p.getDetalles() != null) {
                for (DetallePedido detalle : p.getDetalles()) {
                    String sqlDetalle = "INSERT INTO detalle_pedido (id_pedido, id_producto, cantidad) VALUES (?, ?, ?)";
                    try (PreparedStatement stDetalle = this.getCn().prepareStatement(sqlDetalle)) {
                        stDetalle.setLong(1, p.getId());
                        stDetalle.setLong(2, detalle.getProducto().getId());
                        stDetalle.setInt(3, detalle.getCantidad());
                        stDetalle.executeUpdate();
                        // Cerrar el PreparedStatement después de usarlo
                    }
                }
            }

            return p;

        } catch (Exception ex) {
            Logger.getLogger(UsuarioDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                cerrar();
            } catch (Exception ex) {
                Logger.getLogger(UsuarioDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return null;
    }

    @Override
    @Transactional
    public Pedido getById(Long id) {
        String sql = "SELECT p.*, u.username FROM pedido p "
                + "JOIN usuario u ON p.id_usuario = u.id "
                + "JOIN mesa m ON p.id_mesa = m.id "
                + "WHERE p.id = ?";
        try {
            conectar();
            PreparedStatement st = this.getCn().prepareStatement(sql);

            st.setLong(1, id);

            ResultSet rs = st.executeQuery();
            if (rs.next()) {

                Pedido pedido = new Pedido();

                pedido.setId(rs.getLong("id"));
                pedido.setEstado(EstadoPedido.valueOf(rs.getString("estado")));
                pedido.setHora(rs.getTime("hora"));
                pedido.setFecha(rs.getDate("fecha"));

                Usuario usu = new Usuario();
                usu.setId(rs.getInt("id_usuario"));
                usu.setUsername(rs.getString("username"));

                pedido.setUsuario(usu);

                Mesa mesa = new Mesa();
                mesa.setId(rs.getLong("id_mesa"));
                pedido.setMesa(mesa);

                // Cargar detalles del pedido
                DetallePedidoDAO detallePedidoDAO = new DetallePedidoDAO();
                List<DetallePedido> detalles = detallePedidoDAO.getDetallesByPedidoId(id);
                pedido.setDetalles(detalles);

                System.out.println("Pedido recuperado: " + pedido);
                System.out.println("Detalles del pedido: " + detalles);

                return pedido;
            }
        } catch (Exception ex) {
            Logger.getLogger(UsuarioDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                cerrar();
            } catch (Exception ex) {
                Logger.getLogger(UsuarioDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return null;

    }

    @Override
    @Transactional
    public List<Pedido> getAll() {
        String sql = "SELECT p.id, p.estado, p.hora, p.fecha, u.username, p.id_mesa "
                + "FROM Pedido p "
                + "JOIN Usuario u ON p.id_usuario = u.id";
        List<Pedido> pedidos = new ArrayList<>();
        try {
            conectar();
            PreparedStatement st = this.getCn().prepareStatement(sql);
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                Pedido pedido = new Pedido();
                pedido.setId(rs.getLong("id"));
                pedido.setEstado(EstadoPedido.valueOf(rs.getString("estado")));
                pedido.setHora(rs.getTime("hora"));
                pedido.setFecha(rs.getDate("fecha"));

                Usuario usu = new Usuario();
                usu.setUsername(rs.getString("username"));
                pedido.setUsuario(usu);

                Mesa mesa = new Mesa();
                mesa.setId(rs.getLong("id_mesa"));
                pedido.setMesa(mesa);

                pedidos.add(pedido);
            }
            return pedidos;
        } catch (SQLException ex) {
            Logger.getLogger(UsuarioDAO.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(UsuarioDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                cerrar();
            } catch (Exception ex) {
                Logger.getLogger(UsuarioDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return null;
    }

    @Override
    public Pedido update(Pedido entity) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public boolean delete(long id) {
        String sql = "UPDATE pedido SET estado = 'Cancelado' WHERE id = ?";
        try {
            try {
                conectar();
            } catch (Exception ex) {
                Logger.getLogger(PedidoDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
            PreparedStatement st = this.getCn().prepareStatement(sql);
            st.setLong(1, id);
            int rowsUpdated = st.executeUpdate();
            return rowsUpdated > 0;
        } catch (SQLException ex) {
            Logger.getLogger(PedidoDAO.class.getName()).log(Level.SEVERE, "Error al cancelar el pedido", ex);
        } finally {
            try {
                cerrar();
            } catch (Exception ex) {
                Logger.getLogger(PedidoDAO.class.getName()).log(Level.SEVERE, "Error al cerrar la conexión", ex);
            }
        }
        return false;
    }

}
