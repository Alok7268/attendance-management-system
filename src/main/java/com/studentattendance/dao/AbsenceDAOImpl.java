package com.studentattendance.dao;

import com.studentattendance.model.Absence;
import com.studentattendance.model.Student;
import com.studentattendance.utils.Database;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class AbsenceDAOImpl implements AbsenceDAO {

    @Override
    public void createTable() {
        String sql = "CREATE TABLE IF NOT EXISTS Absence (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "date_time DATETIME NOT NULL," +
                "student_id INTEGER NOT NULL," +
                "FOREIGN KEY (student_id) REFERENCES Students(id)" +
                ")";
        
        try (Connection conn = Database.getConnection();
             Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void markAbsent(int studentId) {
        String sql = "INSERT INTO Absence(date_time, student_id) VALUES(?, ?)";
        
        try (Connection conn = Database.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, LocalDateTime.now().toString());
            pstmt.setInt(2, studentId);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Absence> getAllAbsences() {
        List<Absence> absences = new ArrayList<>();
        String sql = "SELECT * FROM Absence ORDER BY date_time DESC";
        
        try (Connection conn = Database.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            while (rs.next()) {
                Absence absence = new Absence();
                absence.setId(rs.getInt("id"));
                absence.setDateTime(LocalDateTime.parse(rs.getString("date_time")));
                absence.setStudentId(rs.getInt("student_id"));
                absences.add(absence);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return absences;
    }

    @Override
    public List<Student> getAbsentStudents(LocalDate date) {
        List<Student> students = new ArrayList<>();
        String sql = "SELECT s.* FROM Students s " +
                "INNER JOIN Absence a ON s.id = a.student_id " +
                "WHERE DATE(a.date_time) = ? " +
                "ORDER BY s.student_name";
        
        try (Connection conn = Database.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, date.toString());
            
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    Student student = new Student();
                    student.setId(rs.getInt("id"));
                    student.setStudentName(rs.getString("student_name"));
                    student.setRollNumber(rs.getString("roll_number"));
                    student.setCourseName(rs.getString("course_name"));
                    students.add(student);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return students;
    }

    @Override
    public List<Absence> getAbsencesByDate(LocalDate date) {
        List<Absence> absences = new ArrayList<>();
        String sql = "SELECT * FROM Absence WHERE DATE(date_time) = ? ORDER BY date_time DESC";
        
        try (Connection conn = Database.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, date.toString());
            
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    Absence absence = new Absence();
                    absence.setId(rs.getInt("id"));
                    absence.setDateTime(LocalDateTime.parse(rs.getString("date_time")));
                    absence.setStudentId(rs.getInt("student_id"));
                    absences.add(absence);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return absences;
    }

    @Override
    public void deleteAbsence(int id) {
        String sql = "DELETE FROM Absence WHERE id = ?";
        
        try (Connection conn = Database.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void clearAllAbsences() {
        String sql = "DELETE FROM Absence";
        
        try (Connection conn = Database.getConnection();
             Statement stmt = conn.createStatement()) {
            stmt.executeUpdate(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
