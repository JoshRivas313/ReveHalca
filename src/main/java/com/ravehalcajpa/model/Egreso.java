package com.ravehalcajpa.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.io.Serializable;

@Entity
@Table(name = "egreso")
public class Egreso implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long  id;
    @Column(name = "tipoEgreso", nullable = false)
    private String tipoEgreso;
    @Column(name = "descripcion", nullable = false)
    private String descripcion;
    @Column(name = "montoEgreso", nullable = false)
    private String montoEgreso;

    public Egreso() {
    }

    public Egreso(Long id, String tipoEgreso, String descripcion, String montoEgreso) {
        this.id = id;
        this.tipoEgreso = tipoEgreso;
        this.descripcion = descripcion;
        this.montoEgreso = montoEgreso;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long  id) {
        this.id = id;
    }

    public String getTipoEgreso() {
        return tipoEgreso;
    }

    public void setTipoEgreso(String tipoEgreso) {
        this.tipoEgreso = tipoEgreso;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getMontoEgreso() {
        return montoEgreso;
    }

    public void setMontoEgreso(String montoEgreso) {
        this.montoEgreso = montoEgreso;
    }

    @Override
    public String toString() {
        return "Egreso{" + "id=" + id + ", tipoEgreso=" + tipoEgreso + ", descripcion=" + descripcion + ", montoEgreso=" + montoEgreso + '}';
    }

}
