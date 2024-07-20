/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ravehalcajpa.model;
import jakarta.persistence.*;


@Entity

public class Inventario {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @ManyToOne
    @JoinColumn(name = "id_Producto")
    private Producto idprod;
    
    private int cantidad;

    public Inventario() {
    }

    public Inventario(Long id, Producto idprod, int cantidad) {
        this.id = id;
        this.idprod = idprod;
        this.cantidad = cantidad;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Producto getIdprod() {
        return idprod;
    }

    public void setIdprod(Producto idprod) {
        this.idprod = idprod;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    @Override
    public String toString() {
        return "InventarioDAO{" + "id=" + id + ", idprod=" + idprod + ", cantidad=" + cantidad + '}';
    }
    
    
    
    
}
