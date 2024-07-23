package com.ravehalcajpa.bean;

import com.ravehalcajpa.model.CategoriaProducto;
import com.ravehalcajpa.model.Producto;
import com.ravehalcajpa.service.EstadoProducto;
import com.ravehalcajpa.service.Exportacion;
import com.ravehalcajpa.service.impl.CategoriaProductoDAO;
import com.ravehalcajpa.service.impl.ProductoDAO;
import jakarta.annotation.ManagedBean;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import java.io.Serializable;
import java.util.*;

@ManagedBean
@RequestScoped
@Named("productoBean")

public class ProductoBean implements Serializable {

    private static final long serialVersionUID = 1L;
    @Inject
    private ProductoDAO dao;
    private Producto producto = new Producto();
    private List<Producto> productocat;
    private Exportacion ex = new Exportacion();
    @Inject
    private CategoriaProductoDAO catdao;
    private List<CategoriaProducto> categorias;

    public ProductoBean() {
        producto = new Producto();
        producto.setIdcat(new CategoriaProducto());
    }

    public ProductoDAO getDao() {
        return dao;
    }

    public void setDao(ProductoDAO dao) {
        this.dao = dao;
    }

    public Producto getProducto() {
        return producto;
    }

    public void setProducto(Producto producto) {
        this.producto = producto;
    }

    public List<Producto> getProductocat() {
        return productocat;
    }

    public void setProductocat(List<Producto> productocat) {
        this.productocat = productocat;
    }

    public String newProducto() {
        this.producto = new Producto();
        this.producto.setIdcat(new CategoriaProducto());
        return "add";
    }

    public String editProducto() throws Exception {
        Long id = this.producto.getId();
        this.producto = dao.getById(id);
        System.out.println("sssssss" + id);
        System.out.println("sssssss" + id);
        System.out.println("sssssss" + id);
        System.out.println("sssssss" + id);
        System.out.println("sssssss" + id);
        System.out.println("sssssss" + id);
        System.out.println("sssssss" + id);
        System.out.println("sssssss" + id);
        System.out.println("sssssss" + id);

        return "edit";
    }

    public List<Producto> getAll() throws Exception {

        if (productocat == null) {
            productocat = dao.getAll();
        }

        return productocat;
    }

    public String create() throws Exception {
        dao.create(producto);
        return "/producto/index.xhtml?faces-redirect=true";
    }

    public String update() throws Exception {
        dao.update(producto);
        return "/producto/index.xhtml?faces-redirect=true";
    }

    public String delete() throws Exception {
        long id = this.producto.getId();
        dao.delete(id);
        return "/producto/index.xhtml?faces-redirect=true";
    }

    //obtener categorias xdd
    public List<CategoriaProducto> getCategorias() {
        if (categorias == null) {
            categorias = catdao.getAll();
        }
        return categorias;
    }

    //obtener estados xdd
    public List<EstadoProducto> getEstados() {
        return Arrays.asList(EstadoProducto.values());
    }

    public boolean exportarProductos() {

        if (dao != null) {
            return ex.exportarProducto(dao);
        } else {
            System.out.println("DAO no está inicializado.");
            return false;
        }
    }

}
