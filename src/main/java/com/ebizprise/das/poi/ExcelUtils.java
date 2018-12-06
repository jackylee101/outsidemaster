package com.ebizprise.das.poi;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.springframework.stereotype.Component;
@Component
public class ExcelUtils {
	
	
	public List<Map<String, String>> getExcelTypeExlInfo(String pathName, String fileName)
			throws FileNotFoundException, IOException {
		FileInputStream file = new FileInputStream(new File(pathName + fileName));
		HSSFWorkbook workbook = new HSSFWorkbook(file);

		// Get first sheet from the workbook
		HSSFSheet sheet = workbook.getSheetAt(0);

		// Iterate through each rows from first sheet
		Iterator<Row> rowIterator = sheet.iterator();
		int rowCount = 0;
		List<Map<String, String>> list = new ArrayList<Map<String, String>>();
		Map<String, String> head = null;
		while (rowIterator.hasNext()) {
			Map<String, String> content = null;
			Row row = rowIterator.next();
			// For each row, iterate through each columns
			Iterator<Cell> cellIterator = row.cellIterator();
			if (rowCount == 0) {
				head = this.getHeaderRow(cellIterator);				
			} else {
				content =this.getContentRow(cellIterator, head);
				list.add(content);				
			}
			rowCount++;
		}

		file.close();
		return list;
	}

	private Map<String, String> getHeaderRow(Iterator<Cell> cellIterator) {

		Map<String, String> map = new HashMap<String, String>();
		int colCount = 0;
		while (cellIterator.hasNext()) {
			colCount++;
			Cell cell = cellIterator.next();
			switch (cell.getCellType()) {
			case Cell.CELL_TYPE_BOOLEAN:
				map.put(String.valueOf(colCount), String.valueOf(cell.getBooleanCellValue()));
				break;
			case Cell.CELL_TYPE_NUMERIC:

				map.put(String.valueOf(colCount), String.valueOf(cell.getNumericCellValue()));
				break;
			case Cell.CELL_TYPE_STRING:

				map.put(String.valueOf(colCount), String.valueOf(cell.getStringCellValue()));
				break;
			}

		}
		return map;
	}

	private Map<String, String> getContentRow(Iterator<Cell> cellIterator, Map<String, String> head) {

		Map<String, String> map = new HashMap<String, String>();
		int colCount = 0;
		while (cellIterator.hasNext()) {
			colCount++;

			String key = this.findFirstKeyByValue(head, String.valueOf(colCount));
			Cell cell = cellIterator.next();
			switch (cell.getCellType()) {
			case Cell.CELL_TYPE_BOOLEAN:
				map.put(key, String.valueOf(cell.getBooleanCellValue()));
				break;
			case Cell.CELL_TYPE_NUMERIC:

				map.put(key, String.valueOf(cell.getNumericCellValue()));
				break;
			case Cell.CELL_TYPE_STRING:
				map.put(key, String.valueOf(cell.getStringCellValue()));
				break;
			}

		}
		return map;
	}

	private String findFirstKeyByValue(Map<String, String> map, String value) {
		for (Entry<String, String> e : map.entrySet())
			if (e.getKey().equals(value))
				return e.getValue();
		return null;
	}

}
