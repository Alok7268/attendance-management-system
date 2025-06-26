package com.studentattendance.model;

public class Student {
    private int id;
    private String studentName;
    private String rollNumber;
    private String courseName;

    // Constructors
    public Student() {}

    public Student(int id, String studentName, String rollNumber, String courseName) {
        this.id = id;
        this.studentName = studentName;
        this.rollNumber = rollNumber;
        this.courseName = courseName;
    }

    // Getters and setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public String getRollNumber() {
        return rollNumber;
    }

    public void setRollNumber(String rollNumber) {
        this.rollNumber = rollNumber;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }
}

