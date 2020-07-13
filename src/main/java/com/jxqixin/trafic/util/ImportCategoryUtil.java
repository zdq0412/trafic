package com.jxqixin.trafic.util;

import com.jxqixin.trafic.model.Category;
import org.apache.poi.ss.usermodel.*;
import org.thymeleaf.util.StringUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
/**
 * 导入类别
 */
public class ImportCategoryUtil extends ExcelUtil {
    @Override
    public List<?> readExcelValue(Workbook wb) {
        // 得到第一个shell
        Sheet sheet = wb.getSheetAt(0);
        // 得到Excel的行数
        this.setTotalRows(sheet.getPhysicalNumberOfRows());
        Row firstRow = sheet.getRow(0);
        this.setTotalCells(firstRow.getPhysicalNumberOfCells());
        List<Category> list = new ArrayList<Category>();
        // 循环Excel行数
        for (int r = 1; r < getTotalRows(); r++) {
            Row row = sheet.getRow(r);
            if (row == null) {
                continue;
            }
            Category category = new Category();
            category.setCreateDate(new Date());
            for (int c = 0; c < this.getTotalCells(); c++) {
                Cell cell = row.getCell(c);
                if (null != cell) {
                    cell.setCellType(CellType.STRING);
                    switch(c) {
                        //名称
                        case 0:{
                            String name = cell.getStringCellValue();
                            category.setName(name);
                            break;
                        }
                        //父类别名称
                        case 1:{
                            String parentName = cell.getStringCellValue();
                            if(!StringUtils.isEmpty(parentName)){
                                Category parent = new Category();
                                parent.setName(parentName);

                                category.setParent(parent);
                            }
                            break;
                        }
                        //描述
                        case 2:{
                            String note = cell.getStringCellValue();
                            category.setNote(note);
                            break;
                        }
                        //类别
                        case 3:{
                            String type = cell.getStringCellValue();
                            category.setType(type);
                            break;
                        }
                    }
                }
            }
            // 添加到list
            list.add(category);
        }
        return list;
    }
}
