package com.ravehalcajpa.bean;

import com.ravehalcajpa.model.Cliente;
import com.ravehalcajpa.model.Comprobante;
import com.ravehalcajpa.model.DetallePedido;
import com.ravehalcajpa.model.Mesa;
import com.ravehalcajpa.model.Pedido;
import com.ravehalcajpa.model.Usuario;
import com.ravehalcajpa.service.MetodoPago;
import com.ravehalcajpa.service.impl.ClienteDAO;
import com.ravehalcajpa.service.impl.ComprobanteDAO;
import com.ravehalcajpa.service.impl.MesaDAO;
import com.ravehalcajpa.service.impl.PedidoDAO;
import com.ravehalcajpa.service.impl.UsuarioDAO;
import jakarta.annotation.ManagedBean;
import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.RequestScoped;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import java.io.Serializable;
import java.sql.Time;
import java.util.*;
import java.util.stream.Collectors;

@ManagedBean
@RequestScoped
@Named("comprobanteBean")

public class ComprobanteBean implements Serializable {

    private static final long serialVersionUID = 1L;
    @Inject
    private ComprobanteDAO dao;
    private Comprobante comprobante = new Comprobante();

    private List<Comprobante> Comprobantecat;
    private List<Usuario> mozos;

    @Inject
    private UsuarioDAO userDAO;

    private Long selectedMesaId;
    private Long selectedPedidoId;
    private String pedidoInfo;
    private List<DetallePedido> detallesPedido;
    private double total;
    
    @Inject
     private ClienteDAO clientedao;  

    private List<Mesa> mesas;

    @Inject
    private MesaDAO mesaDAO;

    @Inject
    private PedidoDAO pedidoDAO;

    private int selectedUserId;

    public int getSelectedUserId() {
        return selectedUserId;
    }

    public void setSelectedUserId(int selectedUserId) {
        this.selectedUserId = selectedUserId;
    }

    @PostConstruct
    public void init() {
        mesas = mesaDAO.getAll();
    }

    public List<Mesa> getMesas() {
        return mesas;
    }

    public UsuarioDAO getUserDAO() {
        return userDAO;
    }

    public void setUserDAO(UsuarioDAO userDAO) {
        this.userDAO = userDAO;
    }

    public Long getSelectedMesaId() {
        return selectedMesaId;
    }

    public void setSelectedMesaId(Long selectedMesaId) {
        this.selectedMesaId = selectedMesaId;
    }

    public String getPedidoInfo() {
        return pedidoInfo;
    }

    public void setPedidoInfo(String pedidoInfo) {
        this.pedidoInfo = pedidoInfo;
    }

    public List<DetallePedido> getDetallesPedido() {
        return detallesPedido;
    }

    public void setDetallesPedido(List<DetallePedido> detallesPedido) {
        this.detallesPedido = detallesPedido;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public PedidoDAO getPedidoDAO() {
        return pedidoDAO;
    }

    public void setPedidoDAO(PedidoDAO pedidoDAO) {
        this.pedidoDAO = pedidoDAO;
    }

    public ComprobanteBean() {
        comprobante = new Comprobante();
    }

    public ComprobanteDAO getDao() {
        return dao;
    }

    public void setDao(ComprobanteDAO dao) {
        this.dao = dao;
    }

    public Comprobante getComprobante() {
        return comprobante;
    }

    public void setComprobante(Comprobante Comprobante) {
        this.comprobante = Comprobante;
    }

    public List<Comprobante> getComprobantecat() {
        if (Comprobantecat == null) {
            Comprobantecat = dao.getAll();
        }
        return Comprobantecat;
    }

    public void setComprobantecat(List<Comprobante> Comprobantecat) {
        this.Comprobantecat = Comprobantecat;
    }

    public String newComprobante() {
        this.comprobante = new Comprobante();
        return "add";
    }

    public String editComprobante() throws Exception {
        Long id = this.comprobante.getId();
        this.comprobante = dao.getById(id);

        return "edit";
    }

    public List<Comprobante> getAll() throws Exception {

        if (Comprobantecat == null) {
            Comprobantecat = dao.getAll();
        }

        return Comprobantecat;
    }
    
    

    public String create() throws Exception {
        
        Cliente cli = new Cliente(comprobante.getNombrecliente(),comprobante.getApellidocliente());

        selectedUserId = this.comprobante.getIduser().getId();

        
        Usuario selectedUser = userDAO.getById(selectedUserId);

        
        this.comprobante.setIduser(selectedUser);

        this.comprobante.setIdped(pedidoDAO.getById(selectedPedidoId));
        this.comprobante.setHora(new Time(System.currentTimeMillis()));
        this.comprobante.setTotal(this.total);
        dao.create(comprobante);
        clientedao.create(cli);
        return "/comprobante/index.xhtml?faces-redirect=true";
    }

    public String update() throws Exception {
        dao.update(comprobante);
        return "/comprobante/index.xhtml?faces-redirect=true";
    }

    public String delete() throws Exception {
        long id = this.comprobante.getId();
        dao.delete(id);
        return "/comprobante/index.xhtml?faces-redirect=true";
    }

    //FILTRAR TIPO USUARIO SOLO MOZOS
    public List<Usuario> getMozos() {
        if (mozos == null) {
            mozos = userDAO.getAll().stream()
                    .filter(u -> u.getTipoUsuario().getNombre().equals("Mozo"))
                    .collect(Collectors.toList());
        }
        return mozos;
    }

    public List<MetodoPago> getMetodoPago() {
        return Arrays.asList(MetodoPago.values());
    }

    public void obtenerPedido() {
        Mesa mesa = mesaDAO.getById(selectedMesaId);
        if (mesa != null) {
            Pedido pedido = pedidoDAO.getPedidoByMesaId(mesa.getId());
            if (pedido != null) {
                selectedPedidoId = pedido.getId();
                this.detallesPedido=pedido.getDetalles();
                this.total=calcularTotal(detallesPedido);
            } else {
                selectedPedidoId = null; // No se encontró pedido
            }
        }
    }

    private double calcularTotal(List<DetallePedido> detalles) {
        return detalles.stream()
                .mapToDouble(d -> d.getPrecioProducto() * d.getCantidad())
                .sum();
    }

}
