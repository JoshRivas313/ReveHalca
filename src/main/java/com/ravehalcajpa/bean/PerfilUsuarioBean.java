package com.ravehalcajpa.bean;

import com.ravehalcajpa.model.PerfilUsuario;
import com.ravehalcajpa.model.Usuario;
import com.ravehalcajpa.service.impl.PerfilUsuarioDAO;
import jakarta.annotation.ManagedBean;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import java.io.Serializable;
import java.util.List;

@ManagedBean
@RequestScoped
@Named("pusuarioBean")
public class PerfilUsuarioBean implements Serializable {

    private static final long serialVersionUID = 1L;

    @Inject
    private PerfilUsuarioDAO dao;
    private PerfilUsuario perfilusuario = new PerfilUsuario();
    private List<PerfilUsuario> usu;

    public PerfilUsuarioBean() {
        perfilusuario = new PerfilUsuario();
        perfilusuario.setUsuario(new Usuario());
    }

    public PerfilUsuarioDAO getDao() {
        return dao;
    }

    public void setDao(PerfilUsuarioDAO dao) {
        this.dao = dao;
    }

    public PerfilUsuario getPerfilusuario() {
        return perfilusuario;
    }

    public void setPerfilusuario(PerfilUsuario perfilusuario) {
        this.perfilusuario = perfilusuario;
    }

    public List<PerfilUsuario> getUsu() {
        return usu;
    }

    public void setUsu(List<PerfilUsuario> usu) {
        this.usu = usu;
    }

    public String editPerfilUsuario() throws Exception {
        long id = this.perfilusuario.getId();
        this.perfilusuario = dao.getById(id);
        return "edit";
    }
    
    public String edityouPerfilUsuario() throws Exception {
        long id = this.perfilusuario.getId();
        this.perfilusuario = dao.getById(id);
        return "edityou";
    }

    public List<PerfilUsuario> getAll() throws Exception {

        if (usu == null) {
            usu = dao.getAll();
        }

        return usu;
    }

        public String update() {
        dao.update(perfilusuario);
           return "/perfilusuario/index.xhtml?faces-redirect=true";
    }
    
}
