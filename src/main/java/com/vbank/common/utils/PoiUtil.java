package com.vbank.common.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.jfinal.plugin.activerecord.Record;


/** 
 *  
 * @Project:zhuoyue_wc 
 * @Title:PoiUtil.java 
 * @Package:ccom.zhuoyue.utils
 * @Description: Java POI通用导入导出
 * @Author:Dyzeng 
 * @Date:2017年09月4日 下午2:36:46 
 * @Version: 
 */  
public class PoiUtil {

	private final static String excel2003L =".xls";    //2003- 版本的excel
	private final static String excel2007U =".xlsx";   //2007+ 版本的excel
	
	public static PoiUtil me = new PoiUtil();
	
	/*******************************Excel导入*******************************/

	/**
	 * 描述：获取IO流中的数据，组装成List<List<Object>>对象
	 * @param in,fileName
	 * @return
	 * @throws IOException 
	 */
	public static List<List<Object>> getExcelData(File file){
		List<List<Object>> list = null;
	
		String fileName = file.getName();
		FileInputStream in;
		try {
			in = new FileInputStream(file);
			//创建Excel工作薄
			Workbook work = me.getWorkbook(in,fileName);
			if(null == work){
				throw new Exception("Excel工作薄为空！");
			}
			Sheet sheet = null;
			Row row = null;
			Cell cell = null;
			
			list = new ArrayList<List<Object>>();
			sheet = work.getSheetAt(0);  //默认取第一个sheet
			//遍历当前sheet中的所有行
			for (int j = sheet.getFirstRowNum(); j <= sheet.getLastRowNum(); j++) {
				row = sheet.getRow(j);
				//if(row==null||row.getFirstCellNum()==j){continue;} //过滤表头第一行
				
				if(row==null){continue;}
				
				//遍历所有的列
				List<Object> li = new ArrayList<Object>();
				int emptyNum = 0;
				for (int y = row.getFirstCellNum(); y < row.getLastCellNum(); y++) {
					
					cell = row.getCell(y);
					Object cellValue = me.getCellValue(cell);
					li.add(cellValue);
//					System.out.println("当前列："+y+",值为："+cellValue);
//					if(null != cellValue){
//						li.add(cellValue);
//					}else{
//						li.add("");
//						System.out.println("**********--88****");
//					}
					
					if(StringUtils.isEmpty(String.valueOf(cellValue))){
						emptyNum ++;
					}
				}
				
				//当前行所有列全是空的则不添加
				if((emptyNum>0 && row.getLastCellNum()==emptyNum)){
					continue;
				}
				list.add(li);
			}

			work.close();
			//((InputStream) work).close();
		}catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		//System.out.println(list);
		return list;
	}
	
	
	/**
	 * 描述：根据文件后缀，自适应上传文件的版本 
	 * @param inStr,fileName
	 * @return
	 * @throws Exception
	 */
	private Workbook getWorkbook(InputStream inStr,String fileName) throws Exception{
		Workbook wb = null;
		String fileType = fileName.substring(fileName.lastIndexOf("."));
		if(excel2003L.equals(fileType)){
			wb = new HSSFWorkbook(inStr);  //2003-
		}else if(excel2007U.equals(fileType)){
			wb = new XSSFWorkbook(inStr);  //2007+
		}else{
			throw new Exception("解析的文件格式有误！");
		}
		return wb;
	}

	
	/**
	 * 描述：对表格中数值进行格式化
	 * @param cell
	 * @return
	 */
	private  Object getCellValue(Cell cell){
		Object value = null;
		DecimalFormat df = new DecimalFormat("0");  //格式化number String字符
		SimpleDateFormat sdf = new SimpleDateFormat("yyy-MM-dd");  //日期格式化
		DecimalFormat df2 = new DecimalFormat("0.00");  //格式化数字
		if(null != cell){
			switch (cell.getCellTypeEnum()) {
			case STRING:
				value = cell.getRichStringCellValue().getString();
				break;
			case NUMERIC:
				if("General".equals(cell.getCellStyle().getDataFormatString())){
					value = df.format(cell.getNumericCellValue());
				}else if("m/d/yy".equals(cell.getCellStyle().getDataFormatString())){
					value = sdf.format(cell.getDateCellValue());
				}else{
					value = df2.format(cell.getNumericCellValue());
				}
				break;
			case BOOLEAN:
				value = cell.getBooleanCellValue();
				break;
			case BLANK:
				value = "";
				break;
			default:
				break;
			}
		}
		
		return value;
	}
	
	
	
