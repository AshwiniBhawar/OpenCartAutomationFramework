package com.qa.opencart.utils;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvException;

public class CSVUtil {

	private static final String CSV_PATH = "./src/test/resources/testdata/";
	private static List<String[]> rows;

	public static Object[][] getCSVData(String csvName) {

		String csvFile = CSV_PATH + csvName + ".csv";
		CSVReader reader;
		try {
			reader = new CSVReader(new FileReader(csvFile));
			rows = reader.readAll();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (CsvException e) {
			e.printStackTrace();
		}
		
		Object[][] data= new Object[rows.size()][];
		
//		for(int i=0;i<rows.size();i++) {
//			data[i]=rows.get(i);
//		}
		
		for(int i=0;i<rows.size();i++) {
		String[] row = rows.get(i);

        //Replace null / "null" / "" with empty string
        for (int j = 0; j < row.length; j++) {
            if (row[j] == null || row[j].equalsIgnoreCase("null") || row[j].trim().isEmpty()) {
                row[j] = null; // Replace null with ""
            }
        }
        data[i] = row;
    }
		
		return data;

	}

}
