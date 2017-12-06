package com.kington.fshg.common.excel;


import java.io.File;
import java.io.FileInputStream;
import java.io.OutputStream;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
//import org.weijie.entity.Employee;

/******************************
  * 文件名称：ExcelExport.java
  * 功能：导出到Excel
  *****************************************/
public class ExcelExport<T> {
    //格式化日期
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    /**
     * 
     * @param title 标题
     * @param dataset 集合
     * @param out  输出流
     */
    @SuppressWarnings({ "rawtypes", "unchecked" })
	public void exportExcel(Collection<T> dataset, OutputStream out, File file) {
        // 声明一个工作薄
        try {
            //首先检查数据看是否是正确的
            Iterator<T> its = dataset.iterator();
            if(dataset==null||!its.hasNext()||out==null)
            {
                throw new Exception("传入的数据不对！");
            }
            //取得实际泛型类
            T ts = (T) its.next();
            Class tCls = ts.getClass();
            
            
            
            // 将传入的File构造为FileInputStream;
            FileInputStream in = new FileInputStream(file);
            //  得到工作表
            HSSFWorkbook workbook = new HSSFWorkbook(in);
            //  得到第一页
            HSSFSheet sheet = workbook.getSheetAt(0);
            // 生成一个样式
            HSSFCellStyle style = workbook.createCellStyle();
            // 设置标题样式
            style = ExcelStyle.setHeadStyle(workbook, style);

            // 得到所有字段
        
            Field filed[] = ts.getClass().getDeclaredFields();
            // 标题
            List<String> exportfieldtile = new ArrayList<String>();
            // 导出的字段的get方法
            List<Method> methodObj = new ArrayList<Method>();
            // 遍历整个filed
            for (int i = 0; i < filed.length; i++) {
                Field f = filed[i];
                ExcelAnnotation exa = f.getAnnotation(ExcelAnnotation.class);
                // 如果设置了annottion
                if (exa != null) {
                    String exprot = exa.exportName();
                    // 添加到标题
                    exportfieldtile.add(exprot);
                    // 添加到需要导出的字段的方法
                    String fieldname = f.getName();
                    String getMethodName = "get"
                            + fieldname.substring(0, 1).toUpperCase()
                            + fieldname.substring(1);
                    
                    Method getMethod = tCls.getMethod(getMethodName,new Class[] {});
                    
                    methodObj.add(getMethod);
                }
            }
            
         
            
            // 产生表格标题行
            HSSFRow row = sheet.createRow(6);
            for (int i = 0; i < exportfieldtile.size(); i++) {
                HSSFCell cell = row.createCell(i);
              //  cell.setCellStyle(style);
                HSSFRichTextString text = new HSSFRichTextString(exportfieldtile.get(i));
                cell.setCellValue(text);
            }

    
            int index = 0;
            
         // 生成一个样式
            HSSFCellStyle style2 = workbook.createCellStyle();
            style2 = ExcelStyle.setbodyStyle(workbook, style2);
            
            // 循环整个集合
            its = dataset.iterator();
            while (its.hasNext()) {
                //从第8行开始写，第6-7行是标题
                index++;
                row = sheet.createRow(index + 6);
                T t = (T) its.next();
                for (int k = 0; k < methodObj.size(); k++) {
                    HSSFCell cell = row.createCell(k);
                    Method getMethod=methodObj.get(k);
                    Object value = getMethod.invoke(t, new Object[] {});
                    String textValue = getValue(value);
                    cell.setCellStyle(style2);
                    cell.setCellValue(textValue);
                }

            }
            workbook.write(out);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private static final DecimalFormat df = new DecimalFormat("##.#");
    public String getValue(Object value) {
        String textValue = "";
        if (value == null)
            return textValue;

        if (value instanceof Boolean) {
            boolean bValue = (Boolean) value;
            textValue = "是";
            if (!bValue) {
                textValue = "否";
            }
        } else if (value instanceof Date) {
            Date date = (Date) value;
        
            textValue = sdf.format(date);
        }  else if (value instanceof Float) {
        	textValue = df.format(Float.valueOf(value.toString()));
        }	else {
        	textValue = value.toString();
        }

        return textValue;
    }

//    public static void main(String[] args) throws Exception {
//        
//    	File file = new File("E://template1.xls");
//    	
//    	
//        //构造一个模拟的List来测试，实际使用时，这个集合用从数据库中查出来
//        List<Employee> list = new ArrayList<Employee>();
//        for (int i = 0; i < 10; i++) {
//            Employee emp = new Employee();
//            emp.setCde("2122554412223322" + i);
//            emp.setPermit("zkzh"+i);
//            emp.setName("用户名"+i);
//            emp.setPhoto("");
//            emp.setEmail("Email"+i);
//            emp.setMphone("138888" + i);
//            
//            
//            list.add(emp);
//        }
//        //构造输出对象，可以从response输出，直接向用户提供下载
//        OutputStream out = new FileOutputStream("E://testOne1.xls");
//        //开始时间
//        Long l = System.currentTimeMillis();
//        //注意
//        ExcelExport<Employee> ex = new ExcelExport<Employee>();
//        //
//        ex.exportExcel("测试", list, out, file);
//        out.close();
//        //结束时间
//        Long s = System.currentTimeMillis();
//        System.out.println("总共耗时：" + (s - l));
//
//    }
}