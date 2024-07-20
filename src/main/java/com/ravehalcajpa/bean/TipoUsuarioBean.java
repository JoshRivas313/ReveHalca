
package com.ravehalcajpa.bean;

import com.ravehalcajpa.model.TipoUsuario;
import com.ravehalcajpa.service.impl.TipoUsuarioDAO;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import java.util.List;

@Named("tusuarioBean")
@RequestScoped
public class TipoUsuarioBean {
private static final long serialVersionUID = 1L;

    @Inject
    private TipoUsuarioDAO dao;
    private TipoUsuario unidad = new TipoUsuario(); 

    public TipoUsuario getTipoUsuario() {
        return unidad;
    }

    public void setTipoUsuario(TipoUsuario unidad) {
        this.unidad = unidad;
    }
    
      //Redirecciones
    public String newTipoUsuario() {
        this.unidad = new TipoUsuario();
        return "add";
    }

    public String editTipoUsuario() {
        int  id = this.unidad.getId();
        this.unidad = dao.getById(id);
        return "edit";
    }
    
    public List<TipoUsuario> getAll() {
        try {
            return dao.getAll();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    
}
