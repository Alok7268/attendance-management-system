package com.studentattendance.dao;

import com.studentattendance.model.Absence;
import com.studentattendance.model.Student;

import java.time.LocalDate;
import java.util.List;

public interface AbsenceDAO {
    void createTable();
    void markAbsent(int studentId);
    List<Absence> getAllAbsences();
    List<Student> getAbsentStudents(LocalDate date);
    List<Absence> getAbsencesByDate(LocalDate date);
    void deleteAbsence(int id);
    void clearAllAbsences();
}
