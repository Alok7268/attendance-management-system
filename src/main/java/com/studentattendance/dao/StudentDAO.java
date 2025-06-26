package com.studentattendance.dao;

import com.studentattendance.model.Student;

import java.util.List;

public interface StudentDAO {
    void createTable();
    void insertStudent(Student student);
    void insertStudentsBatch(List<Student> students);
    List<Student> getAllStudents();
    Student getStudentById(int id);
    void updateStudent(Student student);
    void deleteStudent(int id);
    void clearAllStudents();
}
