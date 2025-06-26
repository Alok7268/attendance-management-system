package com.studentattendance.utils;

import com.studentattendance.model.Student;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ExcelImporter {

    public static List<Student> importStudentsFromExcel(String filePath) throws IOException {
        List<Student> students = new ArrayList<>();
        
        try (FileInputStream fis = new FileInputStream(filePath);
             Workbook workbook = new XSSFWorkbook(fis)) {
            
            Sheet sheet = workbook.getSheetAt(0); // Get first sheet
            
            // Skip header row (assuming first row contains headers)
            boolean isFirstRow = true;
            for (Row row : sheet) {
                if (isFirstRow) {
                    isFirstRow = false;
                    continue;
                }
                
                if (row.getPhysicalNumberOfCells() < 3) {
                    continue; // Skip rows with insufficient data
                }
                
                Student student = new Student();
                
                // Column 0: Student Name
                Cell nameCell = row.getCell(0);
                if (nameCell != null) {
                    student.setStudentName(getCellValueAsString(nameCell));
                }
                
                // Column 1: Roll Number
                Cell rollCell = row.getCell(1);
                if (rollCell != null) {
                    student.setRollNumber(getCellValueAsString(rollCell));
                }
                
                // Column 2: Course Name
                Cell courseCell = row.getCell(2);
                if (courseCell != null) {
                    student.setCourseName(getCellValueAsString(courseCell));
                }
                
                // Only add student if all required fields are present
                if (student.getStudentName() != null && !student.getStudentName().isEmpty() &&
                    student.getRollNumber() != null && !student.getRollNumber().isEmpty() &&
                    student.getCourseName() != null && !student.getCourseName().isEmpty()) {
                    students.add(student);
                }
            }
        }
        
        return students;
    }
    
    private static String getCellValueAsString(Cell cell) {
        if (cell == null) {
            return "";
        }
        
        switch (cell.getCellType()) {
            case STRING:
                return cell.getStringCellValue().trim();
            case NUMERIC:
                if (DateUtil.isCellDateFormatted(cell)) {
                    return cell.getDateCellValue().toString();
                } else {
                    return String.valueOf((long) cell.getNumericCellValue());
                }
            case BOOLEAN:
                return String.valueOf(cell.getBooleanCellValue());
            case FORMULA:
                return cell.getCellFormula();
            default:
                return "";
        }
    }
}