	/*******************************通用版Excel导出*******************************/
	/**
	 * 根据模板生成excel文件输出到客户端
	 * @param fileDir
	 * @param fileName
	 * @param sheetName
	 * @param lis
	 * @param response
	 */
	public static void createNewExcel(String fileDir,String fileName,String sheetName,
				List<Record> list,HttpServletResponse response){

		OutputStream os = null;
		Workbook wb = null; 
		try{
			if(null == sheetName || sheetName.length() <= 0){
				sheetName = "Sheet1"; //默认导出在第一个Sheet
			}
			if(null != fileDir && !"".equals(fileDir)){
				//如果有模板文件的路径
				wb = me.writeNewExcel(fileDir,sheetName,list);
			}else{
				//如果没有模板文件的路径，则创建一个新的excel
				wb = me.writeNewExcel(sheetName,list);
			}
			//输出到客户端
			response.reset();
	        response.setContentType("application/vnd.ms-excel");  
	        response.setHeader("Content-Disposition", "attachment;filename="+ URLEncoder.encode(fileName, "utf-8"));  
			
//	        response.setContentType("application/octet-stream; charset=utf-8");
//	        response.setHeader("Content-Disposition", "attachment; filename="+Encodes.urlEncode(fileName));
	        
	        os = response.getOutputStream();  
	        wb.write(os);
	        response.flushBuffer();
			
		 } catch (Exception e) { 
	         e.printStackTrace();  
	     } finally{  
			try{
				os.flush();
				os = null;
				//os.close();  
			    //wb.dispose();  ;
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}  
	     }  
	}
	
	
	/**
	 * 描述：根据文件路径获取项目中的文件
	 * @param fileDir 文件路径
	 * @return
	 * @throws Exception
	 */
	private File getExcelTemplateFile(String fileDir) throws Exception{
		//String classDir = null;
		//String fileBaseDir = null;
		File file = null;
		//classDir = Thread.currentThread().getContextClassLoader().getResource("/").getPath();
		//fileBaseDir = classDir.substring(0, classDir.lastIndexOf("classes"));
		
		//file = new File(fileBaseDir+fileDir);
		file = new File(fileDir);
		if(!file.exists()){
			throw new Exception("模板文件不存在！");
		}
		return file;
	}
	
	
	/**
	 * 往指定sheet填充数据(不需要模板文件)
	 * @param file
	 * @param sheetName
	 * @param list
	 * @return
	 * @throws Exception
	 */
	private  Workbook writeNewExcel(String sheetName,List<Record> list) throws Exception{
		Workbook wb = new XSSFWorkbook();
		Row row = null; 
		Cell cell = null;
		
		Sheet sheet = wb.createSheet(sheetName);  //创建一个新的sheet 
		
		//循环插入数据
		int lastRow = 0;//sheet.getLastRowNum();  //插入数据的数据ROW
		CellStyle simpleCs = me.setSimpleCellStyle(wb);    //Excel单元格简单样式
		CellStyle titleCs = me.setTitleRowCellStyle(wb);
		int columnNum = 0;
		columnNum = list.get(0).getColumns().size(); //第一行一般是表头行，作为表格的列数
		
		
		
		
		for (int i = 0; i < list.size(); i++) {
			row = sheet.createRow(lastRow+i); //创建新的ROW，用于数据插入
			//按项目实际需求，在该处将对象数据插入到Excel中
			Record record  = list.get(i);
			for(int j=0;j<record.getColumns().size();j++){
				//Cell赋值开始
				cell = row.createCell(j);
				cell.setCellValue(String.valueOf(record.get("col"+(j+1))));
				//System.out.println("---->>key= "+ "col"+j+1 + " and value= " + tmpMap.get("col"+(j+1)));
//				if(i== 0){
//					cell.setCellStyle(titleCs);//默认第一行表头行加粗
//				}else{
//					cell.setCellStyle(simpleCs);
//				}
				
				cell.setCellStyle(simpleCs);
			}
		}
		
		
		//让列宽随着导出的列长自动适应  
        for (int colNum = 0; colNum < columnNum; colNum++) {  
            int columnWidth = sheet.getColumnWidth(colNum) / 256;  
            for (int rowNum = 0; rowNum < sheet.getLastRowNum(); rowNum++) {  
                XSSFRow currentRow;  
                //当前行未被使用过  
                if (sheet.getRow(rowNum) == null) {  
                    currentRow = (XSSFRow)sheet.createRow(rowNum);  
                } else {  
                    currentRow = (XSSFRow)sheet.getRow(rowNum);  
                }  
                if (currentRow.getCell(colNum) != null) {  
                    XSSFCell currentCell = currentRow.getCell(colNum);  
                    //if (currentCell.getCellType() == XSSFCell.CELL_TYPE_STRING) {  
                        int length = currentCell.getStringCellValue().getBytes().length;  
                        if (columnWidth < length) {  
                            columnWidth = length;  
                        } 
                        
//                        if(columnWidth*2 <255*256){
//                            sheet.setColumnWidth(colNum,columnWidth < 60000 ? 60000 : columnWidth);
//                            System.out.println("-------------------------------11111111");
//                        }else{
//                            sheet.setColumnWidth(colNum,60000 );
//                            System.out.println("-------------------------------222222222");
//                        }
//                        
//                        sheet.setColumnWidth(colNum,255*256);

                    //}  
                }  
            }  
            if(colNum == 0){  
                sheet.setColumnWidth(colNum, (columnWidth-2) * 256);  
                //System.out.println("-------------------------------33333333");
            }else{  
                //sheet.setColumnWidth(colNum, (columnWidth+4) * 256); 
            	if(columnWidth >65280){
            		sheet.setColumnWidth(colNum,65280);
            	}
                //System.out.println("-------------------------------4444444444");
            }  
        }  

		return wb;
	}
	

	
	/**
	 * 往指定sheet填充数据（需要模板文件）
	 * @param file
	 * @param sheetName
	 * @param lis
	 * @return
	 * @throws Exception
	 */
	private Workbook writeNewExcel(String fileDir,String sheetName,List<Record> list) throws Exception{
		File file = me.getExcelTemplateFile(fileDir); //根据模板文件的目录获取模板文件
		
		Workbook wb = null;
		Row row = null; 
		Cell cell = null;
		
		
		FileInputStream fis = new FileInputStream(file);
		wb = me.getWorkbook(fis, file.getName()); //获取工作薄
		Sheet sheet = wb.getSheet(sheetName);
		
		//循环插入数据
		int lastRow = sheet.getLastRowNum()+1;    //插入数据的数据ROW
		CellStyle cs = me.setSimpleCellStyle(wb); //Excel单元格样式

		for (int i= 0; i< list.size(); i++) {
			row = sheet.createRow(lastRow+i); //创建新的ROW，用于数据插入
			//按项目实际需求，在该处将对象数据插入到Excel中
			Record record = list.get(i);
			for(int j=0;j<record.getColumns().size();j++){
				//Cell赋值开始
				String cellValue = String.valueOf(record.get("col"+(j+1)));
				cell = row.createCell(j);
				//System.out.println("-------->>key= "+ "col"+j+1 + " and value= " + tmpMap.get("col"+(j+1)));
				if(cellValue.contains("\r\n")){
					//如果有换行符号，需要设置换行样式
					cs.setWrapText(true);
				}
				cell.setCellStyle(cs);;
				cell.setCellValue(cellValue);
			}
		}
		
		return wb;
	}
	
	
	
	
	/**
	 * 描述：设置简单的Cell样式
	 * @return
	 */
	private CellStyle setSimpleCellStyle(Workbook wb){
		CellStyle cs = wb.createCellStyle();
		//cs.setWrapText(true);
		cs.setBorderBottom(CellStyle.BORDER_THIN); //下边框
		cs.setBorderLeft(CellStyle.BORDER_THIN);//左边框
		cs.setBorderTop(CellStyle.BORDER_THIN);//上边框
		cs.setBorderRight(CellStyle.BORDER_THIN);//右边框

		cs.setAlignment(CellStyle.ALIGN_LEFT); // 左对齐
		
		return cs;
	}
	
	
	/**
	 * 描述：设置第一行标题行的Cell样式
	 * @return
	 */
	private CellStyle setTitleRowCellStyle(Workbook wb){
		CellStyle cs = wb.createCellStyle();
		
		cs.setBorderBottom(CellStyle.BORDER_THIN); //下边框
		cs.setBorderLeft(CellStyle.BORDER_THIN);//左边框
		cs.setBorderTop(CellStyle.BORDER_THIN);//上边框
		cs.setBorderRight(CellStyle.BORDER_THIN);//右边框

		cs.setAlignment(CellStyle.ALIGN_LEFT); // 左对齐
		
		cs.setFillBackgroundColor(HSSFColor.GREEN.index);
		 // 创建字体对象   
        Font ztFont = wb.createFont();   
        ztFont.setItalic(false);                     // 设置字体为斜体字   
        ztFont.setColor(Font.COLOR_NORMAL);         // 将字体设置为“红色”   
        ztFont.setFontHeightInPoints((short)12);    // 将字体大小设置为12px   
        ztFont.setFontName("宋体");               // 将“华文行楷”字体应用到当前单元格上   
        ztFont.setBoldweight(Font.BOLDWEIGHT_BOLD);
		cs.setFont(ztFont);
		
		return cs;
	}
	
	

