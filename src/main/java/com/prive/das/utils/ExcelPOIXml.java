package com.prive.das.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelPOIXml {

	private static Log logger = LogFactory.getLog(ExcelPOIXml.class);

	public void setStyleValue2003(XSSFCell celX, XSSFCellStyle cell,
			XSSFFont font) {
//		cell.setBorderLeft((short) 1);
//		cell.setBorderBottom((short) 1);
//		cell.setBorderRight((short) 1);
		cell.setWrapText(true);
		cell.setFont(font);
		cell.setAlignment(XSSFCellStyle.ALIGN_RIGHT);
//		cell.setBorderTop((short) 1);
		celX.setCellStyle(cell);

	}

	public void outExcel(List lines, String path) {
		XSSFWorkbook workbook = new XSSFWorkbook();
		XSSFSheet sheet = workbook.createSheet("Sheet 1");
		XSSFFont font = workbook.createFont();
		XSSFCellStyle cell11 = workbook.createCellStyle();

		font.setFontHeightInPoints((short) 10);
		font.setFontName("Arial");
		font.setBoldweight(XSSFFont.BOLDWEIGHT_NORMAL);

		XSSFRow row;
		XSSFCell cell;
		// sheet.protectSheet("password");
		// CellStyle unlockedCellStyle = workbook.createCellStyle();
		// unlockedCellStyle.setLocked(false); // true or false based on the
		// cell.

		// head
		// row = sheet.createRow(0);
		// body
		try {
			int i = 0;
			for (i = 0; i < lines.size(); i++) {
				if (i % 1000 == 0)
					logger.info(i);
				else if (i % 10 == 0)
					System.out.print(".");

				row = sheet.createRow(i);
				List ay1 = (List) lines.get(i);
				for (int j = 0; j < ay1.size(); j++) {
					Object obj = ay1.get(j);

					String field = "";
					if (obj == null) {
						// logger.info("error: hm.get(" + header
						// + ") is null");
						field = "";
					} else if ("class java.lang.String".equals(obj.getClass()
							.toString()))
						field = (String) obj;
					else if ("class java.lang.Integer".equals(obj.getClass()
							.toString()))
						field = String.valueOf(obj);
					else if ("class java.math.BigDecimal".equals(obj.getClass()
							.toString()))
						field = String.valueOf(obj);
					else if ("class java.lang.Double".equals(obj.getClass()
							.toString()))
						field = String.valueOf(obj);
					else if ("class java.util.ArrayList".equals(obj.getClass()
							.toString())) {
						List ay = (List) obj;
						for (int k = 0; k < ay.size(); k++) {
							field += (String) ay.get(k) + ",";
						}
					} else if ("class java.lang.Boolean".equals(obj.getClass()
							.toString())) {
						field = String.valueOf(obj);
						// } else if
						// ("class java.sql.Timestamp".equals(obj.getClass().toString()))
						// {
						// field = timestamp2string((Timestamp) obj);
					} else {
						logger.info("錯誤!未處理: " + obj.getClass().toString());
					}
					cell = row.createCell(j);
					cell.setCellValue(field);
					// if ("COL003".equals(field))
					// cell.setCellStyle(unlockedCellStyle);
				}
			}
			System.out.print(i);
		} catch (Exception e) {
			e.printStackTrace();
		}

		// adjust width
		for (int i = 0; i <= 20; i++) {
			sheet.autoSizeColumn((short) i);
		}

		// done
		OutputStream outputStream = null;
		try {
			outputStream = new FileOutputStream(new File(path));
			workbook.write(outputStream);
			logger.info("輸出xlsx:" + path + "成功");
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (outputStream != null) {
				try {
					outputStream.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}

	}

	public void outExcel(List headers, List lines, String path) {
		XSSFWorkbook workbook = new XSSFWorkbook();
		XSSFSheet sheet = workbook.createSheet("Sheet 1");
		XSSFFont font = workbook.createFont();
		XSSFCellStyle cell11 = workbook.createCellStyle();

		font.setFontHeightInPoints((short) 10);
		font.setFontName("Arial");
		font.setBoldweight(XSSFFont.BOLDWEIGHT_NORMAL);

		XSSFRow row;
		XSSFCell cell;

		// head
		row = sheet.createRow(0);
		for (int i = 0; i < headers.size(); i++) {
			String header = (String) headers.get(i);
			cell = row.createCell(i);
			cell.setCellValue(header);
			setStyleValue2003(cell, cell11, font);
			sheet.autoSizeColumn(i);
		}
		// body
		try {
			int i = 0;
			for (i = 0; i < lines.size(); i++) {
				if (i % 1000 == 0)
					logger.info(i);
				else if (i % 10 == 0)
					System.out.print(".");

				row = sheet.createRow(i + 1);
				Map hm = (Map) lines.get(i);
				for (int j = 0; j < headers.size(); j++) {
					String header = (String) headers.get(j);

					String field = "";
					Object obj = hm.get(header);
					if (obj == null) {
						// logger.info("error: hm.get(" + header
						// + ") is null");
						field = "null";
					} else if ("class java.lang.String".equals(obj.getClass()
							.toString()))
						field = (String) obj;
					else if ("class java.lang.Integer".equals(obj.getClass()
							.toString()))
						field = String.valueOf(obj);
					else if ("class java.util.ArrayList".equals(obj.getClass()
							.toString())) {
						List ay = (List) obj;
						for (int k = 0; k < ay.size(); k++) {
							field += (String) ay.get(k) + ",";
						}
					} else {
						logger.info(obj.getClass().toString());
					}
					cell = row.createCell(j);
					cell.setCellValue(field);
					// setStyleValue2003(cell, cell11, font);
					// sheet.autoSizeColumn(j);
				}
			}
			System.out.print(i);
		} catch (Exception e) {
			e.printStackTrace();
		}

		// adjust width
		for (int i = 0; i <= 20; i++) {
			sheet.autoSizeColumn((short) i);
		}

		// done
		OutputStream outputStream = null;
		try {
			outputStream = new FileOutputStream(new File(path));
			workbook.write(outputStream);
			logger.info("輸出xls" + path + "成功");
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (outputStream != null) {
				try {
					outputStream.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}

	}

	public void outExcelposition(String message, String sheetName, int line,
			int field, String path) {
		InputStream is = null;
		XSSFWorkbook workbook = null;
		OutputStream outputStream = null;
		try {
			is = new FileInputStream(new File(path));
			workbook = new XSSFWorkbook(is);
			is.close();

			XSSFSheet sheet = workbook.getSheet(sheetName);
			if (sheet == null)
				sheet = workbook.createSheet(sheetName);
			
			XSSFFont font = workbook.createFont();
			XSSFCellStyle cell11 = workbook.createCellStyle();

			font.setFontHeightInPoints((short) 10);
			font.setFontName("Arial");
//			font.setBoldweight(XSSFFont.BOLDWEIGHT_NORMAL);

			XSSFRow row;
			XSSFCell cell;

			// head
			row = sheet.getRow(line);
			if (row == null)
				row = sheet.createRow(line);
			cell = row.getCell(field);
			if (cell == null)
				cell = row.createCell(field);
			cell.setCellValue(message);
			setStyleValue2003(cell, cell11, font);
			sheet.autoSizeColumn(field);

			outputStream = new FileOutputStream(new File(path));
			workbook.write(outputStream);

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (outputStream != null) {
				try {
					outputStream.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	public List readExcel(String path) {
		InputStream is = null;
		XSSFWorkbook workbook = null;
		List ay = null;
		try {
			is = new FileInputStream(new File(path));
			workbook = new XSSFWorkbook(is);
			is.close();

			XSSFSheet sheet = workbook.getSheetAt(0);

			XSSFRow row;
			XSSFCell cell;
			ay = new ArrayList();
			// head
			List ayHeader = new ArrayList();
			row = sheet.getRow(0);
			for (int i = 0; i < row.getLastCellNum(); i++) {
				cell = row.getCell(i);
				String header = (String) cell.getStringCellValue();
				ayHeader.add(header);
			}
			ay.add(ayHeader);

			for (int j = 1; j <= sheet.getLastRowNum(); j++) {
				row = sheet.getRow(j);
				List ayValue = new ArrayList();
				int lenSum = 0;
				for (int i = 0; i < ayHeader.size(); i++) {
					cell = row.getCell(i);
					String value = readCell(cell);
					ayValue.add(value);
					lenSum = lenSum + value.length();
					// logger.info("value=" + value.length() + ";");
				}
				// logger.info("ayValue=" + ayValue.size() + "/" + lenSum +
				// ";");
				if (lenSum > 0) {
					ay.add(ayValue);
				}
			}
			// head
			// row = sheet.createRow(line);
			// cell = row.createCell(field);
			// cell.setCellValue(message);
			// setStyleValue2003(cell, cell11, font);
			// sheet.autoSizeColumn(field);

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			return ay;
		}
	}

	public XSSFWorkbook loadExcel(String path) {
		InputStream is = null;
		XSSFWorkbook workbook = null;
		try {
			logger.warn("讀取:" + path);
			is = new FileInputStream(new File(path));
			workbook = new XSSFWorkbook(is);
			is.close();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			return workbook;
		}
	}

	public List readExcel(XSSFWorkbook workbook, String sheetName) {
		List ay = null;
		try {
			XSSFSheet sheet = workbook.getSheet(sheetName);
			XSSFRow row;
			XSSFCell cell;
			ay = new ArrayList();
			// head
			List ayHeader = new ArrayList();
			row = sheet.getRow(0);
			for (int i = 1; i < row.getLastCellNum(); i++) {
				cell = row.getCell(i);
				if (cell == null)
					continue;
				String header = readCell(cell);
				// String header = (String) cell.getStringCellValue();
				ayHeader.add(header);
			}
			ay.add(ayHeader);

			for (int j = 1; j <= sheet.getLastRowNum(); j++) {
				row = sheet.getRow(j);
				List ayValue = new ArrayList();
				for (int i = 1; i < ayHeader.size() + 1; i++) {
					cell = row.getCell(i);
					if (cell == null)
						continue;
					String value = readCell(cell);
					if (i == 12)
						logger.info(i);

					// if (cell == null) {
					// ayValue.add("");value
					// } else {
					// String value = readCell(cell);
					ayValue.add(value);
					// }
				}
				ay.add(ayValue);
			}
			// head
			// row = sheet.createRow(line);
			// cell = row.createCell(field);
			// cell.setCellValue(message);
			// setStyleValue2003(cell, cell11, font);
			// sheet.autoSizeColumn(field);

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			return ay;
		}
	}

	public List readExcel(XSSFWorkbook workbook, String sheetName, int headRow,
			int dataRow, int startCell) {
		List ay = null;
		int j = 0;
		int i = 0;
		try {
			XSSFSheet sheet = workbook.getSheet(sheetName);
			XSSFRow row;
			XSSFCell cell;
			ay = new ArrayList();
			// head
			List ayHeader = new ArrayList();
			row = sheet.getRow(headRow);
			logger.warn("解析xls:" + sheetName);
			for (i = startCell; i < row.getLastCellNum(); i++) {
				cell = row.getCell(i);
				if (cell == null) {
					ayHeader.add("");
				} else {
					String header = readCell(cell);
					ayHeader.add(header);
				}
			}
			ay.add(ayHeader);
			logger.debug("標頭行內定為" + headRow + ayHeader);
			logger.debug("資料行內定為" + dataRow);

			int slrn = sheet.getLastRowNum();
			if (slrn == 760)
				logger.debug("debug4385");

			if ("1.9_Service".equals(sheetName))
				logger.debug("debug4885");

			for (j = dataRow; j <= slrn; j++) {
				row = sheet.getRow(j);
				if (row == null)
					continue;

				List ayValue = new ArrayList();
				for (i = startCell; i < row.getLastCellNum(); i++) {
					if (i == 7 && j == 557)
						logger.info("debug!" + i);
					cell = row.getCell(i);
					if (cell == null) {
						ayValue.add("");
					} else {
						String value = readCell(cell);
						// if ("79.400000000000006".equals(value)) {
						// System.out.println("debug17");
						// }
						ayValue.add(value);
					}
				}
				if (j == dataRow)
					logger.debug("資料開始" + ayValue);
				else if (j == sheet.getLastRowNum())
					logger.debug("資料結束" + ayValue);

				ay.add(ayValue);
			}

			// head
			// row = sheet.createRow(line);
			// cell = row.createCell(field);
			// cell.setCellValue(message);
			// setStyleValue2003(cell, cell11, font);
			// sheet.autoSizeColumn(field);

		} catch (Exception e) {
			System.out.println("j:" + j + " i:" + i);
			e.printStackTrace();
		} finally {
			return ay;
		}
	}

	public List readExcelx(XSSFWorkbook workbook, String sheetName,
			int headRow, int dataRow, int startCell) {
		List ay = null;
		int j = 0;
		int i = 0;
		try {
			XSSFSheet sheet = workbook.getSheet(sheetName);
			XSSFRow row;
			XSSFCell cell;
			ay = new ArrayList();
			// head
			List ayHeader = new ArrayList();
			row = sheet.getRow(headRow);
			logger.warn("解析xls:" + sheetName);
			for (i = startCell; i < row.getLastCellNum(); i++) {
				cell = row.getCell(i);
				if (cell == null) {
					ayHeader.add("");
				} else {
					String header = readCell(cell);
					ayHeader.add(header);
				}
			}
			ay.add(ayHeader);
			logger.debug("標頭行內定為" + headRow + ayHeader);
			logger.debug("資料行內定為" + dataRow);
			for (j = dataRow; j <= sheet.getLastRowNum(); j++) {
				row = sheet.getRow(j);
				if (row == null)
					break;

				List ayValue = new ArrayList();
				for (i = startCell; i < row.getLastCellNum(); i++) {
					// if (i == 12)
					// logger.info("debug!" + i);
					cell = row.getCell(i);
					if (cell == null) {
						ayValue.add("");
					} else {
						String value = readCellx(cell);
						ayValue.add(value);
					}
				}
				if (j == dataRow)
					logger.debug("資料開始" + ayValue);
				else if (j == sheet.getLastRowNum())
					logger.debug("資料結束" + ayValue);

				ay.add(ayValue);
			}

			// head
			// row = sheet.createRow(line);
			// cell = row.createCell(field);
			// cell.setCellValue(message);
			// setStyleValue2003(cell, cell11, font);
			// sheet.autoSizeColumn(field);

		} catch (Exception e) {
			System.out.println("j:" + j + " i:" + i);
			e.printStackTrace();
		} finally {
			return ay;
		}
	}

	public List readExcel(XSSFWorkbook workbook, String sheetName, int dataRow,
			int targetCell) {
		List ayValue = null;
		try {
			XSSFSheet sheet = workbook.getSheet(sheetName);
			XSSFRow row;
			XSSFCell cell;
			ayValue = new ArrayList();
			// head
			logger.warn("解析xlsx:" + sheetName);

			logger.debug("資料行內定為" + dataRow);
			for (int j = dataRow; j <= sheet.getLastRowNum(); j++) {
				row = sheet.getRow(j);
				String value = "";
				cell = row.getCell(targetCell);
				if (cell == null) {
				} else {
					value = readCell(cell);
				}
				ayValue.add(value);

				if (j == dataRow)
					logger.debug("資料開始" + value);
				else if (j == sheet.getLastRowNum())
					logger.debug("資料結束" + value);
			}

		} catch (Exception e) {
			logger.error("錯誤!sheet:" + sheetName + " 不存在");
			e.printStackTrace();
		} finally {
			return ayValue;
		}
	}

	private String Array2String(String[] s) {
		String ss = "";
		for (int i = 0; i < s.length; i++) {
			if ("projectAttachedAttribute".equals(s[0]))
				System.out.println("debug473");
			ss += s[i] + "#";
		}
		return ss.substring(0, ss.length() - 1);
	}

	private String readCell(XSSFCell cell) {
		String value = "";
		try {
			switch (cell.getCellType()) {
			case XSSFCell.CELL_TYPE_NUMERIC: // 数字
				logger.info("XSSFCell=" + XSSFCell.CELL_TYPE_NUMERIC + ";");

				if (HSSFDateUtil.isCellDateFormatted(cell)) {
					Date date = cell.getDateCellValue();
					// value = date2string(date, "yyyy-MM-dd'T'HH:mm:ss");
					DateFormat writeFormat = new SimpleDateFormat("yyyyMMdd");
					value = writeFormat.format(date);
					logger.warn("讀取excel數字RAW異常D:" + date + " -->" + value
							+ ";");
				} else {
					value = cell.getRawValue();
					// if (value.length() > 7) {
					if (StringUtils.indexOf(value, ".") >= 0) { // 小數點
						Double d = cell.getNumericCellValue();
						String value2 = String.valueOf(d);
						logger.warn("讀取excel數字RAW異常_1:" + value + " --> "
								+ value2);

						if (StringUtils.indexOf(value2, "E") >= 0) { // 科學符號(數字大於八位數=8.765432111E7)
							d = cell.getNumericCellValue();

							BigDecimal b = new BigDecimal(d);
							b = b.setScale(6, RoundingMode.HALF_UP);
							value2 = b.toPlainString();

							logger.warn("讀取excel數字RAW異常_1.2(科學符號):" + value
									+ " --> " + value2.trim());
							value = value2;
						}
						value = value2;
					} else if (StringUtils.indexOf(value, "E") >= 0) { // 科學符號(小數點後數字過多)
						Double d = cell.getNumericCellValue();

						BigDecimal b = new BigDecimal(d);
						b = b.setScale(6, RoundingMode.HALF_UP);
						String value2 = b.toPlainString();

						logger.warn("讀取excel數字RAW異常_2(科學符號):" + value + " --> "
								+ value2);
						value = value2;
					} else {
						// logger.warn("讀取excel數字RAW異常3:" + value + " -->");
					}
					// }
				}
				try {
					boolean b1 = cell.getCellStyle().getWrapText();// 有斷行
					boolean b2 = cell.getCellStyle().getFont().getStrikeout();// 有廢棄線
					if (b1) {
						String[] s = StringUtils.split(value, "\n");
						value = s[s.length - 1];
					} else if (b2) {
						value = "";
					} else {

					}
				} catch (Exception e) {
					e.printStackTrace();
				}
				break;
			case XSSFCell.CELL_TYPE_STRING: // 字符串
				// logger.info("XSSFCell=" + XSSFCell.CELL_TYPE_STRING + ";");

				value = cell.getStringCellValue();
				if ("vpneid".equals(value))
					logger.debug("debug11117");
				boolean b1 = cell.getCellStyle().getWrapText();// 有斷行
				boolean b2 = cell.getCellStyle().getFont().getStrikeout();// 有廢棄線
				if (b1) {
					if (b2) {
						String[] s = StringUtils.split(value, "\n");
						if (s.length == 0) {
							logger.error("錯誤,不可能空值有廢棄線!");
						} else if (s.length == 1) {
							value = "";
						} else {
							value = s[s.length - 1];
						}
					} else {
						String[] s = StringUtils.split(value, "\n");
						String value2 = Array2String(s);
						if ("mvpnCFS，inmvpnCFS".equals(value2))
							logger.debug("debug3437");
						value = value2;
					}
				} else {
					if (b2) {
						value = "";
					} else {

					}
				}
				break;
			case XSSFCell.CELL_TYPE_BOOLEAN: // Boolean
				// logger.info("XSSFCell=" + XSSFCell.CELL_TYPE_BOOLEAN + ";");

				value = cell.getBooleanCellValue() + "";
				break;
			case XSSFCell.CELL_TYPE_FORMULA: // 公式
				// logger.info("XSSFCell=" + XSSFCell.CELL_TYPE_FORMULA + ";");

				try {
					value = cell.getRawValue();
					if ("baseFMDS11022303".equals(value)) {
						System.out.println("debug17");
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
				// value = cell.getCellFormula() + "";
				break;
			case XSSFCell.CELL_TYPE_BLANK: // 空值
				// logger.info("XSSFCell=" + XSSFCell.CELL_TYPE_BLANK + ";");

				value = "";
				break;
			case XSSFCell.CELL_TYPE_ERROR: // 故障
				// logger.info("XSSFCell=" + XSSFCell.CELL_TYPE_ERROR + ";");

				value = "非法字符";
				break;
			default:
				// logger.info("XSSFCell=未知类型;");

				value = "未知类型";
				break;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		// try {
		// String str = (String) cell.getStringCellValue();
		// return str;
		// } catch (Exception e) {
		//
		// }
		// try {
		// boolean bs = cell.getBooleanCellValue();
		// return String.valueOf(bs);
		// } catch (Exception e) {
		//
		// }
		// try {
		// String str = cell.getRawValue();
		// return String.valueOf(str);
		// } catch (Exception e) {
		//
		// }
		// try {
		// int is = (int) cell.getNumericCellValue();
		// return String.valueOf(is);
		// } catch (Exception e) {
		//
		// }
		return value;
	}

	private String readCellx(XSSFCell cell) {
		String value = "";
		try {
			switch (cell.getCellType()) {
			case XSSFCell.CELL_TYPE_NUMERIC: // 数字
				try {
					value = cell.getRawValue();
					// boolean b1 = cell.getCellStyle().getWrapText();// 有斷行
					// boolean b2 =
					// cell.getCellStyle().getFont().getStrikeout();// 有廢棄線
					// if (b1) {
					// String[] s = StringUtils.split(value, "\n");
					// value = s[s.length - 1];
					// } else if (b2) {
					// value = "";
					// }
				} catch (Exception e) {
					e.printStackTrace();
				}
				break;
			case XSSFCell.CELL_TYPE_STRING: // 字符串
				value = cell.getStringCellValue();
				boolean b1 = cell.getCellStyle().getWrapText();// 有斷行
				boolean b2 = cell.getCellStyle().getFont().getStrikeout();// 有廢棄線
				if (b1) {
					if (b2) {
						value = "";
					} else {
					}
				} else if (b2) {
					value = "";
				}
				break;
			case XSSFCell.CELL_TYPE_BOOLEAN: // Boolean
				value = cell.getBooleanCellValue() + "";
				break;
			case XSSFCell.CELL_TYPE_FORMULA: // 公式
				try {
					value = cell.getRawValue();
				} catch (Exception e) {
					e.printStackTrace();
				}
				// value = cell.getCellFormula() + "";
				break;
			case XSSFCell.CELL_TYPE_BLANK: // 空值
				value = "";
				break;
			case XSSFCell.CELL_TYPE_ERROR: // 故障
				value = "非法字符";
				break;
			default:
				value = "未知类型";
				break;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		// try {
		// String str = (String) cell.getStringCellValue();
		// return str;
		// } catch (Exception e) {
		//
		// }
		// try {
		// boolean bs = cell.getBooleanCellValue();
		// return String.valueOf(bs);
		// } catch (Exception e) {
		//
		// }
		// try {
		// String str = cell.getRawValue();
		// return String.valueOf(str);
		// } catch (Exception e) {
		//
		// }
		// try {
		// int is = (int) cell.getNumericCellValue();
		// return String.valueOf(is);
		// } catch (Exception e) {
		//
		// }
		return value;
	}

	public List readSExcel(String path, String sheetName) {
		InputStream is = null;
		Workbook workbook = null;
		List ay = null;
		try {
			File newFile = new File(path);
			if (newFile.exists()) {
				// Load existing
				workbook = WorkbookFactory.create(newFile);
			} else {
				// What kind of file are they trying to ask for?
				// Add additional supported types here
				if (newFile.getName().endsWith(".xls")) {
					workbook = new HSSFWorkbook();
				} else if (newFile.getName().endsWith(".xlsx")) {
					workbook = new XSSFWorkbook();
				} else {
					throw new IllegalArgumentException(
							"I don't know how to create that kind of new file");
				}
			}

			Sheet sheet = workbook.getSheet(sheetName);

			Row row;
			Cell cell;
			ay = new ArrayList();
			// head
			// List ayHeader=new ArrayList();

			int k = 0;
			List ay1 = new ArrayList();
			for (int j = 0; j <= sheet.getLastRowNum(); j++) {
				row = sheet.getRow(j);
				cell = row.getCell(0);
				if (cell != null) {
					String header = (String) cell.getStringCellValue();
					if (header.getBytes().length != header.length())// 有中文
					{
						k++;
						logger.info(header);
						if (ay1 != null)
							ay.add(ay1);
						if (k >= 3) {
							ay1 = new ArrayList();
							ay1.add(header);
						}
					} else {
						ay1.add(header);
					}
				}
			}
			if (ay1 != null)
				ay.add(ay1);

			// ay1 = null;
			// k = 0;
			// for (int j = 0; j < sheet.getLastRowNum(); j++) {
			// row = sheet.getRow(j);
			// cell = row.getCell(4);
			// if (cell == null)
			// break;
			// String header = (String) cell.getStringCellValue();
			// if (header.getBytes().length != header.length())// 有中文
			// {
			// k++;
			// logger.info(header);
			// if (k == 1) {
			// ay1 = new ArrayList();
			// ay1.add(header);
			// }
			// } else {
			// ay1.add(header);
			// }
			// }
			// if (ay1 != null)
			// ay.add(ay1);

			// ay1 = null;
			// k = 0;
			// for (int j = 0; j < sheet.getLastRowNum(); j++) {
			// row = sheet.getRow(j);
			// cell = row.getCell(8);
			// if (cell == null)
			// break;
			// String header = (String) cell.getStringCellValue();
			// if (header.getBytes().length != header.length())// 有中文
			// {
			// k++;
			// logger.info(header);
			// if (k == 1) {
			// ay1 = new ArrayList();
			// ay1.add(header);
			// }
			// } else {
			// ay1.add(header);
			// }
			// }
			// if (ay1 != null)
			// ay.add(ay1);

			// head
			// row = sheet.createRow(line);
			// cell = row.createCell(field);
			// cell.setCellValue(message);
			// setStyleValue2003(cell, cell11, font);
			// sheet.autoSizeColumn(field);
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			return ay;
		}
	}

	// public static void main(String[] args) {
	// ExcelPOIXml dd = new ExcelPOIXml();
	// List list = dd.readExcel("C:\\temp\\template.xlsx");
	//
	// dd.outExcel(list, "C:\\temp\\template2.xlsx");
	// System.out.println(list);
	// }

}
