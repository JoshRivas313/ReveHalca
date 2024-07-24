package com.ravehalcajpa.service.impl;

import com.ravehalcajpa.connection.conexion;
import com.ravehalcajpa.model.DetallePedido;
import com.ravehalcajpa.model.Mesa;
import com.ravehalcajpa.model.Pedido;
import com.ravehalcajpa.model.Usuario;
import com.ravehalcajpa.service.DAO;
import com.ravehalcajpa.service.EstadoPedido;
import jakarta.transaction.Transactional;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class PedidoDAO extends conexion implements DAO<Pedido> {
private final DetallePedidoDAO detallePedidoDAO = new DetallePedidoDAO();
//    private final InventarioDAO inventarioDAO = new InventarioDAO();

    @Override
    @Transactional
    public Pedido create(Pedido p) {
        String sqlPedido = "INSERT INTO Pedido (estado, hora, fecha, id_usuario, id_mesa) VALUES (?, ?, ?, ?, ?)";
        try {
            conectar();
            Connection conn = this.getCn();
            try (PreparedStatement stPedido = conn.prepareStatement(sqlPedido, Statement.RETURN_GENERATED_KEYS)) {

                stPedido.setString(1, p.getEstado().name());
                if (p.getHora() != null) {
                    stPedido.setTime(2, java.sql.Time.valueOf(p.getHora()));
                } else {
                    stPedido.setNull(2, java.sql.Types.TIME);
                }
                stPedido.setDate(3, new java.sql.Date(p.getFecha().getTime()));
                stPedido.setLong(4, p.getUsuario().getId());
                stPedido.setLong(5, p.getMesa().getId());
                stPedido.executeUpdate();

                try (ResultSet rs = stPedido.getGeneratedKeys()) {
                    if (rs.next()) {
                        long generatedId = rs.getLong(1);
                        p.setId(generatedId);
                    } else {
                        throw new SQLException("No se pudo obtener la id generada para el pedido.");
                    }
                }

                if (p.getDetalles() != null) {
                    for (DetallePedido detalle : p.getDetalles()) {
                        detalle.setPedido(p);
                        detallePedidoDAO.create(detalle);
                    }
                }

                return p;

            } catch (SQLException ex) {
                Logger.getLogger(PedidoDAO.class.getName()).log(Level.SEVERE, "Error al crear el pedido", ex);
                throw new SQLException("Error al crear el pedido", ex);
            } finally {
                cerrar();
            }

        } catch (Exception ex) {
            Logger.getLogger(PedidoDAO.class.getName()).log(Level.SEVERE, "Error al conectar", ex);
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
                if (rs.getTime("hora") != null) {
                    pedido.setHora(rs.getTime("hora").toLocalTime());
                }
                pedido.setFecha(rs.getDate("fecha"));

                Usuario usu = new Usuario();
                usu.setId(rs.getInt("id_usuario"));
                usu.setUsername(rs.getString("username"));

                pedido.setUsuario(usu);

                Mesa mesa = new Mesa();
                mesa.setId(rs.getLong("id_mesa"));
                pedido.setMesa(mesa);

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
                if (rs.getTime("hora") != null) {
                    pedido.setHora(rs.getTime("hora").toLocalTime());
                }
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
    @Transactional
    public Pedido update(Pedido p) {
        String sqlPedido = "UPDATE Pedido SET estado = ?, hora = ?, fecha = ?, id_usuario = ?, id_mesa = ? WHERE id = ?";
        String sqlDeleteDetalles = "DELETE FROM Detalle_Pedido WHERE id_pedido = ?";

        try {
            try {
                conectar();
            } catch (Exception ex) {
                Logger.getLogger(PedidoDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
            Connection conn = this.getCn();

            Pedido currentPedido = getById(p.getId());
            System.out.println("Detalles actuales del pedido:");
            printPedido(currentPedido);

            // me falta agregar método que añada al inventario si se remueve o descuenta producto del pedido :,) 
//            for (DetallePedido detalle : currentPedido.getDetalles()) {
//                try {
//                    inventarioDAO.updateCantDisp(detalle.getProducto().getId(), detalle.getCantidad());
//                } catch (Exception ex) {
//                    Logger.getLogger(PedidoDAO.class.getName()).log(Level.SEVERE, null, ex);
//                }
//            }
            
            try (PreparedStatement stPedido = conn.prepareStatement(sqlPedido)) {
                stPedido.setString(1, p.getEstado().name());

                if (p.getHora() != null) {
                    stPedido.setTime(2, java.sql.Time.valueOf(p.getHora()));
                } else {
                    stPedido.setNull(2, java.sql.Types.TIME);
                }

                stPedido.setDate(3, new java.sql.Date(p.getFecha().getTime()));
                stPedido.setLong(4, p.getUsuario().getId());
                stPedido.setLong(5, p.getMesa().getId());
                stPedido.setLong(6, p.getId());
                stPedido.executeUpdate();
            }

            try (PreparedStatement stDelete = conn.prepareStatement(sqlDeleteDetalles)) {
                stDelete.setLong(1, p.getId());
                stDelete.executeUpdate();
            }

            if (p.getDetalles() != null) {
                for (DetallePedido detalle : p.getDetalles()) {
                    detalle.setPedido(p);
                    detallePedidoDAO.create(detalle);
                }
            }

            Pedido updatedPedido = getById(p.getId());
            System.out.println("Nuevos detalles del pedido:");
            printPedido(updatedPedido);

            return p;

        } catch (SQLException ex) {
            Logger.getLogger(PedidoDAO.class.getName()).log(Level.SEVERE, "Error al actualizar el pedido", ex);
        } finally {
            try {
                cerrar();
            } catch (Exception ex) {
                Logger.getLogger(PedidoDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        return null;
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

    //para ver si se cargaba el detalle actualizado
    private void printPedido(Pedido pedido) {
        System.out.println("Pedido ID: " + pedido.getId());
        System.out.println("Estado: " + pedido.getEstado());
        System.out.println("Hora: " + pedido.getHora());
        System.out.println("Fecha: " + pedido.getFecha());
        System.out.println("Usuario: " + pedido.getUsuario().getUsername());
        System.out.println("Mesa: " + pedido.getMesa().getId());
        System.out.println("Detalles:");
        for (DetallePedido detalle : pedido.getDetalles()) {
            System.out.println(" - Producto ID: " + detalle.getProducto().getId());
            System.out.println("   Cantidad: " + detalle.getCantidad());
        }
    }
    
    
    //OBTENER EL PEDIDO SEGUN LA MESA :(
    //sare cambié el tipo de dato de hora, ya que no me dejaba guardar
    //cree un convertidor, está en el paquete converter
    public Pedido getPedidoByMesaId(Long mesaId) {
        String sql = "SELECT p.id, p.id_usuario, p.id_mesa, p.estado, p.hora, p.fecha FROM pedido p  WHERE p.id_mesa = ? ORDER BY p.fecha DESC, p.hora DESC LIMIT 1";
        try {
            conectar();
            PreparedStatement st = this.getCn().prepareStatement(sql);
            st.setLong(1, mesaId);
            ResultSet rs = st.executeQuery();
            if (rs.next()) {
                Pedido p = new Pedido();
                p.setId(rs.getLong("id"));

                Usuario u = new Usuario();
                u.setId(rs.getInt("id_usuario"));
                p.setUsuario(u);

                Mesa mesa = new Mesa();
                mesa.setId(rs.getLong("id_mesa"));
                p.setMesa(mesa);

                p.setEstado(EstadoPedido.valueOf(rs.getString("estado")));
                if (rs.getTime("hora") != null) {
                    p.setHora(rs.getTime("hora").toLocalTime());
                }
                p.setFecha(rs.getDate("fecha"));
                
                DetallePedidoDAO detallePedidoDAO = new DetallePedidoDAO();
                List<DetallePedido> detalles = detallePedidoDAO.getDetallesByPedidoId(p.getId());
                p.setDetalles(detalles);

                return p;
            }
        } catch (Exception ex) {
            Logger.getLogger(PedidoDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                cerrar();
            } catch (Exception ex) {
                Logger.getLogger(PedidoDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return null;
    }

}