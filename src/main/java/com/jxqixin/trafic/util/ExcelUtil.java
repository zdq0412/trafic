package com.jxqixin.trafic.util;

import com.jxqixin.trafic.model.Directory;
import com.jxqixin.trafic.model.Functions;
import com.jxqixin.trafic.model.SafetyInvestmentCategory;
import com.jxqixin.trafic.model.Schema;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
/**
 * 导入导出工具类
 * @author zdq
 */
public abstract class ExcelUtil {
	private SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
	public SimpleDateFormat getFormat() {
		return format;
	}
	// 总行数
	private int totalRows = 0;
	// 总条数
	private int totalCells = 0;
	//Excel的标题列表，也是实体类的属性列表
	private List<String> titlesList = new ArrayList<>();
	// 错误信息接收器
	private String errorMsg;
	
	public List<String> getTitlesList() {
		return titlesList;
	}
	public void setTitlesList(List<String> titlesList) {
		this.titlesList = titlesList;
	}
	public void setTotalRows(int totalRows) {
		this.totalRows = totalRows;
	}
	public void setTotalCells(int totalCells) {
		this.totalCells = totalCells;
	}
	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
	}
	public int getTotalRows() {
		return totalRows;
	}
	public int getTotalCells() {
		return totalCells;
	}
	public String getErrorMsg() {
		return errorMsg;
	}
	/**
	 * 设置日期 格式，默认为:yyyy-MM-dd
	 * @param pattern
	 */
	public void setPattern(String pattern) {
		format.applyPattern(pattern);
	}
	/**
	 * 获取Excel文档中的数据
	 * 约定：
	 * 1、Excel文件名称必须为实体类名称
	 * 2、Excel sheet0标题必须为实体类中的属性名称
	 * 3、日期为纯文本格式
	 * @param file
	 * @return
	 */
	public  Object getData(MultipartFile file){
		List<?> list = null;
		String fileName = file.getOriginalFilename();
		if (!validateExcel(fileName)) {// 验证文件名是否合格
			return null;
		}
		boolean isExcel2003 = true;// 根据文件名判断文件是2003版本还是2007版本
		if (isExcel2007(fileName)) {
			isExcel2003 = false;
		}
		try {
			list = createExcel(file.getInputStream(), isExcel2003);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return list;
	}

	public  List<Functions> getFunctionsData(File file){
		List<Functions> list = null;
		String fileName = file.getName();
		if (!validateExcel(fileName)) {// 验证文件名是否合格
			return null;
		}
		boolean isExcel2003 = true;// 根据文件名判断文件是2003版本还是2007版本
		if (isExcel2007(fileName)) {
			isExcel2003 = false;
		}
		try {
			list = (List<Functions>) createExcel(new FileInputStream(file), isExcel2003);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return list;
	}

	public  List<SafetyInvestmentCategory> getSafetyInvestmentCategoryData(File file){
		List<SafetyInvestmentCategory> list = null;
		String fileName = file.getName();
		if (!validateExcel(fileName)) {// 验证文件名是否合格
			return null;
		}
		boolean isExcel2003 = true;// 根据文件名判断文件是2003版本还是2007版本
		if (isExcel2007(fileName)) {
			isExcel2003 = false;
		}
		try {
			list = (List<SafetyInvestmentCategory>) createExcel(new FileInputStream(file), isExcel2003);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return list;
	}

	/**
	 * 导入模式数据
	 * @param file
	 * @return
	 */
	public  List<Schema> getSchemaData(File file){
		List<Schema> list = null;
		String fileName = file.getName();
		if (!validateExcel(fileName)) {// 验证文件名是否合格
			return null;
		}
		boolean isExcel2003 = true;// 根据文件名判断文件是2003版本还是2007版本
		if (isExcel2007(fileName)) {
			isExcel2003 = false;
		}
		try {
			list = (List<Schema>) createExcel(new FileInputStream(file), isExcel2003);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return list;
	}
	/**
	 * 导入目录数据，就是一级菜单
	 * @param file
	 * @return
	 */
	public  List<Directory> getDirectoryData(File file){
		List<Directory> list = null;
		String fileName = file.getName();
		if (!validateExcel(fileName)) {// 验证文件名是否合格
			return null;
		}
		boolean isExcel2003 = true;// 根据文件名判断文件是2003版本还是2007版本
		if (isExcel2007(fileName)) {
			isExcel2003 = false;
		}
		try {
			list = (List<Directory>) createExcel(new FileInputStream(file), isExcel2003);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return list;
	}
	/**
	 * 根据excel里面的内容读取客户信息
	 * @param is      输入流
	 * @param isExcel2003   excel是2003还是2007版本
	 * @return
	 * @throws IOException
	 */
	private List<?> createExcel(InputStream is, boolean isExcel2003) {
		try {
			Workbook wb = null;
			if (isExcel2003) {// 当excel是2003时,创建excel2003
				wb = new HSSFWorkbook(is);
			} else {// 当excel是2007时,创建excel2007
				wb = new XSSFWorkbook(is);
			}
			return readExcelValue(wb);
		} catch (IOException e) {
			e.printStackTrace();
		} 
		return null;
	}
	/**
	 * 读取Excel的信息
	 * @param wb
	 */
	public abstract List<?> readExcelValue(Workbook wb);
	/**
	 * 验证EXCEL文件
	 * 
	 * @param filePath
	 * @return
	 */
	private boolean validateExcel(String filePath) {
		if (filePath == null || !(isExcel2003(filePath) || isExcel2007(filePath))) {
			errorMsg = "文件名不是excel格式";
			return false;
		}
		return true;
	}
	// @描述：是否是2003的excel，返回true是2003
	private boolean isExcel2003(String filePath) {
		return filePath.matches("^.+\\.(?i)(xls)$");
	}
	// @描述：是否是2007的excel，返回true是2007
	private boolean isExcel2007(String filePath) {
		return filePath.matches("^.+\\.(?i)(xlsx)$");
	}

	 /**
     * 导出Excel
     * @param sheetName sheet名称
     * @param title 标题
     * @param values 内容
     * @return
     */
    public static HSSFWorkbook getHSSFWorkbook(String sheetName, String []title, List<String[]> values){
        // 第一步，创建一个HSSFWorkbook，对应一个Excel文件
    	HSSFWorkbook wb = new HSSFWorkbook();
        // 第二步，在workbook中添加一个sheet,对应Excel文件中的sheet
        HSSFSheet sheet = wb.createSheet(sheetName);
        // 第三步，在sheet中添加表头第0行,注意老版本poi对Excel的行数列数有限制
        HSSFRow row = sheet.createRow(0);
        // 第四步，创建单元格，并设置值表头 设置表头居中
        HSSFCellStyle style = wb.createCellStyle();
        //声明列对象
        HSSFCell cell = null;
        //创建标题
        for(int i=0;i<title.length;i++){
            cell = row.createCell(i);
            cell.setCellValue(title[i]);
            cell.setCellStyle(style);
        }
        
        int sheetNameIndex = 0;
        int lineNumber = 1;
        //创建内容
        for(int i=0;i<values.size();i++){
        	if(i>0&&i%50000==0) {
        		sheet = wb.createSheet(sheetName+(sheetNameIndex++));
        		lineNumber = 1;
        		row = sheet.createRow(0);
        		for(int k=0;k<title.length;k++){
                    cell = row.createCell(k);
                    cell.setCellValue(title[k]);
                    cell.setCellStyle(style);
                }
        	}
            row = sheet.createRow(lineNumber++);

            for(int j=0;j<values.get(i).length;j++){
                //将内容按顺序赋给对应的列对象
                row.createCell(j).setCellValue(values.get(i)[j]);
            }
        }
        return wb;
    }
}
