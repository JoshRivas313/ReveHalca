package com.ravehalcajpa.bean;

import com.ravehalcajpa.model.Mesa;
import com.ravehalcajpa.service.impl.MesaDAO;
import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import java.io.Serializable;
import java.util.List;

@Named("mesaBean")
@RequestScoped
public class MesaBean implements Serializable {

    private static final long serialVersionUID = 1L;

    @Inject
    private MesaDAO dao;
    private Mesa unidad = new Mesa();
    private List<Mesa> numesa;

    public MesaBean() {
        unidad =new Mesa();
    }

      @PostConstruct
    public void init() {
        getIdmesas(); // Asegúrate de llamar a getMozo durante la inicialización
    }
    
    public MesaDAO getDao() {
        return dao;
    }

    public void setDao(MesaDAO dao) {
        this.dao = dao;
    }

    public Mesa getUnidad() {
        return unidad;
    }

    public void setUnidad(Mesa unidad) {
        this.unidad = unidad;
    }

    public List<Mesa> getNumesa() {
        return numesa;
    }

    public void setNumesa(List<Mesa> numesa) {
        this.numesa = numesa;
    }

    public Mesa getMesa() {
        return unidad;
    }

    public void setMesa(Mesa unidad) {
        this.unidad = unidad;
    }

    //Redirecciones
    public String newMesa() {
        this.unidad = new Mesa();
        return "add";
    }

    public String editMesa() {
        Long id = this.unidad.getId();
        this.unidad = dao.getById(id);
        return "edit";
    }

    public List<Mesa> getAll() {
        try {
            return dao.getAll();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public String create() {
        unidad.setEstado("Activo");
        dao.create(unidad);
        return "/mesas/index.xhtml?faces-redirect=true";
    }

    public String update() {
        dao.update(unidad);
        return "/mesas/index.xhtml?faces-redirect=true";
    }

    public String delete() {
        long id = this.unidad.getId();
        dao.delete(id);
        return "/mesas/index.xhtml?faces-redirect=true";
    }

public void getIdmesas() {
    System.out.println("Llamada a getMesa");
    numesa = dao.getAll();
    if (numesa != null) {
        System.out.println("Mesas cargadas: " + numesa.size());
        for (Mesa mesa : numesa) {
            System.out.println("Mesa ID: " + mesa.getId());
        }
    } else {
        System.out.println("No se encontraron mesas.");
    }
}
}
