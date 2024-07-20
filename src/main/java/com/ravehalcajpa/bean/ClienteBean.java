
package com.ravehalcajpa.bean;

import com.ravehalcajpa.model.Cliente;
import com.ravehalcajpa.service.impl.ClienteDAO;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import java.io.Serializable;
import java.util.List;

@Named("clienteBean")
@RequestScoped
public class ClienteBean implements Serializable {
     private static final long serialVersionUID = 1L;

     @Inject
    private ClienteDAO dao;
    private Cliente unidad = new Cliente();

    public Cliente getCliente() {
        return unidad;
    }

    public void setCliente(Cliente unidad) {
        this.unidad = unidad;
    }
    
    //Redirecciones

    public String editCliente() {
        Long  id = this.unidad.getId();
        this.unidad = dao.getById(id);
        return "edit";
    }
    
    public List<Cliente> getAll() {
        try {
            return dao.getAll();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }
    
    public String update() {
        dao.update(unidad);
           return "/clientes/index.xhtml?faces-redirect=true";
    }
     
    
}
