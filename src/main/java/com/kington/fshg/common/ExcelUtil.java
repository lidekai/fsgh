package com.kington.fshg.common;

import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.hssf.util.Region;
import org.apache.poi.ss.usermodel.CellStyle;

public class ExcelUtil {

	/**
	 * 导出excel
	 * 
	 * @param response
	 * @param list
	 * @param filename
	 * @param title
	 * @param heads
	 */
	public static void export(HttpServletResponse response,
			List<Map<String, String>> list, String filename, String title,
			String[] heads, Integer[] columnSize) {
		java.io.OutputStream os = null;
		try {
			String filename2 = new String(filename.getBytes("GBK"), "ISO8859-1");
			response.addHeader("Content-Disposition", "attachment;filename="
					+ filename2);
			response.setContentType("application/ms-excel;charset=GBK");
			os = response.getOutputStream();
			toExcel(list, title, heads, columnSize, os);
			list.clear();
			list = null;
		} catch (Exception ex) {
			ex.printStackTrace(System.out);
		} finally {
			if (os != null) {
				try {
					os.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}

	public static void toExcel(List<Map<String, String>> list, String title,
			String[] headers, Integer[] columnSize, OutputStream out) {
		// 默认加序号列
		List<String> headArr = new ArrayList<String>();
		headArr.add("序号");
		for (String header : headers) {
			headArr.add(header);
		}

		// 声明一个工作薄
		HSSFWorkbook workbook = new HSSFWorkbook();
		// 生成一个表格
		HSSFSheet sheet = workbook.createSheet(title);
		// 设置表格默认列宽度为15个字节
		sheet.setDefaultColumnWidth((short) 15);

		HSSFFont fontStyle = workbook.createFont();
		fontStyle.setFontName("宋体");
		fontStyle.setFontHeightInPoints((short) 14);

		// 生成一个样式
		HSSFCellStyle style = workbook.createCellStyle();
		// 标题行样式
		style.setFillForegroundColor(HSSFColor.SKY_BLUE.index);
		style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
		style.setBorderBottom(HSSFCellStyle.BORDER_THIN);
		style.setBorderLeft(HSSFCellStyle.BORDER_THIN);
		style.setBorderRight(HSSFCellStyle.BORDER_THIN);
		style.setBorderTop(HSSFCellStyle.BORDER_THIN);
		style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		style.setFont(fontStyle);

		// 表格内容样式
		HSSFFont fontStyle2 = workbook.createFont();
		fontStyle2.setFontName("宋体");
		fontStyle2.setFontHeightInPoints((short) 12);

		HSSFCellStyle style2 = workbook.createCellStyle();
		style2.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		style2.setFont(fontStyle2);

		// 产生表格标题行
		HSSFRow hrow = sheet.createRow(0);
		HSSFCell cell = null;
		for (short i = 0; i < headArr.size(); i++) {
			cell = hrow.createCell(i);
			cell.setCellStyle(style);
			// HSSFRichTextString text = new HSSFRichTextString(headers[i]);
			cell.setCellValue(headArr.get(i));
		}

		sheet.setColumnWidth(0, 8 * 256);
		if (columnSize != null) {
			for (int index = 0; index < columnSize.length; index++) {
				sheet.setColumnWidth(index + 1, columnSize[index] * 256);
			}
		}

		Map<String, String> map = null;
		Object value = null;
		int row = 1;
		for (int i = 0; i < list.size(); i++) {
			if (list.get(i) instanceof Map) {
				map = (Map<String, String>) list.get(i);
			}
			hrow = sheet.createRow(row);
			hrow.setHeight((short) 350);
			if (headers != null) {
				for (int x = 0; x < headArr.size(); x++) {
					cell = hrow.createCell(x);
					cell.setCellStyle(style2);
					// 序号列
					if (x == 0) {
						cell.setCellValue(i + 1);
					} else {
						value = map.get(headArr.get(x));
						if (value != null
								&& StringUtils.isNotBlank(value.toString())) {
							cell.setCellValue(value.toString());
						}
					}

				}
			}
			row++;
		}

		try {
			workbook.write(out);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void exportJdccAndBhgxm(HttpServletResponse response,
			List<Map<String, String>> list,List<Map<String, String>> list1, String filename, String title,
			String title1,String[] heads,String[] bhgxms, Integer[] columnSize){
		java.io.OutputStream os = null;
		try {
			String filename2 = new String(filename.getBytes("GBK"), "ISO8859-1");
			response.addHeader("Content-Disposition", "attachment;filename="
					+ filename2);
			response.setContentType("application/ms-excel;charset=GBK");
			os = response.getOutputStream();
			
			toExcelJdccAndBhgxm(list,title,title1,list1,heads,columnSize,bhgxms,os);
			
			list.clear();
			list1.clear();
			list = null;
			list1 = null;
		} catch (Exception ex) {
			ex.printStackTrace(System.out);
		} finally {
			if (os != null) {
				try {
					os.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}

	public static void toExcelJdccAndBhgxm(List<Map<String, String>> list, String title,String title1,
			List<Map<String, String>> list1,String[] headers, Integer[] columnSize,
			String[] bhgxms,OutputStream out){// 默认加序号列
		List<String> headArr = new ArrayList<String>();
		headArr.add("序号");
		for (String header : headers) {
			headArr.add(header);
		}
		List<String> headArr1 = new ArrayList<String>();
		headArr1.add("序号");
		for (String header1 : bhgxms) {
			headArr1.add(header1);
		}

		// 声明一个工作薄
		HSSFWorkbook workbook = new HSSFWorkbook();
		// 生成一个表格
		HSSFSheet sheet = workbook.createSheet(title);
		HSSFSheet sheet1 = workbook.createSheet(title1);
		// 设置表格默认列宽度为15个字节
		sheet.setDefaultColumnWidth((short) 15);

		HSSFFont fontStyle = workbook.createFont();
		fontStyle.setFontName("宋体");
		fontStyle.setFontHeightInPoints((short) 14);

		// 生成一个样式
		HSSFCellStyle style = workbook.createCellStyle();
		// 标题行样式
		style.setFillForegroundColor(HSSFColor.SKY_BLUE.index);
		style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
		style.setBorderBottom(HSSFCellStyle.BORDER_THIN);
		style.setBorderLeft(HSSFCellStyle.BORDER_THIN);
		style.setBorderRight(HSSFCellStyle.BORDER_THIN);
		style.setBorderTop(HSSFCellStyle.BORDER_THIN);
		style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		style.setFont(fontStyle);

		// 表格内容样式
		HSSFFont fontStyle2 = workbook.createFont();
		fontStyle2.setFontName("宋体");
		fontStyle2.setFontHeightInPoints((short) 12);

		HSSFCellStyle style2 = workbook.createCellStyle();
		style2.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		style2.setFont(fontStyle2);

		// 产生表格标题行
		HSSFRow hrow = sheet.createRow(0);
		HSSFCell cell = null;
		for (short i = 0; i < headArr.size(); i++) {
			cell = hrow.createCell(i);
			cell.setCellStyle(style);
			// HSSFRichTextString text = new HSSFRichTextString(headers[i]);
			cell.setCellValue(headArr.get(i));
		}

		sheet.setColumnWidth(0, 8 * 256);
		if (columnSize != null) {
			for (int index = 0; index < columnSize.length; index++) {
				sheet.setColumnWidth(index + 1, columnSize[index] * 256);
			}
		}

		Map<String, String> map = null;
		Object value = null;
		int row = 1;
		for (int i = 0; i < list.size(); i++) {
			if (list.get(i) instanceof Map) {
				map = (Map<String, String>) list.get(i);
			}
			hrow = sheet.createRow(row);
			hrow.setHeight((short) 350);
			if (headers != null) {
				for (int x = 0; x < headArr.size(); x++) {
					cell = hrow.createCell(x);
					cell.setCellStyle(style2);
					// 序号列
					if (x == 0) {
						cell.setCellValue(i + 1);
					} else {
						value = map.get(headArr.get(x));
						if (value != null
								&& StringUtils.isNotBlank(value.toString())) {
							cell.setCellValue(value.toString());
						}
					}

				}
			}
			row++;
		}
		
		/***
		 * 不合格项目参数工作表
		 */
		
		// 产生表格标题行
		HSSFRow hrow1 = sheet1.createRow(0);
		HSSFCell cell1 = null;
		for (short i = 0; i < headArr1.size(); i++) {
			cell1 = hrow1.createCell(i);
			cell1.setCellStyle(style);
			// HSSFRichTextString text = new HSSFRichTextString(headers[i]);
			cell1.setCellValue(headArr1.get(i));
		}

		sheet1.setColumnWidth(0, 8 * 256);
		if (columnSize != null) {
			for (int index = 0; index < columnSize.length; index++) {
				sheet1.setColumnWidth(index + 1, columnSize[index] * 256);
			}
		}

		Map<String, String> map1 = null;
		Object value1 = null;
		int row1 = 1;
		for (int i = 0; i < list1.size(); i++) {
			if (list1.get(i) instanceof Map) {
				map1 = (Map<String, String>) list1.get(i);
			}
			hrow1 = sheet1.createRow(row1);
			hrow1.setHeight((short) 350);
			if (bhgxms != null) {
				for (int x = 0; x < headArr1.size(); x++) {
					cell1 = hrow1.createCell(x);
					cell1.setCellStyle(style2);
					// 序号列
					if (x == 0) {
						cell1.setCellValue(i + 1);
					} else {
						value1 = map1.get(headArr1.get(x));
						if (value1 != null && StringUtils.isNotBlank(value1.toString())) {
							cell1.setCellValue(value1.toString());
						}
					}

				}
			}
			row1++;
		}
				
				

		try {
			workbook.write(out);
		} catch (IOException e) {
			e.printStackTrace();
		}}
	
	
	

    /**
     * 导出双标题Excel
     * @param response
     * @param title
     * @param headers
     * @param list
     * @param filename
     * @param heads
     * @param columnSize
     */
    public static void exportDoubleTitle(HttpServletResponse response,String title, String[] headers, List<Map<String, String>> list, String filename,
			Integer[] columnSize){

		java.io.OutputStream os = null;
		try {
			String filename2 = new String(filename.getBytes("GBK"), "ISO8859-1");
			response.addHeader("Content-Disposition", "attachment;filename="
					+ filename2);
			response.setContentType("application/ms-excel;charset=GBK");
			os = response.getOutputStream();
			doExcel(list, title, headers, columnSize, os);
			list.clear();
			list = null;
		} catch (Exception ex) {
			ex.printStackTrace(System.out);
		} finally {
			if (os != null) {
				try {
					os.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	
    }

    @SuppressWarnings("deprecation")
	public static  void doExcel(List<Map<String, String>> list, String title,
			String[] headers,Integer[] columnSize, OutputStream out) {
		// 默认加序号列
		List<String> headArr = new ArrayList<String>();

		// 声明一个工作薄
		HSSFWorkbook workbook = new HSSFWorkbook();
		// 生成一个表格
		HSSFSheet sheet = workbook.createSheet(title);
		// 设置表格默认列宽度为15个字节
		sheet.setDefaultColumnWidth((short) 15);

		HSSFFont fontStyle = workbook.createFont();
		fontStyle.setFontName("宋体");
		fontStyle.setFontHeightInPoints((short) 14);

		// 生成一个样式
		HSSFCellStyle style = workbook.createCellStyle();
		// 标题行样式
		style.setFillForegroundColor(HSSFColor.SKY_BLUE.index);
		style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
		style.setBorderBottom(HSSFCellStyle.BORDER_THIN);
		style.setBorderLeft(HSSFCellStyle.BORDER_THIN);
		style.setBorderRight(HSSFCellStyle.BORDER_THIN);
		style.setBorderTop(HSSFCellStyle.BORDER_THIN);
		style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		style.setVerticalAlignment(CellStyle.VERTICAL_CENTER);//垂直居中
		style.setFont(fontStyle);

		// 表格内容样式
		HSSFFont fontStyle2 = workbook.createFont();
		fontStyle2.setFontName("宋体");
		fontStyle2.setFontHeightInPoints((short) 12);

		HSSFCellStyle style2 = workbook.createCellStyle();
		style2.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		style2.setFont(fontStyle2);

		// 产生表格标题行  
        HSSFRow row = sheet.createRow(0);  
        HSSFRow row2 = sheet.createRow(1);  
        for(short i = 0, n = 0; i < headers.length; i++){//i是headers的索引，n是Excel的索引  
            HSSFCell cell1 = row.createCell(n);  
            cell1.setCellStyle(style);  
            HSSFRichTextString text = null;  
            if(headers[i].contains(":")){//双标题  
                String[] temp = headers[i].split(":");  
                text = new HSSFRichTextString(temp[0]);  
                String[] tempSec = temp[1].split(",");  
                sheet.addMergedRegion(new Region(0, n, 0, (short) (n + tempSec.length -1)));  
                short tempI = n;  
                for(int j = 0; j < tempSec.length -1; j++){  
                    HSSFCell cellT = row.createCell(++tempI);  
                    cellT.setCellStyle(style);  
                }  
                for(int j = 0; j < tempSec.length; j++){  
                    HSSFCell cell2 = row2.createCell(n++);  
                    cell2.setCellStyle(style);  
                    cell2.setCellValue(new HSSFRichTextString(tempSec[j]));
                    headArr.add(tempSec[j]);
                }  
            }else{//单标题  
                HSSFCell cell2 = row2.createCell(n);  
                cell2.setCellStyle(style);  
                text = new HSSFRichTextString(headers[i]);  
                sheet.addMergedRegion(new Region(0, n, 1, n));  
                n++;
                headArr.add(headers[i]);
            }  
            cell1.setCellValue(text);  
        }  

		sheet.setColumnWidth(0, 8 * 256);
		if (columnSize != null) {
			for (int index = 0; index < columnSize.length; index++) {
				sheet.setColumnWidth(index + 1, columnSize[index] * 256);
			}
		}

		Map<String, String> map = null;
		Object value = null;
		int hrow = 2;
		for (int i = 0; i < list.size(); i++) {
			if (list.get(i) instanceof Map) {
				map = (Map<String, String>) list.get(i);
			}
			row = sheet.createRow(hrow);
			row.setHeight((short) 350);
			if (headers != null) {
				for (int x = 0; x < headArr.size(); x++) {
					HSSFCell cell = row.createCell(x);
					cell.setCellStyle(style2);
					// 序号列
					if (x == 0) {
						cell.setCellValue(i + 1);
					} else {
						value = map.get(headArr.get(x));
						if (value != null
								&& StringUtils.isNotBlank(value.toString())) {
							cell.setCellValue(value.toString());
						}
					}

				}
			}
			hrow++;
		}

		try {
			workbook.write(out);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
