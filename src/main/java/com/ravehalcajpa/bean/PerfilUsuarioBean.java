package com.ravehalcajpa.bean;

import com.ravehalcajpa.infra.security.LogonMB;
import com.ravehalcajpa.model.PerfilUsuario;
import com.ravehalcajpa.model.Usuario;
import com.ravehalcajpa.service.impl.PerfilUsuarioDAO;
import jakarta.annotation.ManagedBean;
import jakarta.enterprise.context.RequestScoped;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import java.io.File;
import java.io.InputStream;
import java.io.Serializable;
import java.util.HashMap;
import java.util.List;

import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.util.JRLoader;

@ManagedBean
@RequestScoped
@Named("pusuarioBean")
public class PerfilUsuarioBean implements Serializable {

    private static final long serialVersionUID = 1L;

    @Inject
    private PerfilUsuarioDAO dao;
    private PerfilUsuario perfilusuario = new PerfilUsuario();
    private List<PerfilUsuario> usu;

    @Inject
    private LogonMB logon;

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

        String opcion;

        if (this.perfilusuario != null) {
            System.out.println("No se necesita crear ");
            opcion = "edit";

        } else {

            System.out.println("Se debe crear");
            opcion = "create";
        }

        return opcion;
    }

    public List<PerfilUsuario> getAll() throws Exception {

        if (usu == null) {
            usu = dao.getAll();
        }

        return usu;
    }

    public PerfilUsuario getAllId() throws Exception {
        perfilusuario = dao.getByIdUsuario(logon.getUsuario().getId());

        return perfilusuario;

    }

    public String editarUsuarioUnitario() throws Exception {

        perfilusuario = dao.getByIdUsuario(logon.getUsuario().getId());

        return "editUnit";

    }

    public String update() {
        dao.update(perfilusuario);
        return "/perfilusuario/index.xhtml?faces-redirect=true";
    }
    
     public String updateUnit() {
        dao.update(perfilusuario);
         System.out.println("Perfil Usuario");
        return "/perfilusuario/indexmi.xhtml?faces-redirect=true";
    }
     

    public String crear() {

        dao.create(perfilusuario, 24);
        return "/perfilusuario/index.xhtml?faces-redirect=true";
    }

    

    

    public String exportarReporteUsuario() {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        String rutaArchivo = "";

        try {
            InputStream reporteEmpleadoStream = facesContext.getExternalContext().getResourceAsStream("/reportesJasper/Prueba.jasper");
            if (reporteEmpleadoStream == null) {
                System.out.println("Reporte Jasper no encontrado.");
                return null;
            }

            List<PerfilUsuario> reportesEmpleados = dao.getFilteredAttributes();
            if (reportesEmpleados.isEmpty()) {
                System.out.println("No hay datos para generar el reporte.");
                return null;
            }

            JasperReport reporteEmpleado = (JasperReport) JRLoader.loadObject(reporteEmpleadoStream);
            JRBeanCollectionDataSource ds = new JRBeanCollectionDataSource(reportesEmpleados);
            JasperPrint jasperPrint = JasperFillManager.fillReport(reporteEmpleado, new HashMap<>(), ds);

            // Generar un nombre de archivo único
            String nombreArchivo = "ReporteUsuarios1" + System.currentTimeMillis() + ".pdf";

            // Definir la ruta donde se guardará el archivo
            String directorioReportes = System.getProperty("user.home") + File.separator + "reportes";
            File directorio = new File(directorioReportes);
            if (!directorio.exists()) {
                directorio.mkdirs();
            }

            rutaArchivo = directorioReportes + File.separator + nombreArchivo;

            // Guardar el PDF en el servidor
            JasperExportManager.exportReportToPdfFile(jasperPrint, rutaArchivo);

            System.out.println("Reporte generado y guardado en: " + rutaArchivo);

            // Aquí puedes hacer lo que necesites con el archivo generado
            // Por ejemplo, podrías guardarlo en una base de datos, enviarlo por correo, etc.
            // Mostrar mensaje de éxito
            FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Éxito", "Reporte generado correctamente en el servidor.");
            facesContext.addMessage(null, message);

        } catch (Exception e) {
            System.out.println("Error al generar el reporte: " + e.getMessage());
            e.printStackTrace();
            FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "No se pudo generar el reporte.");
            facesContext.addMessage(null, message);
        }

        return null;
    }

}
