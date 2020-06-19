package com.jxqixin.trafic.util;
import com.jxqixin.trafic.model.Schema;
import org.apache.poi.ss.usermodel.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
/**
 * 导入模式记录
 */
public class ImportSchemaUtil extends ExcelUtil {
    @Override
    public List<?> readExcelValue(Workbook wb) {
        // 得到第一个shell
        Sheet sheet = wb.getSheetAt(0);
        // 得到Excel的行数
        this.setTotalRows(sheet.getPhysicalNumberOfRows());
        Row firstRow = sheet.getRow(0);
        this.setTotalCells(firstRow.getPhysicalNumberOfCells());
        List<Schema> list = new ArrayList<Schema>();
        // 循环Excel行数
        for (int r = 1; r < getTotalRows(); r++) {
            Row row = sheet.getRow(r);
            if (row == null) {
                continue;
            }
            Schema schema = new Schema();
            schema.setCreateDate(new Date());
            for (int c = 0; c < this.getTotalCells(); c++) {
                Cell cell = row.getCell(c);
                if (null != cell) {
                    cell.setCellType(CellType.STRING);
                    switch(c) {
                        //id
                        case 0:{
                            String id = cell.getStringCellValue();
                            schema.setId(id);
                            break;
                        }
                        //模式名称
                        case 1:{
                            String schemaName = cell.getStringCellValue();
                            schema.setName(schemaName);
                            break;
                        }
                        //备注
                        case 2:{
                            String note = cell.getStringCellValue();
                            schema.setNote(note);
                            break;
                        }
                        //优先级
                        case 3:{
                            String priority = cell.getStringCellValue();
                            schema.setPriority(Integer.valueOf(priority));
                            break;
                        }
                        //是否选中
                        case 4:{
                            String type = cell.getStringCellValue();
                            schema.setSelected("1".equals(type)?true:false);
                            break;
                        }
                    }
                }
            }
            // 添加到list
            list.add(schema);
        }
        return list;
    }
}
