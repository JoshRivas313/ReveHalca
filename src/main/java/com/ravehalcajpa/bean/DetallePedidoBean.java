package com.ravehalcajpa.bean;

import com.ravehalcajpa.model.DetallePedido;
import com.ravehalcajpa.model.Producto;
import com.ravehalcajpa.service.impl.ProductoDAO;
import jakarta.annotation.ManagedBean;
import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.SessionScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@ManagedBean
@SessionScoped
@Named("detpedidoBean")
public class DetallePedidoBean implements Serializable {

    private static final long serialVersionUID = 1L;

    @Inject
    private ProductoDAO productoDAO;

    private List<DetallePedido> detallesPedido;
    private List<Producto> productos;

    @PostConstruct
    public void init() {
        try {
            productos = productoDAO.getAll();
            detallesPedido = new ArrayList<>();
        } catch (Exception ex) {
            Logger.getLogger(DetallePedidoBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public List<DetallePedido> getDetallesPedido() {
        return detallesPedido;
    }

    public void setDetallesPedido(List<DetallePedido> detallesPedido) {
        this.detallesPedido = detallesPedido;
    }

    public List<Producto> getProductos() {
        return productos;
    }

    public void setProductos(List<Producto> productos) {
        this.productos = productos;
    }

    public ProductoDAO getProductoDAO() {
        return productoDAO;
    }

    public void setProductoDAO(ProductoDAO productoDAO) {
        this.productoDAO = productoDAO;
    }


    public void addDetalle(Producto producto) {
        DetallePedido existingDetalle = findDetalleByProducto(producto);
        if (existingDetalle != null) {
            existingDetalle.setCantidad(existingDetalle.getCantidad() + 1);
        } else {
            DetallePedido nuevoDetalle = new DetallePedido();
            nuevoDetalle.setProducto(producto);
            nuevoDetalle.setCantidad(1);
            detallesPedido.add(nuevoDetalle);
        }
        System.out.println("Detalle añadido: " + producto.getNombre());
        printAllDetalles();
    }

    private DetallePedido findDetalleByProducto(Producto producto) {
        for (DetallePedido detalle : detallesPedido) {
            if (detalle.getProducto().getId().equals(producto.getId())) {
                return detalle;
            }
        }
        return null;
    }
    
     public void removeDetalle(DetallePedido detalle) {
        detallesPedido.remove(detalle);
        System.out.println("Detalle removido: " + detalle.getProducto().getNombre());
        printAllDetalles();
    }

    public void incrementarCantidad(DetallePedido detalle) {
        detalle.setCantidad(detalle.getCantidad() + 1);
        System.out.println("Cantidad incrementada para: " + detalle.getProducto().getNombre());
        printAllDetalles();
    }

    public void decrementarCantidad(DetallePedido detalle) {
        if (detalle.getCantidad() > 1) {
            detalle.setCantidad(detalle.getCantidad() - 1);
        } else {
            detallesPedido.remove(detalle);
        }
        System.out.println("Cantidad decrementada para: " + detalle.getProducto().getNombre());
        printAllDetalles();
    }

    private void printAllDetalles() {
        System.out.println("Lista actual de detalles del pedido:");
        for (DetallePedido detalle : detallesPedido) {
            System.out.println("- " + detalle.getProducto().getNombre() + " (Cantidad: " + detalle.getCantidad() + ")");
        }
    }
    
    public void clearDetalles() {
        detallesPedido.clear();
        System.out.println("Todos los detalles han sido eliminados.");
    }
}
