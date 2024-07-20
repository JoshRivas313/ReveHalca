
package com.ravehalcajpa.model;

import com.ravehalcajpa.service.EstadoProducto;
import jakarta.persistence.*;

@Entity

public class Producto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @ManyToOne
    @JoinColumn(name = "id_Categoria_Producto")
    private CategoriaProducto idcat;

    private String nombre;
    @Enumerated(EnumType.STRING)
    private EstadoProducto estado;
    private double precio;

    public Producto() {
    }

    public Producto(Long id, CategoriaProducto idcat, String nombre, EstadoProducto estado, double precio) {
        this.id = id;
        this.idcat = idcat;
        this.nombre = nombre;
        this.estado = estado;
        this.precio = precio;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public CategoriaProducto getIdcat() {
        return idcat;
    }

    public void setIdcat(CategoriaProducto idcat) {
        this.idcat = idcat;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public EstadoProducto getEstado() {
        return estado;
    }

    public void setEstado(EstadoProducto estado) {
        this.estado = estado;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    @Override
    public String toString() {
        return "Producto{" + "id=" + id + ", idcat=" + idcat + ", nombre=" + nombre + ", estado=" + estado + ", precio=" + precio + '}';
    }

    
}
