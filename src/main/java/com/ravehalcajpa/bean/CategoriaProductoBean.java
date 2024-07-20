
package com.ravehalcajpa.bean;

import com.ravehalcajpa.model.CategoriaProducto;
import com.ravehalcajpa.service.impl.CategoriaProductoDAO;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import java.util.List;

@Named("cproductoBean")
@RequestScoped
public class CategoriaProductoBean {
    private static final long serialVersionUID = 1L;

    @Inject
    private CategoriaProductoDAO dao;
    private CategoriaProducto unidad = new CategoriaProducto();

    public CategoriaProducto getCategoriaProducto() {
        return unidad;
    }

    public void setCategoriaProducto(CategoriaProducto unidad) {
        this.unidad = unidad;
    }

    //Redirecciones
    public String newCategoriaProducto() {
        this.unidad = new CategoriaProducto();
        return "add";
    }

    public String editCategoriaProducto() {
        Long  id = this.unidad.getId();
        this.unidad = dao.getById(id);
        return "edit";
    }

   

    public List<CategoriaProducto> getAll() {
        try {
            return dao.getAll();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public String create() {
        dao.create(unidad);
        return "/categoriaproductos/index.xhtml?faces-redirect=true";
    }

    public String update() {
        dao.update(unidad);
           return "/categoriaproductos/index.xhtml?faces-redirect=true";
    }
     
    public String delete() {
        long  id = this.unidad.getId();
        dao.delete(id);
        return "/categoriaproductos/index.xhtml?faces-redirect=true";
    }

}
