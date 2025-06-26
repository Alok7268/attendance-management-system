package com.studentattendance.model;

import java.time.LocalDateTime;

public class Absence {
    private int id;
    private LocalDateTime dateTime;
    private int studentId;

    // Constructors
    public Absence() {}

    public Absence(int id, LocalDateTime dateTime, int studentId) {
        this.id = id;
        this.dateTime = dateTime;
        this.studentId = studentId;
    }

    // Getters and setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    public int getStudentId() {
        return studentId;
    }

    public void setStudentId(int studentId) {
        this.studentId = studentId;
    }
}
