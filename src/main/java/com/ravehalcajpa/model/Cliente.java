
package com.ravehalcajpa.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.io.Serializable;

@Entity
@Table(name = "cliente")
public class Cliente implements Serializable{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long  id;
    @Column(name = "nombreCliente", nullable = false)
    private String nombreCliente;
    @Column(name = "apellidoCliente", nullable = false)
    private String apellidoCliente;

    public Cliente() {
    }

    public Cliente(Long id, String nombreCliente, String apellidoCliente) {
        this.id = id;
        this.nombreCliente = nombreCliente;
        this.apellidoCliente = apellidoCliente;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombreCliente() {
        return nombreCliente;
    }

    public void setNombreCliente(String nombreCliente) {
        this.nombreCliente = nombreCliente;
    }

    public String getApellidoCliente() {
        return apellidoCliente;
    }

    public void setApellidoCliente(String apellidoCliente) {
        this.apellidoCliente = apellidoCliente;
    }
    
    @Override
    public String toString(){
                return "Cliente{" + "id=" + id + ", nombreCliente=" + nombreCliente + "apellidoCliente=" + apellidoCliente + '}';
    }
    
}
