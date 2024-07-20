package com.ravehalcajpa.bean;

import com.ravehalcajpa.model.Mesa;
import com.ravehalcajpa.service.impl.MesaDAO;
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

}
