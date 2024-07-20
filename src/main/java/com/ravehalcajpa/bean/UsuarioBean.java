package com.ravehalcajpa.bean;

import com.ravehalcajpa.model.TipoUsuario;
import com.ravehalcajpa.model.Usuario;
import com.ravehalcajpa.service.EstadoUser;
import com.ravehalcajpa.service.impl.TipoUsuarioDAO;
import com.ravehalcajpa.service.impl.UsuarioDAO;
import jakarta.annotation.ManagedBean;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import java.io.Serializable;
import java.util.Arrays;
import java.util.List;

@ManagedBean
@RequestScoped
@Named("usuarioBean")
public class UsuarioBean implements Serializable {

    private static final long serialVersionUID = 1L;
    @Inject
    private UsuarioDAO dao;
    private Usuario usuario = new Usuario();
    private List<Usuario> tipousu;
    @Inject
    private TipoUsuarioDAO tipodao;
    private List<TipoUsuario> tipos;

    public UsuarioBean() {
        usuario = new Usuario();
        usuario.setTipoUsuario(new TipoUsuario());
    }

    public UsuarioDAO getDao() {
        return dao;
    }

    public void setDao(UsuarioDAO dao) {
        this.dao = dao;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public List<Usuario> getTipousu() {
        return tipousu;
    }

    public void setTipousu(List<Usuario> tipousu) {
        this.tipousu = tipousu;
    }

    public String newUsuario() {
        this.usuario = new Usuario();
        this.usuario.setTipoUsuario(new TipoUsuario());
        return "add";
    }

    public String editUsuario() throws Exception {
        int id = this.usuario.getId();
        this.usuario = dao.getById(id);
        System.out.println("sssssss"+id);
        System.out.println("sssssss"+id);
        System.out.println("sssssss"+id);
        System.out.println("sssssss"+id);
        System.out.println("sssssss"+id);
        System.out.println("sssssss"+id);
        System.out.println("sssssss"+id);
        System.out.println("sssssss"+id);
        System.out.println("sssssss"+id);
        return "edit";
    }

    public List<Usuario> getAll() throws Exception {

        if (tipousu == null) {
            tipousu = dao.getAll();
        }

        return tipousu;
    }

    public String create() throws Exception {
        dao.create(usuario);
        return "/usuario/index.xhtml?faces-redirect=true";
    }

    public String update() {
        dao.update(usuario);
        return "/usuario/index.xhtml?faces-redirect=true";
    }

    public String delete() throws Exception {
        int id = this.usuario.getId();
        dao.delete(id);
        return "/usuario/index.xhtml?faces-redirect=true";
    }

    //obtener categorias xdd
    public List<TipoUsuario> getTipos() {
        if (tipos == null) {
            tipos = tipodao.getAll();
        }
        return tipos;
    }
    
    //obtener estados xdd
    public List<EstadoUser> getEstados() {
        return Arrays.asList(EstadoUser.values());
    }
}
