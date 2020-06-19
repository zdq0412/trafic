package com.jxqixin.trafic.util;

import com.jxqixin.trafic.model.Directory;
import com.jxqixin.trafic.model.Functions;
import com.jxqixin.trafic.model.Schema;
import com.jxqixin.trafic.service.IDirectoryService;
import com.jxqixin.trafic.service.IFunctionsService;
import org.apache.poi.ss.usermodel.*;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 导入权限(菜单)记录
 */
public class ImportFunctionUtil extends ExcelUtil {
    private IFunctionsService functionsService;
    public ImportFunctionUtil(IFunctionsService functionsService){
        this.functionsService = functionsService;
    }
    @Override
    public List<?> readExcelValue(Workbook wb) {
        // 得到第一个shell
        Sheet sheet = wb.getSheetAt(0);
        // 得到Excel的行数
        this.setTotalRows(sheet.getPhysicalNumberOfRows());
        Row firstRow = sheet.getRow(0);
        this.setTotalCells(firstRow.getPhysicalNumberOfCells());
        List<Functions> list = new ArrayList<Functions>();
        // 循环Excel行数
        for (int r = 1; r < getTotalRows(); r++) {
            Row row = sheet.getRow(r);
            if (row == null) {
                continue;
            }
            Functions functions = new Functions();
            functions.setCreateDate(new Date());
            functions.setUrl("");
            for (int c = 0; c < this.getTotalCells(); c++) {
                Cell cell = row.getCell(c);
                if (null != cell) {
                    cell.setCellType(CellType.STRING);
                    switch(c) {
                        //id
                        case 0:{
                            String id = cell.getStringCellValue();
                            functions.setId(id);
                            break;
                        }
                        //名称
                        case 1:{
                            String schemaName = cell.getStringCellValue();
                            functions.setName(schemaName);
                            break;
                        }
                        //icon
                        case 2:{
                            String icon = cell.getStringCellValue();
                            functions.setIcon(icon);
                            break;
                        }
                        //唯一标识
                        case 3:{
                            String index = cell.getStringCellValue();
                            functions.setIndex(index);
                            break;
                        }
                        //父ID
                        case 4:{
                            String pid = cell.getStringCellValue();
                            if(!StringUtils.isEmpty(pid)) {
                                Functions parent = functionsService.queryObjById(pid);
                                functions.setParent(parent);
                            }
                            break;
                        }
                        //type类型：菜单或功能
                        case 5:{
                            String type = cell.getStringCellValue();
                            functions.setType(type);
                            break;
                        }
                        //优先级
                        case 6:{
                            String priority = cell.getStringCellValue();
                            functions.setPriority(Integer.valueOf(priority));
                            break;
                        }
                        //说明
                        case 7:{
                            String note = cell.getStringCellValue();
                            functions.setNote(note);
                            break;
                        }
                        //是否为叶子节点
                        case 8:{
                            String isLeaf = cell.getStringCellValue();
                            functions.setLeaf("1".equals(isLeaf)?true:false);
                            break;
                        }
                    }
                }
            }
            // 添加到list
            list.add(functions);
        }
        return list;
    }
}
