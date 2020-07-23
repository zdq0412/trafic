package com.jxqixin.trafic.util;
import com.jxqixin.trafic.model.SafetyInvestmentCategory;
import org.apache.poi.ss.usermodel.*;
import java.util.ArrayList;
import java.util.List;
/**
 * 导入安全生产费用支出类别
 */
public class ImportSafetyInvestmentCategoryUtil extends ExcelUtil {
    @Override
    public List<?> readExcelValue(Workbook wb) {
        // 得到第一个shell
        Sheet sheet = wb.getSheetAt(0);
        // 得到Excel的行数
        this.setTotalRows(sheet.getPhysicalNumberOfRows());
        Row firstRow = sheet.getRow(0);
        this.setTotalCells(firstRow.getPhysicalNumberOfCells());
        List<SafetyInvestmentCategory> list = new ArrayList<SafetyInvestmentCategory>();
        // 循环Excel行数
        for (int r = 1; r < getTotalRows(); r++) {
            Row row = sheet.getRow(r);
            if (row == null) {
                continue;
            }
            SafetyInvestmentCategory safetyInvestmentCategory = new SafetyInvestmentCategory();
            for (int c = 0; c < this.getTotalCells(); c++) {
                Cell cell = row.getCell(c);
                if (null != cell) {
                    cell.setCellType(CellType.STRING);
                    switch(c) {
                        //serialNo
                        case 0:{
                            String serialNo = cell.getStringCellValue();
                            safetyInvestmentCategory.setSerialNo(serialNo);
                            break;
                        }
                        //名称
                        case 1:{
                            String name = cell.getStringCellValue();
                            safetyInvestmentCategory.setName(name);
                            break;
                        }
                    }
                }
            }
            // 添加到list
            list.add(safetyInvestmentCategory);
        }
        return list;
    }
}
