
package com.ravehalcajpa.bean;

import com.ravehalcajpa.model.Inventario;
import com.ravehalcajpa.model.Producto;
import com.ravehalcajpa.service.impl.InventarioDAO;
import com.ravehalcajpa.service.impl.ProductoDAO;
import jakarta.annotation.ManagedBean;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import java.io.Serializable;
import java.util.List;

@ManagedBean
@RequestScoped
@Named("inventarioBean")
public class InventarioBean implements Serializable{
    
     private static final long serialVersionUID = 1L;
    @Inject
    private InventarioDAO dao;
    private Inventario inventario;
    
    @Inject
    private ProductoDAO prodDAO;
    private List<Producto> products;

    public InventarioBean() {
        inventario = new Inventario();
        inventario.setIdprod(new Producto());
    }

    public InventarioDAO getDao() {
        return dao;
    }

    public void setDao(InventarioDAO dao) {
        this.dao = dao;
    }

    public Inventario getInventario() {
        return inventario;
    }

    public void setInventario(Inventario inventario) {
        this.inventario = inventario;
    }
    
    public String newInventario() {
        this.inventario = new Inventario();
        this.inventario.setIdprod(new Producto());
        return "add";
    }
    
    
    
    public List<Inventario> getAll() {
        try {
            return dao.getAll();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    
    public String create() throws Exception {
        dao.create(inventario);
        return "/inventario/index.xhtml?faces-redirect=true";
    }

    
    public List<Producto> getProductos() throws Exception{
        if (products == null) {
            products = prodDAO.getAll();
        }
        return products;
    }
    
    
    
    
    
    
    
}
