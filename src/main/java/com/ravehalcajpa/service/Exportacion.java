package com.ravehalcajpa.service;
import com.ravehalcajpa.model.Producto;
import com.ravehalcajpa.service.impl.ProductoDAO;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import java.io.File;
import java.io.FileOutputStream;
import java.util.List;

public class Exportacion {
    
    public boolean exportarProducto(ProductoDAO dao){
        List<Producto> productos = dao.getAll();
        
        if (productos == null || productos.isEmpty()) {
            System.out.println("No hay productos para exportar.");
            return false;
        }
        XSSFWorkbook workbook = new XSSFWorkbook();
        XSSFSheet sheet = workbook.createSheet("Productos");
        int rownum = 0;
        Row headerRow = sheet.createRow(rownum++);
        String[] headers = {"ID", "Nombre", "Estado", "Precio"};
        for (int i = 0; i < headers.length; i++) {
            Cell cell = headerRow.createCell(i);
            cell.setCellValue(headers[i]);
        }
        for (Producto producto : productos) {
            Row row = sheet.createRow(rownum++);
            row.createCell(0).setCellValue(producto.getId());
            row.createCell(1).setCellValue(producto.getNombre());
            row.createCell(2).setCellValue(producto.getEstado().toString());
            row.createCell(3).setCellValue(producto.getPrecio());
        }
        
        String userHome = System.getProperty("user.home");
        File netBeansProjectsDir = new File(userHome, "NetBeansProjects");
        File exportsDir = new File(netBeansProjectsDir, "exports");
        if (!exportsDir.exists()) {
            exportsDir.mkdirs();
        }
        
        File archivo = new File(exportsDir, "productos.xlsx");
        
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