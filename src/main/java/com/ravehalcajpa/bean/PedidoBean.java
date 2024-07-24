package com.ravehalcajpa.bean;

import com.ravehalcajpa.model.DetallePedido;
import com.ravehalcajpa.model.Mesa;
import com.ravehalcajpa.model.Pedido;
import com.ravehalcajpa.model.Producto;
import com.ravehalcajpa.model.Usuario;
import com.ravehalcajpa.service.EstadoPedido;
import com.ravehalcajpa.service.impl.DetallePedidoDAO;
import com.ravehalcajpa.service.impl.MesaDAO;
import com.ravehalcajpa.service.impl.PedidoDAO;
import com.ravehalcajpa.service.impl.UsuarioDAO;
import jakarta.annotation.ManagedBean;
import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.SessionScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@ManagedBean
@SessionScoped
@Named("pedidoBean")
public class PedidoBean implements Serializable {

    private static final long serialVersionUID = 1L;

    @Inject
    private PedidoDAO dao;

    private Pedido pedido = new Pedido();
    private List<Pedido> tipope;

    @Inject
    private UsuarioDAO tipodao;
    private List<Usuario> tipos;

    @Inject
    private MesaDAO mesadao;
    private List<Mesa> tmesa;

    @Inject
    private DetallePedidoBean detallePedidoBean;

    @PostConstruct
    public void init() {
        try {
            if (pedido == null) {
                pedido = new Pedido();
            }
            if (pedido.getUsuario() == null) {
                pedido.setUsuario(new Usuario());
            }
            if (pedido.getMesa() == null) {
                pedido.setMesa(new Mesa());
            }
            if (pedido.getDetalles() == null) {
                pedido.setDetalles(new ArrayList<>());
            }

        } catch (Exception ex) {
            Logger.getLogger(PedidoBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public MesaDAO getMesadao() {
        return mesadao;
    }

    public void setMesadao(MesaDAO mesadao) {
        this.mesadao = mesadao;
    }

    public List<Mesa> getTmesa() {
        return tmesa;
    }

    public void setTmesa(List<Mesa> tmesa) {
        this.tmesa = tmesa;
    }

    public DetallePedidoBean getDetallePedidoBean() {
        return detallePedidoBean;
    }

    public void setDetallePedidoBean(DetallePedidoBean detallePedidoBean) {
        this.detallePedidoBean = detallePedidoBean;
    }

    public List<DetallePedido> getDetallesPedido() {
        return detallePedidoBean.getDetallesPedido();
    }

    public void setDetallesPedido(List<DetallePedido> detallesPedido) {
        detallePedidoBean.setDetallesPedido(detallesPedido);
    }

    public PedidoDAO getDao() {
        return dao;
    }

    public void setDao(PedidoDAO dao) {
        this.dao = dao;
    }

    public Pedido getPedido() {
        return pedido;
    }

    public void setPedido(Pedido pedido) {
        this.pedido = pedido;
    }

    public List<Pedido> getTipope() {
        return tipope;
    }

    public void setTipope(List<Pedido> tipope) {
        this.tipope = tipope;
    }

    public UsuarioDAO getTipodao() {
        return tipodao;
    }

    public void setTipodao(UsuarioDAO tipodao) {
        this.tipodao = tipodao;
    }

    public String newPedido() {
        this.pedido = new Pedido();
        this.pedido.setUsuario(new Usuario());
        this.pedido.setMesa(new Mesa());
        detallePedidoBean.clearDetalles();
        return "add";
    }

    public String editPedido() throws Exception {
        long id = this.pedido.getId();
        System.out.println("Editando pedido con ID: " + id);
        this.pedido = dao.getById(id);
        detallePedidoBean.setDetallesPedido(pedido.getDetalles());
        return "edit";
    }

    public List<Pedido> getAll() throws Exception {
        if (tipope == null) {
            tipope = dao.getAll();
            for (Pedido pedido : tipope) {
                // Cargar los detalles del pedido
                DetallePedidoDAO detallePedidoDAO = new DetallePedidoDAO();
                List<DetallePedido> detalles = detallePedidoDAO.getDetallesByPedidoId(pedido.getId());
                pedido.setDetalles(detalles);
            }
        }
        return tipope;
    }

    public String create() throws Exception {
        pedido.setDetalles(detallePedidoBean.getDetallesPedido());
        dao.create(pedido);
        tipope = null;
        return "/pedido/index.xhtml?faces-redirect=true";
    }

    public String update() {
        pedido.setDetalles(detallePedidoBean.getDetallesPedido());
        dao.update(pedido);
        return "/pedido/index.xhtml?faces-redirect=true";
    }

    public String delete() throws Exception {
        long id = this.pedido.getId();
        dao.delete(id);
        return "/pedido/index.xhtml?faces-redirect=true";
    }

    // Obtener usuarios
    public List<Usuario> getTipos() {
        if (tipos == null) {
            tipos = tipodao.getAll();
        }
        return tipos;
    }

    // Obtener estados
    public List<EstadoPedido> getEstados() {
        return Arrays.asList(EstadoPedido.values());
    }

    // Obtener mesas
    public List<Mesa> getMesas() {
        if (tmesa == null) {
            tmesa = mesadao.getAll();
        }
        return tmesa;
    }

    public void addDetalle(Producto producto) {
        detallePedidoBean.addDetalle(producto);
        System.out.println("llegué a pedidoBean");
    }

    public void removeDetalle(DetallePedido detalle) {
        detallePedidoBean.removeDetalle(detalle);
    }

    public void menosProd(DetallePedido detalle) {
        detallePedidoBean.decrementarCantidad(detalle);
    }

    public void masProd(DetallePedido detalle) {
        System.out.println("mas pedido");
        detallePedidoBean.incrementarCantidad(detalle);

    }
}
