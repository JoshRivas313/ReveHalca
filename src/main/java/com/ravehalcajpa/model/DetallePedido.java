
package com.ravehalcajpa.model;

import jakarta.persistence.*;

@Entity
@Table(name = "detalle_pedido")

public class DetallePedido {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @ManyToOne
    @JoinColumn(name = "id_pedido")
    private Pedido pedido;

    @ManyToOne
    @JoinColumn(name = "id_producto")
    private Producto producto;

    private int cantidad;

    public DetallePedido() {
    }

    public DetallePedido(Long id, Pedido pedido, Producto producto, int cantidad) {
        this.id = id;
        this.pedido = pedido;
        this.producto = producto;
        this.cantidad = cantidad;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Pedido getPedido() {
        return pedido;
    }

    public void setPedido(Pedido pedido) {
        this.pedido = pedido;
    }

    public Producto getProducto() {
        return producto;
    }

    public void setProducto(Producto producto) {
        this.producto = producto;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }
    
    // Métodos de conveniencia para obtener nombre y precio del producto
    public String getNombreProducto() {
        return producto != null ? producto.getNombre() : "";
    }

    public double getPrecioProducto() {
        return producto != null ? producto.getPrecio() : 0.0;
    }
    
    @Override
    public String toString() {
        return "DetallePedido{" + "id=" + id + ", pedido=" + pedido + ", producto=" + producto + ", cantidad=" + cantidad + '}';
    }
    
}
