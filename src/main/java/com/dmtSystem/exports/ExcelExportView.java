package com.dmtSystem.exports;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.dmtSystem.models.Product;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.web.servlet.view.document.AbstractXlsView;

import com.dmtSystem.models.OrderFlow;

public class ExcelExportView extends AbstractXlsView {

	@Override
	protected void buildExcelDocument(Map<String, Object> model, Workbook workbook, HttpServletRequest request,
			HttpServletResponse response)  {

		response.setHeader("Content-Disposition", "attachment;filename=\"registoAtividade"
				+ LocalDate.now().format(DateTimeFormatter.ofPattern("dd-MM-yyy")) + ".xls\"");
		List<OrderFlow> orders = (List<OrderFlow>) model.get("encomendas");
		Sheet sheet = workbook.createSheet("Registo Atividade");
		Row header = sheet.createRow(0);
		header.createCell(0).setCellValue("Cotação");
		header.createCell(1).setCellValue("Posto");
		header.createCell(2).setCellValue("Arma");
		header.createCell(3).setCellValue("NIM");
		header.createCell(4).setCellValue("Local");
		header.createCell(5).setCellValue("Data de Registo");
		header.createCell(6).setCellValue("Data Limite");
		header.createCell(7).setCellValue("Estado");

		CellStyle styleBold = workbook.createCellStyle();// Create style
		Font font = workbook.createFont();// Create font
		font.setBold(true);// Make font bold
		styleBold.setFont(font);// set it to bold
		styleBold.setAlignment(HorizontalAlignment.CENTER);
		
		CellStyle styleBoldRight = workbook.createCellStyle();
		styleBoldRight.setFont(font);// set it to bold
		styleBoldRight.setAlignment(HorizontalAlignment.CENTER);
		styleBoldRight.setBorderRight(BorderStyle.THICK);
		
		
		for (int i = 0; i < header.getLastCellNum()-1; i++) {// For each cell in the row
			header.getCell(i).setCellStyle(styleBold);// Set the style
		}
		header.getCell(7).setCellStyle(styleBoldRight);

		CellStyle style = workbook.createCellStyle();
		// Setting Background color
		style.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
		style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
		style.setAlignment(HorizontalAlignment.CENTER);
		
		CellStyle styleCenter = workbook.createCellStyle();
		CellStyle styleCenterRight = workbook.createCellStyle();
		
		styleCenter.setAlignment(HorizontalAlignment.CENTER);
		styleCenter.setBorderBottom(BorderStyle.THICK);
		styleCenter.setBorderTop(BorderStyle.THICK);
		
		styleCenterRight.setAlignment(HorizontalAlignment.CENTER);
		styleCenterRight.setBorderBottom(BorderStyle.THICK);
		styleCenterRight.setBorderTop(BorderStyle.THICK);
		styleCenterRight.setBorderRight(BorderStyle.THICK);
		
		int rowNum = 1;
		for (OrderFlow order : orders) {
			Row row = sheet.createRow(rowNum++);
			row.createCell(0).setCellValue(Long.parseLong(order.getEncCode()));
			row.createCell(1).setCellValue(order.getClient().getRanked());
			row.createCell(2).setCellValue(order.getClient().getWeapon());
			row.createCell(3).setCellValue(Long.parseLong(order.getClient().getNim()));
			row.createCell(4).setCellValue(order.getDescription());
			row.createCell(5).setCellValue(order.getDateStart().format(DateTimeFormatter.ofPattern("dd-MM-yyy")));
			row.createCell(6).setCellValue(order.getDateLimite().format(DateTimeFormatter.ofPattern("dd-MM-yyy")));
			row.createCell(7).setCellValue(order.getState());
			
			for (int i = 0; i < row.getLastCellNum()-1; i++) {// For each cell in the row
				row.getCell(i).setCellStyle(styleCenter);// Set the style
			}
			
			row.getCell(7).setCellStyle(styleCenterRight);

			if (order.getProd().size() > 0) {

				for (Product product : order.getProd()) {
					Row row1 = sheet.createRow(rowNum++);

					Cell cell = row1.createCell(0);
					cell.setCellValue(product.getNna());
					cell.setCellStyle(style);
					Cell cell1 = row1.createCell(1);
					cell1.setCellValue(product.getDescription());
					cell1.setCellStyle(style);
					
					

				}
			}
		}

		for (int j = 0; j < 8; j++)
			sheet.autoSizeColumn(j);
	}
}