package com.ravehalcajpa.bean;

import com.ravehalcajpa.model.Comprobante;
import com.ravehalcajpa.model.Ingreso;
import com.ravehalcajpa.service.Exportacion;
import com.ravehalcajpa.service.impl.ComprobanteDAO;
import com.ravehalcajpa.service.impl.IngresoDAO;

import jakarta.annotation.ManagedBean;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import java.io.Serializable;
import java.util.*;
import java.util.stream.Collectors;

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
    private List<Comprobante> comprobantes;

    public List<Ingreso> getAll() throws Exception {

        this.autocom(dao);

        if (Ingreso == null) {
            Ingreso = dao.getAll();
        }

        return Ingreso;
    }

    public void autocom(IngresoDAO ingresoDAO) {
        comprobantes = daocom.getAll();
        List<Ingreso> existingIngresos = ingresoDAO.getAll();
        Set<Long> existingComprobanteIds = existingIngresos.stream()
                .map(ingreso -> ingreso.getComprobante().getId())
                .collect(Collectors.toSet());

        for (Comprobante comprobante : comprobantes) {
            if (!existingComprobanteIds.contains(comprobante.getId())) {
                Ingreso ingreso = new Ingreso();
                ingreso.setComprobante(comprobante);
                ingreso.setFecha(comprobante.getFecha());
                ingreso.setTotal(comprobante.getTotal());
                dao.create(ingreso);
            }
        }
    }

    public boolean exportarIngresos() {
        Exportacion ex = new Exportacion();

        if (dao != null) {
            return ex.exportarProducto(dao);
        } else {
            System.out.println("DAO no está inicializado.");
            return false;
        }
    }

}
