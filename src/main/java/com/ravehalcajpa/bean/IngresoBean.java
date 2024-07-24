package com.ravehalcajpa.bean;

import com.ravehalcajpa.model.Comprobante;
import com.ravehalcajpa.model.Ingreso;
import com.ravehalcajpa.service.impl.ComprobanteDAO;
import com.ravehalcajpa.service.impl.IngresoDAO;

import jakarta.annotation.ManagedBean;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import java.io.Serializable;
import java.util.*;

@ManagedBean
@RequestScoped
@Named("ingresoBean")

public class IngresoBean implements Serializable {

    private static final long serialVersionUID = 1L;

    @Inject
    private IngresoDAO dao;
    private List<Ingreso> Ingreso;

    @Inject
    private ComprobanteDAO daocom;
    private Comprobante com;
    private List<Comprobante> comprobantes;

    public List<Ingreso> getAll() throws Exception {
        
        this.autocom(dao);

        if (Ingreso == null) {
            Ingreso = dao.getAll();
        }

        return Ingreso;
    }

    public void autocom(IngresoDAO Ingreso) {

        comprobantes = daocom.getAll();

        if (Ingreso.getAll() == null|| Ingreso.getAll().isEmpty()) {
            for (Comprobante comprobante : comprobantes) {
                Ingreso ingreso = new Ingreso();
                ingreso.setComprobante(comprobante);
                ingreso.setFecha(comprobante.getFecha());
                ingreso.setTotal(comprobante.getTotal());
                dao.create(ingreso);
            }

        } else {

            for (Comprobante comprobante : comprobantes) {
                if (dao.getIngresoByComprobante(comprobante.getId()) == null) {
                    Ingreso ingreso = new Ingreso();
                    ingreso.setComprobante(comprobante);
                    ingreso.setFecha(comprobante.getFecha());
                    ingreso.setTotal(comprobante.getTotal());
                    dao.create(ingreso);
                }

            }

            
        }
    }

}
