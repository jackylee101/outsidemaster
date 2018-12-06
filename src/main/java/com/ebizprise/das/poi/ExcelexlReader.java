/**
 * The MIT License
 *
 * Copyright (c) 2012 www.myjeeva.com
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and
 * associated documentation files (the "Software"), to deal in the Software without restriction,
 * including without limitation the rights to use, copy, modify, merge, publish, distribute,
 * sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or
 * substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT
 * NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND
 * NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM,
 * DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 * 
 */
package com.ebizprise.das.poi;

import java.io.BufferedInputStream;
/**
 * Generic Excel File(XLSX) Reading using Apache POI
 * 
 * @author <a href="mailto:jeeva@myjeeva.com">Jeevanandam M.</a>
 * 
 * @since v1.0
 */
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParserFactory;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.openxml4j.exceptions.OpenXML4JException;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.util.IOUtils;
import org.apache.poi.xssf.eventusermodel.ReadOnlySharedStringsTable;
import org.apache.poi.xssf.eventusermodel.XSSFReader;
import org.apache.poi.xssf.eventusermodel.XSSFSheetXMLHandler;
import org.apache.poi.xssf.eventusermodel.XSSFSheetXMLHandler.SheetContentsHandler;
import org.apache.poi.xssf.model.StylesTable;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.xml.sax.ContentHandler;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;

public class ExcelexlReader {

	private static final Log LOG = LogFactory.getLog(ExcelReader.class);

	private static final int READ_ALL = -1;

	private BufferedInputStream xlsxPackage;
	private ExcelSheetCallback sheetCallback;

	/**
	 * Constructor: Microsoft Excel File (XSLX) Reader
	 * 
	 * @param pkg
	 *            a {@link OPCPackage} object - The package to process XLSX
	 * @param sheetContentsHandler
	 *            a {@link SheetContentsHandler} object - WorkSheet contents
	 *            handler
	 * @param sheetCallback
	 *            a {@link ExcelSheetCallback} object - WorkSheet callback for
	 *            sheet processing begin and end (can be null)
	 */
	public ExcelexlReader(BufferedInputStream pkg, SheetContentsHandler sheetContentsHandler,
			ExcelSheetCallback sheetCallback) {
		this.xlsxPackage = pkg;
		this.sheetCallback = sheetCallback;
	}

	/**
	 * Constructor: Microsoft Excel File (XSLX) Reader
	 * 
	 * @param filePath
	 *            a {@link String} object - The path of XLSX file
	 * @param sheetContentsHandler
	 *            a {@link SheetContentsHandler} object - WorkSheet contents
	 *            handler
	 * @param sheetCallback
	 *            a {@link ExcelSheetCallback} object - WorkSheet callback for
	 *            sheet processing begin and end (can be null)
	 */
	public ExcelexlReader(String filePath, SheetContentsHandler sheetContentsHandler, ExcelSheetCallback sheetCallback)
			throws Exception {
		this(getOPCPackage(getFile(filePath)), sheetContentsHandler, sheetCallback);
	}

	/**
	 * Constructor: Microsoft Excel File (XSLX) Reader
	 * 
	 * @param file
	 *            a {@link File} object - The File object of XLSX file
	 * @param sheetContentsHandler
	 *            a {@link SheetContentsHandler} object - WorkSheet contents
	 *            handler
	 * @param sheetCallback
	 *            a {@link ExcelSheetCallback} object - WorkSheet callback for
	 *            sheet processing begin and end (can be null)
	 */
	public ExcelexlReader(File file, SheetContentsHandler sheetContentsHandler, ExcelSheetCallback sheetCallback)
			throws Exception {

		this(getOPCPackage(file), sheetContentsHandler, sheetCallback);
	}

	/**
	 * Processing all the WorkSheet from XLSX Workbook.
	 * 
	 * <br>
	 * <br>
	 * <strong>Example 1:</strong><br>
	 * <code>ExcelReader excelReader = new ExcelReader("src/main/resources/Sample-Person-Data.xlsx", workSheetHandler, sheetCallback);
	 * <br>excelReader.process();</code> <br>
	 * <br>
	 * <strong>Example 2:</strong><br>
	 * <code>ExcelReader excelReader = new ExcelReader(file, workSheetHandler, sheetCallback);
	 * <br>excelReader.process();</code> <br>
	 * <br>
	 * <strong>Example 3:</strong><br>
	 * <code>ExcelReader excelReader = new ExcelReader(pkg, workSheetHandler, sheetCallback);
	 * <br>excelReader.process();</code>
	 * 
	 * @throws Exception
	 */
	public void process() throws Exception {
		read(READ_ALL);
	}

	/**
	 * Processing of particular WorkSheet (zero based) from XLSX Workbook.
	 * 
	 * <br>
	 * <br>
	 * <strong>Example 1:</strong><br>
	 * <code>ExcelReader excelReader = new ExcelReader("src/main/resources/Sample-Person-Data.xlsx", workSheetHandler, sheetCallback);
	 * <br>excelReader.process(2);</code> <br>
	 * <br>
	 * <strong>Example 2:</strong><br>
	 * <code>ExcelReader excelReader = new ExcelReader(file, workSheetHandler, sheetCallback);
	 * <br>excelReader.process(2);</code> <br>
	 * <br>
	 * <strong>Example 3:</strong><br>
	 * <code>ExcelReader excelReader = new ExcelReader(pkg, workSheetHandler, sheetCallback);
	 * <br>excelReader.process(2);</code>
	 * 
	 * @param sheetNumber
	 *            a int object
	 * @throws Exception
	 */
	public void process(int sheetNumber) throws Exception {
		read(sheetNumber);
	}

	private void read(int sheetNumber)   {	 

		Workbook workbook;
		try {
			workbook = new HSSFWorkbook(this.xlsxPackage);
			 
			 System.out.println("no="+workbook.getNumberOfSheets());
			for (int sheetIndex = 0; sheetIndex < workbook.getNumberOfSheets(); ++sheetIndex) {
				Sheet worksheets = workbook.getSheetAt(sheetIndex);
				if (null != sheetCallback)
					this.sheetCallback.startSheet(sheetIndex, worksheets.getSheetName());
				if ((READ_ALL == sheetNumber) || (sheetIndex == sheetNumber)) {
					workbook.getSheetAt(sheetIndex);
					 System.out.println("workbook="+workbook.getNumberOfSheets()+",sheetIndex="+sheetIndex);
				}

				if (null != sheetCallback)
					this.sheetCallback.endSheet();
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	private static File getFile(String filePath) throws Exception {
		if (null == filePath || filePath.isEmpty()) {
			throw new Exception("File path cannot be null");
		}

		return new File(filePath);
	}

	private static BufferedInputStream getOPCPackage(File file) throws Exception {
		if (null == file || !file.canRead()) {
			throw new Exception("File object is null or cannot have read permission");
		}
		
	 
		return new BufferedInputStream(new FileInputStream(file));
	}
}
