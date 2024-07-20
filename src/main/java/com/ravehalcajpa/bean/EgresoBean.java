package com.ravehalcajpa.bean;
import com.ravehalcajpa.model.Egreso;
import com.ravehalcajpa.service.impl.EgresoDAO;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import java.io.Serializable;
import java.util.List;


@Named("egresoBean")
@RequestScoped
public class EgresoBean implements Serializable {
    
    private static final long serialVersionUID = 1L;

    @Inject
    private EgresoDAO dao;
    private Egreso unidad = new Egreso();

    public Egreso getEgreso() {
        return unidad;
    }

    public void setEgreso(Egreso unidad) {
        this.unidad = unidad;
    }

    //Redirecciones
    public String newEgreso() {
        this.unidad = new Egreso();
        return "add";
    }

    public String editEgreso() {
        Long  id = this.unidad.getId();
        this.unidad = dao.getById(id);
        return "edit";
    }

   

    public List<Egreso> getAll() {
        try {
            return dao.getAll();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public String create() {
        dao.create(unidad);
        return "/egresos/index.xhtml?faces-redirect=true";
    }

    public String update() {
        dao.update(unidad);
           return "/egresos/index.xhtml?faces-redirect=true";
    }
     
    public String delete() {
        long  id = this.unidad.getId();
        dao.delete(id);
        return "/egresos/index.xhtml?faces-redirect=true";
    }

}
