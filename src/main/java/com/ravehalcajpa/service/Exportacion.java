package com.ravehalcajpa.service;
import com.ravehalcajpa.model.Ingreso;
import com.ravehalcajpa.model.Producto;
import com.ravehalcajpa.service.impl.IngresoDAO;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import java.io.File;
import java.io.FileOutputStream;
import java.util.List;

public class Exportacion {
    
    public boolean exportarProducto(IngresoDAO dao){
        List<Ingreso> ingresos = dao.getAll();
        
        if (ingresos == null || ingresos.isEmpty()) {
            System.out.println("No hay Ingresos para exportar.");
            return false;
        }
        XSSFWorkbook workbook = new XSSFWorkbook();
        XSSFSheet sheet = workbook.createSheet("Ingresos");
        int rownum = 0;
        Row headerRow = sheet.createRow(rownum++);
        String[] headers = {"ID", "ID Comprobante", "Fecha", "Monto"};
        for (int i = 0; i < headers.length; i++) {
            Cell cell = headerRow.createCell(i);
            cell.setCellValue(headers[i]);
        }
        for (Ingreso ingreso : ingresos) {
            Row row = sheet.createRow(rownum++);
            row.createCell(0).setCellValue(ingreso.getId());
            row.createCell(1).setCellValue(ingreso.getComprobante().getId());
            row.createCell(2).setCellValue(ingreso.getFecha().toString());
            row.createCell(3).setCellValue(ingreso.getTotal());
        }
        
        String userHome = System.getProperty("user.home");
        File netBeansProjectsDir = new File(userHome, "NetBeansProjects");
        File exportsDir = new File(netBeansProjectsDir, "exports");
        if (!exportsDir.exists()) {
            exportsDir.mkdirs();
        }
        
        File archivo = new File(exportsDir, "ingresos.xlsx");
        
        try (FileOutputStream salida = new FileOutputStream(archivo)) {
            workbook.write(salida);
            System.out.println("Archivo Excel creado exitosamente.");
            System.out.println("Ubicación del archivo: " + archivo.getAbsolutePath());
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}