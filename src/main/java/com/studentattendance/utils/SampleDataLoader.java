package com.studentattendance.utils;

import com.studentattendance.dao.StudentDAOImpl;
import com.studentattendance.model.Student;

import java.util.Arrays;
import java.util.List;

public class SampleDataLoader {
    
    public static void loadSampleData() {
        StudentDAOImpl studentDAO = new StudentDAOImpl();
        
        // Clear existing data
        studentDAO.clearAllStudents();
        
        // Create sample students
        List<Student> sampleStudents = Arrays.asList(
            new Student(0, "John Smith", "CS001", "Computer Science"),
            new Student(0, "Emma Johnson", "CS002", "Computer Science"),
            new Student(0, "Michael Brown", "CS003", "Computer Science"),
            new Student(0, "Sarah Davis", "CS004", "Computer Science"),
            new Student(0, "David Wilson", "CS005", "Computer Science"),
            new Student(0, "Lisa Anderson", "CS006", "Computer Science"),
            new Student(0, "James Miller", "CS007", "Computer Science"),
            new Student(0, "Jennifer Taylor", "CS008", "Computer Science"),
            new Student(0, "Robert Thomas", "CS009", "Computer Science"),
            new Student(0, "Amanda Jackson", "CS010", "Computer Science")
        );
        
        // Insert sample data
        studentDAO.insertStudentsBatch(sampleStudents);
        
        System.out.println("Sample data loaded successfully!");
    }
}
