package com.ravehalcajpa.model;

import com.ravehalcajpa.service.EstadoPedido;
import jakarta.persistence.*;
import java.time.LocalTime;
import java.util.Date;
import java.util.List;

@Entity
public class Pedido {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "id_usuario", nullable = false)
    private Usuario usuario;

    @Column(name = "id_mesa", nullable = false)
    private Mesa mesa;

    @Enumerated(EnumType.STRING)
    private EstadoPedido estado;

    private LocalTime hora;

    @Temporal(TemporalType.DATE)
    private Date fecha;

    @OneToMany(mappedBy = "pedido", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<DetallePedido> detalles;

    public Pedido() {
    }

    public Pedido(Long id, Usuario usuario, Mesa mesa, EstadoPedido estado, LocalTime hora, Date fecha) {
        this.id = id;
        this.usuario = usuario;
        this.mesa = mesa;
        this.estado = estado;
        this.hora = hora;
        this.fecha = fecha;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public Mesa getMesa() {
        return mesa;
    }

    public void setMesa(Mesa mesa) {
        this.mesa = mesa;
    }

    public EstadoPedido getEstado() {
        return estado;
    }

    public void setEstado(EstadoPedido estado) {
        this.estado = estado;
    }

    public LocalTime getHora() {
        return hora;
    }

    public void setHora(LocalTime hora) {
        this.hora = hora;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public List<DetallePedido> getDetalles() {
        return detalles;
    }

    public void setDetalles(List<DetallePedido> detalles) {
        this.detalles = detalles;

    }

    @Override
    public String toString() {
        return "Pedido{" + "id=" + id + ", usuario=" + usuario + ", mesa=" + mesa
                + ", estado=" + estado + ", hora=" + hora + ", fecha=" + fecha
                + ", detalles=" + (detalles != null ? detalles.size() : "null") + '}';
    }

}
