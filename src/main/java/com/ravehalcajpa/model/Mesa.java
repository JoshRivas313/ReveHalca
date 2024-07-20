
package com.ravehalcajpa.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.io.Serializable;

@Entity
@Table(name = "mesa")
public class Mesa implements Serializable{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long  id;
    @Column(name = "capacidadMesa", nullable = false)
    private Long capacidadMesa;
    @Column(name = "estado", nullable = false)
    private String estado;
 
    public Mesa() {
    }

    public Mesa(Long id, Long capacidadMesa, String estado) {
        this.id = id;
        this.capacidadMesa = capacidadMesa;
        this.estado = estado;
    }

    
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getCapacidadMesa() {
        return capacidadMesa;
    }

    public void setCapacidadMesa(Long capacidadMesa) {
        this.capacidadMesa = capacidadMesa;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }
    
    @Override
    public String toString() {
        return "Mesa{" + "id=" + id + ", capacidadMesa=" + capacidadMesa + "estado=" + estado + '}';
    }
    
    
}
