
package com.ravehalcajpa.bean;

import com.ravehalcajpa.model.Comprobante;
import com.ravehalcajpa.service.impl.ComprobanteDAO;
import jakarta.annotation.ManagedBean;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import java.io.Serializable;
import java.util.*;

@ManagedBean
@RequestScoped
@Named("comprobanteBean")

public class ComprobanteBean implements Serializable {

    private static final long serialVersionUID = 1L;
    @Inject
    private ComprobanteDAO dao;
    private Comprobante comprobante = new Comprobante();
    private List<Comprobante> Comprobantecat;

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
        dao.create(comprobante);
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

    

}
