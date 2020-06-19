package com.jxqixin.trafic.util;

import com.jxqixin.trafic.model.Directory;
import com.jxqixin.trafic.model.Schema;
import com.jxqixin.trafic.service.ISchemaService;
import org.apache.poi.ss.usermodel.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 导入目录(一级菜单)记录
 */
public class ImportDirectoryUtil extends ExcelUtil {
    private ISchemaService schemaService;
    public ImportDirectoryUtil(ISchemaService schemaService){
        this.schemaService = schemaService;
    }
    @Override
    public List<?> readExcelValue(Workbook wb) {
        // 得到第一个shell
        Sheet sheet = wb.getSheetAt(0);
        // 得到Excel的行数
        this.setTotalRows(sheet.getPhysicalNumberOfRows());
        Row firstRow = sheet.getRow(0);
        this.setTotalCells(firstRow.getPhysicalNumberOfCells());
        List<Directory> list = new ArrayList<Directory>();
        // 循环Excel行数
        for (int r = 1; r < getTotalRows(); r++) {
            Row row = sheet.getRow(r);
            if (row == null) {
                continue;
            }
            Directory directory = new Directory();
            directory.setCreateDate(new Date());
            for (int c = 0; c < this.getTotalCells(); c++) {
                Cell cell = row.getCell(c);
                if (null != cell) {
                    cell.setCellType(CellType.STRING);
                    switch(c) {
                        //id
                        case 0:{
                            String id = cell.getStringCellValue();
                            directory.setId(id);
                            break;
                        }
                        //目录名称
                        case 1:{
                            String schemaName = cell.getStringCellValue();
                            directory.setName(schemaName);
                            break;
                        }
                        //icon
                        case 2:{
                            String icon = cell.getStringCellValue();
                            directory.setIcon(icon);
                            break;
                        }
                        //唯一标识
                        case 3:{
                            break;
                        }
                        //所属模式ID
                        case 4:{
                            String schema_id = cell.getStringCellValue();
                            Schema schema = schemaService.queryObjById(schema_id);
                            directory.setSchema(schema);
                            break;
                        }
                        //优先级
                        case 5:{
                            String priority = cell.getStringCellValue();
                            directory.setPriority(Integer.valueOf(priority));
                            break;
                        }
                        //说明
                        case 6:{
                            String note = cell.getStringCellValue();
                            directory.setNote(note);
                            break;
                        }
                    }
                }
            }
            // 添加到list
            list.add(directory);
        }
        return list;
    }
}
