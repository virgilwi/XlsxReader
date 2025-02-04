package com.example.xlsxreader.service;

import com.example.xlsxreader.utils.QuickSelect;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class XlsxService {
    public int getNthLargest(String filePath, int n) throws IOException {
        List<Integer> numbers = readNumbersFromExcel(filePath);
        return QuickSelect.findNthLargest(numbers, n);
    }

    private List<Integer> readNumbersFromExcel(String filePath) throws IOException {
        try (FileInputStream fis = new FileInputStream(new File(filePath));
             Workbook workbook = new XSSFWorkbook(fis)){
            Sheet sheet = workbook.getSheetAt(0);
            return StreamSupport.stream(sheet.spliterator(), false)
                    .map(row -> row.getCell(0)).filter(Objects::nonNull)
                    .filter(cell -> cell.getCellType() == CellType.NUMERIC || cell.getCellType() == CellType.STRING && cell.getStringCellValue().matches("\\d+"))
                    .map(cell -> cell.getCellType() == CellType.NUMERIC ? (int) cell.getNumericCellValue()
                                                                        : Integer.parseInt(cell.getStringCellValue()))
                    .collect(Collectors.toList());
        }
    }
}
