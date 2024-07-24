package com.ravehalcajpa.model;

import com.ravehalcajpa.service.MetodoPago;
import jakarta.persistence.*;
import java.sql.Time;
import java.util.Date;

@Entity

public class Comprobante {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "id_pedido")
    private Pedido idped;

    private String nombrecliente;
    private String apellidocliente;

    @ManyToOne
    @JoinColumn(name = "id_usuario")
    private Usuario iduser;

    @Enumerated(EnumType.STRING)
    private MetodoPago pago;

    private Time hora;
    private double total;
    
    private Date fecha;

    public Comprobante() {
    }

    public Comprobante(Long id, Pedido idped, String nombrecliente, String apellidocliente, Usuario iduser, MetodoPago pago, Time hora, double total, Date fecha) {
        this.id = id;
        this.idped = idped;
        this.nombrecliente = nombrecliente;
        this.apellidocliente = apellidocliente;
        this.iduser = iduser;
        this.pago = pago;
        this.hora = hora;
        this.total = total;
        this.fecha = fecha;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Pedido getIdped() {
        return idped;
    }

    public void setIdped(Pedido idped) {
        this.idped = idped;
    }

    public String getNombrecliente() {
        return nombrecliente;
    }

    public void setNombrecliente(String nombrecliente) {
        this.nombrecliente = nombrecliente;
    }

    public String getApellidocliente() {
        return apellidocliente;
    }

    public void setApellidocliente(String apellidocliente) {
        this.apellidocliente = apellidocliente;
    }

    public Usuario getIduser() {
        return iduser;
    }

    public void setIduser(Usuario iduser) {
        this.iduser = iduser;
    }

    public MetodoPago getPago() {
        return pago;
    }

    public void setPago(MetodoPago pago) {
        this.pago = pago;
    }

    public Time getHora() {
        return hora;
    }

    public void setHora(Time hora) {
        this.hora = hora;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    
}
