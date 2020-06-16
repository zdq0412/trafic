package com.jxqixin.trafic.util;

import com.twostep.resume.model.Dictionary;
import com.twostep.resume.model.DictionaryMapping;
import com.twostep.resume.service.IDictionaryMappingService;
import com.twostep.resume.service.IDictionaryService;
import org.apache.poi.ss.usermodel.*;
import org.springframework.util.CollectionUtils;
import org.thymeleaf.util.StringUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
/**
 * 数据字典导入导出工具类
 * @author zdq
 */
public class DictionaryExcelUtil extends ExcelUtil {
	private IDictionaryService dictionaryService;
	private IDictionaryMappingService dictionaryMappingService;
	public DictionaryExcelUtil(IDictionaryService dictionaryService,IDictionaryMappingService dictionaryMappingService){
		this.dictionaryMappingService = dictionaryMappingService;
		this.dictionaryService = dictionaryService;
	}

	public static List<String> cols = new ArrayList<>();
	static {
		cols.add("公司名称");
		cols.add("城市名称");
		cols.add("区域名称");
		cols.add("地点名称");
	}
	@Override
	public List<?> readExcelValue(Workbook wb) {
		// 得到第一个shell
		Sheet sheet = wb.getSheetAt(0);
		// 得到Excel的行数
		this.setTotalRows(sheet.getPhysicalNumberOfRows());
		// 得到Excel的列数(前提是有行数)
		if (getTotalRows() > 1 && sheet.getRow(0) != null) {
			this.setTotalCells(sheet.getRow(0).getPhysicalNumberOfCells());
		}else {
			throw new RuntimeException("无导入数据!");
		}
		Row firstRow = sheet.getRow(0);
		int cellNum = firstRow.getPhysicalNumberOfCells();
		if(cellNum!=cols.size()) {
			throw new RuntimeException("请使用正确模版!");
		}
		List<Dictionary> list = new ArrayList<>();
		// 循环Excel行数
		for (int r = 1; r < getTotalRows(); r++) {
			Row row = sheet.getRow(r);
			if (row == null) {
				continue;
			}
			Dictionary company = null;
			Dictionary city = null;
			Dictionary department = null;
			Dictionary position = null;
			for (int c = 0; c < this.getTotalCells(); c++) {
				Dictionary dictionary = new Dictionary();
				Cell cell = row.getCell(c);
				if (null != cell) {
					cell.setCellType(CellType.STRING);
					switch(c) {
						//公司名称
					case 0:{
						String text = cell.getStringCellValue();
						if(StringUtils.isEmpty(text)){
							break;
						}
						List<Dictionary> dictionaryList = dictionaryService.findByTextAndCategory(text,"公司名称");
						if(CollectionUtils.isEmpty(dictionaryList)){
							dictionary.setCategory("公司名称");
							dictionary.setText(text);
							dictionary.setCreateDate(new Date());
							company = dictionaryService.addObj(dictionary);
						}else{
							company = dictionaryList.get(0);
						}
						break;
					}
					//城市名称
					case 1:{
						String text = cell.getStringCellValue();
						if(StringUtils.isEmpty(text)){
							break;
						}
						List<Dictionary> dictionaryList = dictionaryService.findByTextAndCategory(text,"城市名称");
						if(CollectionUtils.isEmpty(dictionaryList)){
							dictionary.setCategory("城市名称");
							dictionary.setText(text);
							dictionary.setCreateDate(new Date());
							city = dictionaryService.addObj(dictionary);
						}else{
							city = dictionaryList.get(0);
						}
						break;
					}
					//区域名称
					case 2:{
						String text = cell.getStringCellValue();
						if(StringUtils.isEmpty(text)){
							break;
						}
						List<Dictionary> dictionaryList = dictionaryService.findByTextAndCategory(text,"区域名称");
						if(CollectionUtils.isEmpty(dictionaryList)){
							dictionary.setCategory("区域名称");
							dictionary.setText(text);
							dictionary.setCreateDate(new Date());
							department = dictionaryService.addObj(dictionary);
						}else{
							department = dictionaryList.get(0);
						}
						if(company!=null){
							DictionaryMapping dictionaryMapping = new DictionaryMapping();
							dictionaryMapping.setParent(company);
							dictionaryMapping.setSelf(department);
							dictionaryMappingService.addObj(dictionaryMapping);
						}
						break;
					}
					//地点名称
					case 3:{
						String text = cell.getStringCellValue();
						if(StringUtils.isEmpty(text)){
							break;
						}
						List<Dictionary> dictionaryList = dictionaryService.findByTextAndCategory(text,"地点名称");
						if(CollectionUtils.isEmpty(dictionaryList)){
							dictionary.setCategory("地点名称");
							dictionary.setText(text);
							dictionary.setCreateDate(new Date());
							position = dictionaryService.addObj(dictionary);
						}else{
							position = dictionaryList.get(0);
						}
						if(department!=null){
							DictionaryMapping dictionaryMapping = new DictionaryMapping();
							dictionaryMapping.setParent(department);
							dictionaryMapping.setSelf(position);
							dictionaryMappingService.addObj(dictionaryMapping);
						}
						break;
					}
					}
				}
			}
			company = null;
			city = null;
			position = null;
			department = null;
		}
		return list;
	}
}
