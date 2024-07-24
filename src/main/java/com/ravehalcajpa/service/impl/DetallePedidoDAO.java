package com.ravehalcajpa.service.impl;

import com.ravehalcajpa.connection.conexion;
import com.ravehalcajpa.model.DetallePedido;
import com.ravehalcajpa.model.Producto;
import com.ravehalcajpa.service.DAO;
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

public class DetallePedidoDAO extends conexion implements DAO<DetallePedido> {

    private final InventarioDAO inventarioDAO = new InventarioDAO();

    @Override
    @Transactional
    public DetallePedido create(DetallePedido dp) {
        String sql = "INSERT INTO Detalle_Pedido (id_pedido, id_producto, cantidad) VALUES (?, ?, ?)";
        try {
            conectar();
            Connection conn = this.getCn();
            try (PreparedStatement st = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
                st.setLong(1, dp.getPedido().getId());
                st.setLong(2, dp.getProducto().getId());
                st.setInt(3, dp.getCantidad());
                st.executeUpdate();

                try (ResultSet rs = st.getGeneratedKeys()) {
                    if (rs.next()) {
                        long generatedId = rs.getLong(1);
                        dp.setId(generatedId);
                    } else {
                        throw new SQLException("No se pudo obtener la id generada para el detalle del pedido.");
                    }
                }
                
                inventarioDAO.updateCantDisp(dp.getProducto().getId(), dp.getCantidad());

                return dp;

            } catch (SQLException ex) {
                Logger.getLogger(DetallePedidoDAO.class.getName()).log(Level.SEVERE, "Error al crear el detalle del pedido", ex);
                throw new SQLException("Error al crear el detalle del pedido", ex);
            } finally {
                cerrar();
            }

        } catch (Exception ex) {
            Logger.getLogger(DetallePedidoDAO.class.getName()).log(Level.SEVERE, "Error al conectar", ex);
        }
        return null;
    }

    @Override
    public List<DetallePedido> getAll() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    @Transactional
    public DetallePedido getById(Long id) {
//        String sql = "SELECT * FROM detalle_pedido WHERE id = ?";
//        try {
//            conectar();
//            PreparedStatement st = this.getCn().prepareStatement(sql);
//            st.setLong(1, id);
//            ResultSet rs = st.executeQuery();
//
//            if (rs.next()) {
//                DetallePedido detalle = new DetallePedido();
//                detalle.setId(rs.getLong("id"));
//
//                Pedido pedido = new Pedido();
//                pedido.setId(rs.getLong("id_pedido"));
//                detalle.setPedido(pedido);
//
//                Producto producto = new Producto();
//                producto.setId(rs.getLong("id_producto"));
//                detalle.setProducto(producto);
//
//                detalle.setCantidad(rs.getInt("cantidad"));
//                return detalle;
//            }
//        } catch (SQLException ex) {
//            Logger.getLogger(DetallePedidoDAO.class.getName()).log(Level.SEVERE, "Error al obtener el detalle del pedido", ex);
//        } catch (Exception ex) {
//            Logger.getLogger(DetallePedidoDAO.class.getName()).log(Level.SEVERE, null, ex);
//        } finally {
//            try {
//                cerrar();
//            } catch (Exception ex) {
//                Logger.getLogger(DetallePedidoDAO.class.getName()).log(Level.SEVERE, null, ex);
//            }
//        }
        return null;
    }

    @Override
    @Transactional
    public DetallePedido update(DetallePedido detalle) {
//        String sql = "UPDATE detalle_pedido SET id_pedido = ?, id_producto = ?, cantidad = ? WHERE id = ?";
//        try {
//            conectar();
//            PreparedStatement st = this.getCn().prepareStatement(sql);
//
//            st.setLong(1, detalle.getPedido().getId());
//            st.setLong(2, detalle.getProducto().getId());
//            st.setInt(3, detalle.getCantidad());
//            st.setLong(4, detalle.getId());
//
//            st.executeUpdate();
//            return detalle;
//        } catch (SQLException ex) {
//            Logger.getLogger(DetallePedidoDAO.class.getName()).log(Level.SEVERE, "Error al actualizar el detalle del pedido", ex);
//            return null;
//        } catch (Exception ex) {
//            Logger.getLogger(DetallePedidoDAO.class.getName()).log(Level.SEVERE, null, ex);
//        } finally {
//            try {
//                cerrar();
//            } catch (Exception ex) {
//                Logger.getLogger(DetallePedidoDAO.class.getName()).log(Level.SEVERE, null, ex);
//            }
//        }
        return null;
    }

    @Override
    @Transactional
    public boolean delete(long id) {
        return false;
        //esto era para pedidodao xddd
//        String sql = "UPDATE detalle_pedido SET estado = 'Cancelado' WHERE id = ?";
//        try {
//            conectar();
//            PreparedStatement st = this.getCn().prepareStatement(sql);
//            st.setLong(1, id);
//            int rowsUpdated = st.executeUpdate();
//            return rowsUpdated > 0;
//        } catch (Exception ex) {
//            Logger.getLogger(UsuarioDAO.class.getName()).log(Level.SEVERE, null, ex);
//        } finally {
//            try {
//                cerrar();
//            } catch (Exception ex) {
//                Logger.getLogger(UsuarioDAO.class.getName()).log(Level.SEVERE, null, ex);
//            }
//        }
//        return false;
    }

    @Transactional
    public List<DetallePedido> getDetallesByPedidoId(Long pedidoId) {
        List<DetallePedido> detalles = new ArrayList<>();
        String sql = "SELECT dp.*, p.nombre as nombre_producto, p.precio "
                + "FROM detalle_pedido dp "
                + "JOIN producto p ON dp.id_producto = p.id "
                + "WHERE dp.id_pedido = ?";
        try {
            conectar();
            PreparedStatement st = this.getCn().prepareStatement(sql);
            st.setLong(1, pedidoId);
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                DetallePedido detalle = new DetallePedido();
                detalle.setId(rs.getLong("id"));
                detalle.setCantidad(rs.getInt("cantidad"));

                Producto producto = new Producto();
                producto.setId(rs.getLong("id_producto"));
                producto.setNombre(rs.getString("nombre_producto"));
                producto.setPrecio(rs.getDouble("precio"));
                detalle.setProducto(producto);

                detalles.add(detalle);
            }
        } catch (SQLException ex) {
            Logger.getLogger(DetallePedidoDAO.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(DetallePedidoDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                cerrar();
            } catch (Exception ex) {
                Logger.getLogger(DetallePedidoDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return detalles;
    }

}
