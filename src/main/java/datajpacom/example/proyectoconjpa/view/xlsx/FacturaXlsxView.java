package datajpacom.example.proyectoconjpa.view.xlsx;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.view.document.AbstractXlsxView;

import datajpacom.example.proyectoconjpa.entities.Factura;
import datajpacom.example.proyectoconjpa.entities.ItemFactura;

@Component("factura/ver.xlsx")
public class FacturaXlsxView extends AbstractXlsxView {

    @Override
    protected void buildExcelDocument(Map<String, Object> model, Workbook workbook, HttpServletRequest request,
            HttpServletResponse response) throws Exception {
        // TODO Auto-generated method stub
        response.setHeader("Content-Disposition", "attachment; filename=\"factura_view.xlsx\"");
        Factura factura = (Factura) model.get("factura");
        Sheet sheet = workbook.createSheet("Factura");

        Row row = sheet.createRow(0);
        Cell cell = row.createCell(0);

        cell.setCellValue("Datos del cliente");
        row = sheet.createRow(1);
        cell = row.createCell(0);
        cell.setCellValue(factura.getCliente().getNombre() + " " + factura.getCliente().getApellido());

        row = sheet.createRow(2);
        cell = row.createCell(0);
        cell.setCellValue(factura.getCliente().getEmail());

        // otra forma
        sheet.createRow(4).createCell(0).setCellValue("Datos de la factura");
        sheet.createRow(5).createCell(0).setCellValue("Folio " + factura.getId());
        sheet.createRow(6).createCell(0).setCellValue("Descripcion " + factura.getDescripcion());
        sheet.createRow(7).createCell(0).setCellValue("Fecha " + factura.getCreateAt());

        CellStyle theaderStyle = workbook.createCellStyle();
        theaderStyle.setBorderBottom(BorderStyle.MEDIUM);
        theaderStyle.setBorderTop(BorderStyle.MEDIUM);
        theaderStyle.setBorderLeft(BorderStyle.MEDIUM);
        theaderStyle.setBorderRight(BorderStyle.MEDIUM);
        theaderStyle.setFillForegroundColor(IndexedColors.GOLD.index);
        theaderStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);


        CellStyle tbodyStyle = workbook.createCellStyle();
        tbodyStyle.setBorderBottom(BorderStyle.THIN);
        tbodyStyle.setBorderTop(BorderStyle.THIN);
        tbodyStyle.setBorderLeft(BorderStyle.THIN);
        tbodyStyle.setBorderRight(BorderStyle.THIN);

        Row header = sheet.createRow(9);
        header.createCell(0).setCellValue("Producto");
        header.createCell(1).setCellValue("Precio");
        header.createCell(2).setCellValue("Cantidad");
        header.createCell(3).setCellValue("Total");

        header.getCell(0).setCellStyle(theaderStyle);
        header.getCell(1).setCellStyle(theaderStyle);
        header.getCell(2).setCellStyle(theaderStyle);
        header.getCell(3).setCellStyle(theaderStyle);
        int rowNum = 10; // Empieza en esta porque el header va en el 9
        for (ItemFactura item : factura.getItems()) {
            Row fila = sheet.createRow(rowNum++);
            cell = fila.createCell(0);
            cell.setCellValue(item.getProducto().getNombre());
            cell.setCellStyle(tbodyStyle);

            cell = fila.createCell(1);
            cell.setCellValue(item.getProducto().getPrecio());
            cell.setCellStyle(tbodyStyle);

            cell = fila.createCell(2);
            cell.setCellValue(item.getCantidad());
            cell.setCellStyle(tbodyStyle);

            cell = fila.createCell(3);
            cell.setCellValue(item.calcularImporte());
            cell.setCellStyle(tbodyStyle);
        }
        Row filatotal = sheet.createRow(rowNum);
        filatotal.createCell(2).setCellValue("Gran total: ");
        filatotal.createCell(3).setCellValue(factura.getTotal());
    }

}
