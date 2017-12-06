package com.kington.fshg.common.excel;

import java.io.File;
import java.io.FileInputStream;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;

import com.kington.fshg.common.excel.vo.CustomsTypeExcelVO;
import com.kington.fshg.common.excel.vo.ProductTypeExcelVO;




/*******************************************************************************
 * 文件名称：ImportExcel.java 版本：
 * 功能：从Excel导入到数据库 
 * ******************************************************************************/

public class ImportExcel<T> {
    
    Class<T> clazz;

    public ImportExcel(Class<T> clazz) {
        this.clazz = clazz;
    }

    @SuppressWarnings({ "unchecked", "rawtypes" })
	public Collection<T> importExcel(File file, String... pattern) {
        Collection<T> dist = new ArrayList();
        try {
            /**
             * 类反射得到调用方法
             */
            // 得到目标目标类的所有的字段列表
            Field filed[] = clazz.getDeclaredFields();
            // 将所有标有Annotation的字段，也就是允许导入数据的字段,放入到一个map中
            Map fieldmap = new HashMap();
            // 循环读取所有字段
            for (int i = 0; i < filed.length; i++) {
                Field f = filed[i];
                // 得到单个字段上的Annotation
                ExcelAnnotation exa = f.getAnnotation(ExcelAnnotation.class);
                // 如果标识了Annotationd的话
                if (exa != null) {
                    // 构造设置了Annotation的字段的Setter方法
                    String fieldname = f.getName();
                    String setMethodName = "set" + fieldname.substring(0, 1).toUpperCase() + fieldname.substring(1);
                    // 构造调用的method
                    Method setMethod = clazz.getMethod(setMethodName,new Class[] { f.getType() });
                    // 将这个method以Annotaion的名字为key来存入。
                    fieldmap.put(exa.exportName(), setMethod);
                }
            }
            /**
             * excel的解析开始
             */
            // 将传入的File构造为FileInputStream;
            FileInputStream in = new FileInputStream(file);
            // 得到工作表
            HSSFWorkbook book = new HSSFWorkbook(in);
            // 得到第一页
            HSSFSheet sheet = book.getSheetAt(0);
            // 得到第一面的所有行
            Iterator<Row> row = sheet.rowIterator();
  

            List l = new ArrayList();
            for (; row.hasNext();) {

    			Row item = row.next();
    			if (item != null) {
    				Cell cell = item.getCell(0);
    				if (cell != null) {
    					if(cell.getCellType()== Cell.CELL_TYPE_BLANK) continue;
    						if(cell.getCellType() == Cell.CELL_TYPE_STRING ){
    							if ("序号".equals(cell.getStringCellValue().trim())) {
    								l.add(item);
    							}else{
    								try {
    									Integer.parseInt(cell.getStringCellValue().trim());
    									l.add(item);
    								} catch (Exception e) {
    								}
    							}
    						}else if(cell.getCellType() == Cell.CELL_TYPE_NUMERIC){
    							l.add(item);
    						}
    					
    				}
    				
    			}
            }

            row = l.iterator();

            /**
             * 标题解析
             */
            // 得到第一行，也就是标题行
            Row title = row.next();
            // 得到第一行的所有列
            Iterator<Cell> cellTitle = title.cellIterator();
            // 将标题的文字内容放入到一个map中。
            Map titlemap = new HashMap();
            // 从标题第一列开始
            int i = 0;
            // 循环标题所有的列
            while (cellTitle.hasNext()) {
                Cell cell = cellTitle.next();
                String value = cell.getStringCellValue().trim();
                titlemap.put(i, value);
                i = i + 1;
            }
            /**
             * 解析内容行
             */
            // 用来格式化日期的DateFormat
            SimpleDateFormat sf;
            if (pattern.length < 1) {
                sf = new SimpleDateFormat("yyyy-MM-dd");
            } else
                sf = new SimpleDateFormat(pattern[0]);
            
            int colSize = titlemap.size();
            while (row.hasNext()) {
                // 标题下的第一行
                Row rown = row.next();

                // 行的所有列
                // 得到传入类的实例
                T tObject = clazz.newInstance();
                int k = 0;
                // 遍历一行的列
                for (int idx = 0 ; idx < colSize; idx ++) {
                    Cell cell = rown.getCell(idx);
               //     System.out.println("..>>>>"+idx);
                    //判断表格里的内容是否为空
                    if(cell == null || cell.getCellType() == Cell.CELL_TYPE_BLANK){
                    	k=k+1;
                    	continue;
                    }
                    if(cell.getCellType() == Cell.CELL_TYPE_STRING){
                    	if(StringUtils.isBlank(cell.getStringCellValue().trim())){
                    		k=k+1;
                        	continue;
                        }
                    }
                    
                    // 这里得到此列的对应的标题
                    String titleString = (String) titlemap.get(k);
                    // 如果这一列的标题和类中的某一列的Annotation相同，那么则调用此类的的set方法，进行设值
                    if (fieldmap.containsKey(titleString)) {
                        Method setMethod = (Method) fieldmap.get(titleString);
                        // 得到setter方法的参数
                        Type[] ts = setMethod.getGenericParameterTypes();
                        // 只要一个参数
                        String xclass = ts[0].toString();
                        // 判断参数类型
                        if (xclass.equals("class java.lang.String")) {
                            if (cell.getCellType() == Cell.CELL_TYPE_NUMERIC) {
                            	setMethod.invoke(tObject, new Long((long)cell.getNumericCellValue()).toString().trim());
                            } else if (cell.getCellType() == Cell.CELL_TYPE_STRING) {
                            	setMethod.invoke(tObject, cell.getStringCellValue().trim());
                            }
                        } else if (xclass.equals("class java.util.Date")) {
                           if (cell.getCellType() == Cell.CELL_TYPE_STRING ) {
                        	   setMethod.invoke(tObject, sf.parse(cell.getStringCellValue().trim()));
                           } else if(cell.getCellType() == Cell.CELL_TYPE_NUMERIC) {
                        	   setMethod.invoke(tObject, cell.getDateCellValue());
                           }
                           
                        } else if (xclass.equals("class java.lang.Boolean")) {
                            Boolean boolname = true;
                            if (cell.getStringCellValue().trim().equals("否")) {
                                boolname = false;
                            }
                            setMethod.invoke(tObject, boolname);
                        } else if (xclass.equals("class java.lang.Integer")) {
                            setMethod.invoke(tObject, new Integer(cell.getStringCellValue().trim()));
                        }
                        else if (xclass.equals("int")) {
                        	if (cell.getCellType() == Cell.CELL_TYPE_NUMERIC) {
                        		setMethod.invoke(tObject, new Integer((int) cell.getNumericCellValue()));
                        	} else if (cell.getCellType() == Cell.CELL_TYPE_STRING) {
                        		try {
    								Integer.parseInt(cell.getStringCellValue().trim());
    								setMethod.invoke(tObject, new Integer(cell.getStringCellValue().trim()));
    							} catch (Exception e) {
    							}
                        	}
                        }
                        else if (xclass.equals("class java.lang.Long")) {
                            setMethod.invoke(tObject, new Long(cell.getStringCellValue().trim()));
                        }
                        else if (xclass.equals("class java.lang.Double")) {
                            setMethod.invoke(tObject, new Double(cell.getNumericCellValue()));
                        }
                        else if (xclass.equals("class java.lang.Float")) {
                            if (cell.getCellType() == Cell.CELL_TYPE_NUMERIC) {
                            	setMethod.invoke(tObject, new Float(cell.getNumericCellValue()));
                            } else if (cell.getCellType() == Cell.CELL_TYPE_STRING) {
                            	try {
                            		Float.parseFloat(cell.getStringCellValue().trim());
    								setMethod.invoke(tObject, new Float(cell.getStringCellValue().trim()));
    							} catch (Exception e) {
    							}
                            }
                        }
                    }
                    // 下一列
                    k = k + 1;
                }
                dist.add(tObject);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return dist;
    }
    
    

    public Collection<ProductTypeExcelVO> importProdTypeExcel(File file,String... pattern){
    	Collection<ProductTypeExcelVO> dist=new ArrayList<ProductTypeExcelVO>();
    	try {
			FileInputStream in = new FileInputStream(file);
			HSSFWorkbook book=new  HSSFWorkbook(in);
			HSSFSheet sheet=book.getSheetAt(0);
			Iterator<Row> row=sheet.rowIterator();
			
			List l=new ArrayList();
			for(;row.hasNext();){
				Row item=row.next();
				if(item != null){
					Cell cell = item.getCell(0);
					if(cell != null){
						if(cell.getCellType()==Cell.CELL_TYPE_BLANK) continue;
						if(cell.getCellType()==Cell.CELL_TYPE_STRING){
							if("序号".equals(cell.getStringCellValue().trim())){
								l.add(item);
							}else{
								try{
									Integer.parseInt(cell.getStringCellValue().trim());
									l.add(item);
								}catch(Exception e){
								}
							}
						}else if(cell.getCellType()==Cell.CELL_TYPE_NUMERIC){
							l.add(item);
						}
					}
				}
			}
			
			row = l.iterator();
			//得到第一行，标题行
			Row title = row.next();
			//得到第一行的所有列
			Iterator<Cell> cellTitle =title.cellIterator();
			//将标题的文字内容放入map
			Map titlemap = new HashMap();
			//从标题第一列开始
			int i=0;
			//循环标题所有列
			while(cellTitle.hasNext()){
				Cell cell=cellTitle.next();
				String value=cell.getStringCellValue().trim();
				titlemap.put(i, value);
				i++;
			}
			/**
			 * 解析内容行
			 */
			int colSize=titlemap.size();
			ProductTypeExcelVO vo=null;
			while(row.hasNext()){
				Row rown=row.next();
				vo=new ProductTypeExcelVO();
				int k=0;
				//遍历一行的列
				for(int idx=0; idx<colSize;idx++){
					Cell cell=rown.getCell(idx, Row.RETURN_BLANK_AS_NULL);
					//判断表格里的内容是否为空
					if(cell==null || cell.getCellType()==Cell.CELL_TYPE_BLANK){
						k++;
						continue;
					}
					if(cell.getCellType()==Cell.CELL_TYPE_STRING){
						if(StringUtils.isBlank(cell.getStringCellValue().trim())){
							k++;
							continue;
						}
					}
					//这里得到此列的对应的标题
					String titleString=(String) titlemap.get(k);
					if(StringUtils.isNotBlank(titleString)){
						if(titleString.equals("所属分类")){
							vo.setProductType(cell.getStringCellValue().trim());
						}else if(titleString.equals("分类名称")){
							vo.setProductTypeName(cell.getStringCellValue().trim());
						}else if(titleString.equals("级别")){
							if(cell.getCellType()==Cell.CELL_TYPE_NUMERIC){
								String id=String.valueOf(cell.getNumericCellValue());
								vo.setLev(id);
							}else{
								vo.setLev(cell.getStringCellValue().trim());
							}
						}
					}else{
						continue;
					}
					//下一列
					k++;
				}
				dist.add(vo);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return dist;
    	
    }

    /* public static void main(String[] args) {
        ImportExcel<ExcelVo> test = new ImportExcel(ExcelVo.class);
        File file = new File("E://template.xls");
        Long befor = System.currentTimeMillis();
        final List<ExcelVo> result = (ArrayList<ExcelVo>) test.importExcel(file, 6);

        Long after = System.currentTimeMillis();
        System.out.println("此次操作共耗时：" + (after - befor) + "毫秒");
        
//        final ServiceFacade sf = new ServiceFacade();
        
        new Thread() {
            public void run() {
                for (int i = 0; i < result.size(); i++) {
                    final ExcelVo employee = result.get(i);
//                            sf.insertEmp(employee);

                    System.out.println("导入的信息为：" + employee.getCde()
//                    		 + "----" + employee.getPermit() + "---"
                            + "----" + employee.getName() + "---");
//                            + employee.getPhoto() + "-----"
//                            + employee.getEmail() + "-----"
//                            + employee.getMphone());
                }
            }
        }.start();
        System.out.println("共转化为List的行数为：" + result.size());
    }*/
}