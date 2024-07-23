package com.ravehalcajpa.bean;

import com.ravehalcajpa.model.DetallePedido;
import com.ravehalcajpa.model.Mesa;
import com.ravehalcajpa.model.Pedido;
import com.ravehalcajpa.model.Usuario;
import com.ravehalcajpa.service.EstadoPedido;
import com.ravehalcajpa.service.impl.DetallePedidoDAO;
import com.ravehalcajpa.service.impl.MesaDAO;
import com.ravehalcajpa.service.impl.PedidoDAO;
import com.ravehalcajpa.service.impl.ProductoDAO;
import com.ravehalcajpa.service.impl.UsuarioDAO;
import jakarta.annotation.ManagedBean;
import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@ManagedBean
@RequestScoped
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

    private List<DetallePedido> detallesPedido = new ArrayList<>();
    @Inject
    private ProductoDAO productoDAO;

    @PostConstruct
    public void init() {
        if (pedido == null) {
            pedido = new Pedido();
        }
        if (pedido.getUsuario() == null) {
            pedido.setUsuario(new Usuario());
        }
        if (pedido.getMesa() == null) {
            pedido.setMesa(new Mesa());
        }
        detallesPedido.add(new DetallePedido());

    }

    public List<DetallePedido> getDetallesPedido() {
        return detallesPedido;
    }

    public void setDetallesPedido(List<DetallePedido> detallesPedido) {
        this.detallesPedido = detallesPedido;
    }

    public ProductoDAO getProductoDAO() {
        return productoDAO;
    }

    public void setProductoDAO(ProductoDAO productoDAO) {
        this.productoDAO = productoDAO;
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

    public PedidoBean() {
        pedido = new Pedido();
        pedido.setDetalles(new ArrayList<>());
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
        this.pedido.setDetalles(new ArrayList<>());
        return "add";
    }

    public String editPedido() throws Exception {
        long id = this.pedido.getId();
        this.pedido = dao.getById(id);
        System.out.println("sssssss" + id);
        System.out.println("sssssss" + id);
        System.out.println("sssssss" + id);
        System.out.println("sssssss" + id);
        System.out.println("sssssss" + id);
        System.out.println("sssssss" + id);
        System.out.println("sssssss" + id);
        System.out.println("sssssss" + id);
        System.out.println("sssssss" + id);
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

        if (tipope != null) {
            for (Pedido ped : tipope) {
                System.out.println("Pedido ID: " + ped.getId());
                System.out.println("Estado: " + ped.getEstado());
                System.out.println("Hora: " + ped.getHora());
                System.out.println("Fecha: " + ped.getFecha());
                System.out.println("Nombre del Usuario: " + (ped.getUsuario() != null ? ped.getUsuario().getUsername() : "N/A"));
                System.out.println("ID de la Mesa: " + (ped.getMesa() != null ? ped.getMesa().getId() : "N/A"));
                System.out.println("Detalles:");
                for (DetallePedido detalle : ped.getDetalles()) {
                    System.out.println("  Producto: " + detalle.getNombreProducto());
                    System.out.println("  Precio: " + detalle.getPrecioProducto());
                    System.out.println("  Cantidad: " + detalle.getCantidad());
                }
            }
        } else {
            System.out.println("No se obtuvieron pedidos.");
        }

        return tipope;
    }

    public String create() throws Exception {
        dao.create(pedido);
        return "/pedido/index.xhtml?faces-redirect=true";
    }

    public String update() {
        dao.update(pedido);
        return "/pedido/index.xhtml?faces-redirect=true";
    }

    public String delete() throws Exception {
        long id = this.pedido.getId();
        dao.delete(id);
        return "/pedido/index.xhtml?faces-redirect=true";
    }

    //obtener usuarioooos xdd
    public List<Usuario> getTipos() {
        if (tipos == null) {
            tipos = tipodao.getAll();
        }
        return tipos;
    }

    //obtener estados xdd
    public List<EstadoPedido> getEstados() {
        return Arrays.asList(EstadoPedido.values());
    }

    //para el número de mesas
    public List<Mesa> getMesas() {
        if (tmesa == null) {
            tmesa = mesadao.getAll();
        }
        return tmesa;
    }
    
    
    public void addDetalle() {
        detallesPedido.add(new DetallePedido());
    }

    public void removeDetalle(DetallePedido detalle) {
        detallesPedido.remove(detalle);
    }
}

