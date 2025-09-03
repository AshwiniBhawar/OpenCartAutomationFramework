package com.qa.opencart.utils;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;


public class ExcelUtil {
	public static final String TEST_DATA_SHEET_PATH= "./src/test/resources/testdata/"; 
	public static Workbook workbook;
	public static Sheet sheet;
	
	public static Object[][] getTestData(String fileName,String sheetName) {
		String filePath = TEST_DATA_SHEET_PATH + fileName + ".xlsx";
		Object[][] data=null;
		try {
			FileInputStream ip = new FileInputStream(filePath);
			workbook=WorkbookFactory.create(ip);
			sheet=workbook.getSheet(sheetName);
			data= new Object[sheet.getLastRowNum()][sheet.getRow(0).getLastCellNum()];
			
//			for(int row=0; row<sheet.getLastRowNum();row++) {
//				for(int col=0;col<sheet.getRow(0).getLastCellNum();col++) {
//					data[row][col]=sheet.getRow(row+1).getCell(col).toString();
//				}
//			}
			
			for(int row=0; row<sheet.getLastRowNum();row++) {
				for(int col=0;col<sheet.getRow(0).getLastCellNum();col++) {
					String cellValue=sheet.getRow(row+1).getCell(col).toString();
					if(cellValue.equals("null")) {
						data[row][col]=null;
					}
					else {
					data[row][col]=cellValue.toString();
					}
				}
			}
			
		} catch (InvalidFormatException e) {
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} 
		
		return data;
	}

}