	/*-----------------------------------自定义样式导出Excel------------------------------------*/
	
	
	/**
	 * 往指定sheet填充数据（需要模板文件）
	 * @param file
	 * @param sheetName
	 * @param lis
	 * @return
	 * @throws Exception
	 */
	private Workbook writeNewExcelDefined(String fileDir,String sheetName,List<Record> list) throws Exception{
		File file = me.getExcelTemplateFile(fileDir); //根据模板文件的目录获取模板文件
		
		Workbook wb = null;
		Row row = null; 
		Cell cell = null;
		
		
		FileInputStream fis = new FileInputStream(file);
		wb = me.getWorkbook(fis, file.getName()); //获取工作薄
		Sheet sheet = wb.getSheet(sheetName);
		
		//循环插入数据
		int lastRow = sheet.getLastRowNum()+1;    //插入数据的数据ROW
		CellStyle cs = me.setSimpleCellStyle(wb); //Excel单元格样式

		for (int i= 0; i< list.size(); i++) {
			row = sheet.createRow(lastRow+i); //创建新的ROW，用于数据插入
			//按项目实际需求，在该处将对象数据插入到Excel中
			Record record = list.get(i);
			for(int j=0;j<record.getColumns().size();j++){
				//Cell赋值开始
				String cellValue = String.valueOf(record.get("col"+(j+1)));
				cell = row.createCell(j);
				//System.out.println("-------->>key= "+ "col"+j+1 + " and value= " + tmpMap.get("col"+(j+1)));
				if(cellValue.contains("\r\n")){
					//如果有换行符号，需要设置换行样式
					cs.setWrapText(true);
				}
				cell.setCellStyle(cs);;
				cell.setCellValue(cellValue);
			}
		}
		
		return wb;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	//测试导入导出
    public static void main(String[] args) throws Exception {  
    	/*------------------------测试导入------------------------*/
    	
    	
    	
    	
    	/*------------------------测试导出------------------------*/
    	//按条件从数据库取列表数据  
        List<Record> listVo =null;// Team.dao.findQnrAnswerExport(qnrId); 
        if(null == listVo){
    		listVo = new ArrayList<Record>();
    	}
        //拼接表头第一行
        Record titleRow = new Record();
    	titleRow.set("col1", "编号");
    	titleRow.set("col2", "学员姓名");
    	titleRow.set("col3", "学员手机");
    	titleRow.set("col4", "已使用券");
    	titleRow.set("col5", "用户类型");
    	titleRow.set("col6", "领取时间");
    	titleRow.set("col7", "最近登录时间");
    	listVo.add(0, titleRow);		
        
        //导出Excel文件数据 ,fileDir为空表示直接生一个新的excel,否则按模板导出.
        String fileDir = ""; //request.getSession().getServletContext().getRealPath("static")+File.separator+"excelTemplates"+File.separator+"活动券领取详情导出模板.xlsx";
//        String fileName= teamQuestionnaire.getStr("qnrName")+DateUtils.getDate("yyyyMMddHHmmss")+"总领取详情报表.xls";  
//        String sheetName="Sheet1";    
//        PoiUtil.createNewExcel(fileDir,fileName,sheetName,listVo,getResponse()); //生成一个excel输出到客户端  
        
        
        
    }  
	
}
