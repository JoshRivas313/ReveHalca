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
import jakarta.enterprise.context.SessionScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import java.io.Serializable;
import java.sql.Time;
import java.util.*;
import java.util.stream.Collectors;

@ManagedBean
@SessionScoped
@Named("comprobanteBean")

public class ComprobanteBean implements Serializable {

    private static final long serialVersionUID = 1L;
    @Inject
    private ComprobanteDAO dao;
    private List<Comprobante> Comprobantecat;
    private Comprobante comprobante = new Comprobante();

    @Inject
    private UsuarioDAO userDAO;
    private List<Usuario> mozos;
    private int selectedUserId;
    private double total;

    @Inject
    private ClienteDAO clientedao;
    private Cliente clien;

    @Inject
    private MesaDAO mesaDAO;
    private List<Mesa> mesas;
    private Long selectedMesaId;

    @Inject
    private PedidoDAO pedidoDAO;
    private Long selectedPedidoId;
    private List<DetallePedido> detallesPedido;

    @PostConstruct
    public void init() {
        mesas = mesaDAO.getAll();
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

    public void setComprobante(Comprobante comprobante) {
        this.comprobante = comprobante;
    }

    public UsuarioDAO getUserDAO() {
        return userDAO;
    }

    public void setUserDAO(UsuarioDAO userDAO) {
        this.userDAO = userDAO;
    }

    public int getSelectedUserId() {
        return selectedUserId;
    }

    public void setSelectedUserId(int selectedUserId) {
        this.selectedUserId = selectedUserId;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public ClienteDAO getClientedao() {
        return clientedao;
    }

    public void setClientedao(ClienteDAO clientedao) {
        this.clientedao = clientedao;
    }

    public MesaDAO getMesaDAO() {
        return mesaDAO;
    }

    public void setMesaDAO(MesaDAO mesaDAO) {
        this.mesaDAO = mesaDAO;
    }

    public List<Mesa> getMesas() {
        return mesas;
    }

    public void setMesas(List<Mesa> mesas) {
        this.mesas = mesas;
    }

    public Long getSelectedMesaId() {
        return selectedMesaId;
    }

    public void setSelectedMesaId(Long selectedMesaId) {
        this.selectedMesaId = selectedMesaId;
    }

    public PedidoDAO getPedidoDAO() {
        return pedidoDAO;
    }

    public void setPedidoDAO(PedidoDAO pedidoDAO) {
        this.pedidoDAO = pedidoDAO;
    }

    public Long getSelectedPedidoId() {
        return selectedPedidoId;
    }

    public void setSelectedPedidoId(Long selectedPedidoId) {
        this.selectedPedidoId = selectedPedidoId;
    }

    public List<DetallePedido> getDetallesPedido() {
        return detallesPedido;
    }

    public void setDetallesPedido(List<DetallePedido> detallesPedido) {
        this.detallesPedido = detallesPedido;
    }

    public List<Comprobante> getComprobantecat() {
        if (Comprobantecat == null) {
            Comprobantecat = dao.getAll();
        }
        return Comprobantecat;
    }

    public String newComprobante() {
        this.comprobante = new Comprobante();
        return "add";
    }

    public String editComprobante() throws Exception {
        Long id = this.comprobante.getId();
        this.comprobante = dao.getById(id);
        this.selectedMesaId = comprobante.getIdped().getMesa().getId();

        this.clien = clientedao.getByNombre(this.comprobante.getNombrecliente(), this.comprobante.getApellidocliente());

        System.out.println("el cliente obtenido" + clien);
        System.out.println("el cliente obtenido" + clien);
        System.out.println("el cliente obtenido" + clien);
        System.out.println("el cliente obtenido" + clien);
        System.out.println("el cliente obtenido" + clien);
        System.out.println("el cliente obtenido" + clien);
        return "edit";
    }

    public List<Comprobante> getAll() throws Exception {

        if (Comprobantecat == null) {
            Comprobantecat = dao.getAll();
        }

        return Comprobantecat;
    }

    public String create() throws Exception {

        Mesa mesa = mesaDAO.getById(selectedMesaId);

        Pedido pedido = pedidoDAO.getPedidoByMesaId(mesa.getId());

        this.selectedPedidoId = pedido.getId();

        this.detallesPedido = pedido.getDetalles();
        this.total = calcularTotal(detallesPedido);

        Cliente cli = new Cliente(comprobante.getNombrecliente(), comprobante.getApellidocliente());

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
        
        

        Mesa mesa = mesaDAO.getById(selectedMesaId);

        Pedido pedido = pedidoDAO.getPedidoByMesaId(mesa.getId());

        this.selectedPedidoId = pedido.getId();

        this.detallesPedido = pedido.getDetalles();
        this.total = calcularTotal(detallesPedido);

        this.clien.setNombreCliente(comprobante.getNombrecliente());
        this.clien.setApellidoCliente(comprobante.getApellidocliente());
        Usuario uhm = this.comprobante.getIduser();
        

        this.comprobante.setHora(new Time(System.currentTimeMillis()));
        this.comprobante.setIduser(uhm);
        this.comprobante.setIdped(pedidoDAO.getById(selectedPedidoId));
        this.comprobante.setTotal(this.total);
        dao.update(comprobante);
        clientedao.update(clien);
        Comprobantecat = null;
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
                    .filter(u -> u.getTipoUsuario().getNombre().equals("Cajero"))
                    .collect(Collectors.toList());
        }
        return mozos;
    }

    public List<MetodoPago> getMetodoPago() {
        return Arrays.asList(MetodoPago.values());
    }

    private double calcularTotal(List<DetallePedido> detalles) {
        return detalles.stream()
                .mapToDouble(d -> d.getPrecioProducto() * d.getCantidad())
                .sum();
    }

}
