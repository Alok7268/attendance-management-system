package com.studentattendance.ui;

import com.studentattendance.dao.AbsenceDAOImpl;
import com.studentattendance.dao.StudentDAOImpl;
import com.studentattendance.model.Student;
import com.studentattendance.utils.ExcelImporter;
import com.studentattendance.utils.SampleDataLoader;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.List;

public class StudentAttendanceApp extends JFrame {
    private StudentDAOImpl studentDAO;
    private AbsenceDAOImpl absenceDAO;
    private JButton importButton;
    private JButton sampleDataButton;
    private JButton attendanceButton;
    private JButton viewAbsenteesButton;
    private JLabel statusLabel;

    public StudentAttendanceApp() {
        studentDAO = new StudentDAOImpl();
        absenceDAO = new AbsenceDAOImpl();

        // Initialize database tables
        studentDAO.createTable();
        absenceDAO.createTable();

        initializeUI();
    }

    private void initializeUI() {
        setTitle("Student Attendance Management System");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Create main panel
        JPanel mainPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Title
        JLabel titleLabel = new JLabel("Student Attendance Management System");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        mainPanel.add(titleLabel, gbc);

        // Import Students Button
        importButton = new JButton("Import Students from Excel");
        importButton.setFont(new Font("Arial", Font.PLAIN, 14));
        importButton.addActionListener(new ImportButtonListener());
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 2;
        mainPanel.add(importButton, gbc);

        // Load Sample Data Button
        sampleDataButton = new JButton("Load Sample Data");
        sampleDataButton.setFont(new Font("Arial", Font.PLAIN, 14));
        sampleDataButton.addActionListener(new SampleDataButtonListener());
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        mainPanel.add(sampleDataButton, gbc);

        // Take Attendance Button
        attendanceButton = new JButton("Take Attendance");
        attendanceButton.setFont(new Font("Arial", Font.PLAIN, 14));
        attendanceButton.addActionListener(new AttendanceButtonListener());
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 2;
        mainPanel.add(attendanceButton, gbc);

        // View Absentees Button
        viewAbsenteesButton = new JButton("View Today's Absentees");
        viewAbsenteesButton.setFont(new Font("Arial", Font.PLAIN, 14));
        viewAbsenteesButton.addActionListener(new ViewAbsenteesButtonListener());
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridwidth = 2;
        mainPanel.add(viewAbsenteesButton, gbc);

        // Status Label
        statusLabel = new JLabel("Ready");
        statusLabel.setHorizontalAlignment(SwingConstants.CENTER);
        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.gridwidth = 2;
        mainPanel.add(statusLabel, gbc);

        add(mainPanel, BorderLayout.CENTER);

        // Set window properties
        setSize(400, 350);
        setLocationRelativeTo(null);
        setResizable(false);
    }

    private class ImportButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setFileFilter(new FileNameExtensionFilter("Excel Files", "xlsx", "xls"));

            int result = fileChooser.showOpenDialog(StudentAttendanceApp.this);
            if (result == JFileChooser.APPROVE_OPTION) {
                String filePath = fileChooser.getSelectedFile().getAbsolutePath();
                importStudents(filePath);
            }
        }
    }

    private void importStudents(String filePath) {
        try {
            statusLabel.setText("Importing students...");
            List<Student> students = ExcelImporter.importStudentsFromExcel(filePath);

            if (students.isEmpty()) {
                JOptionPane.showMessageDialog(this, "No valid student data found in the Excel file.",
                        "Import Error", JOptionPane.WARNING_MESSAGE);
                statusLabel.setText("Import failed - No data found");
                return;
            }

            // Clear existing students and import new ones
            studentDAO.clearAllStudents();
            studentDAO.insertStudentsBatch(students);

            JOptionPane.showMessageDialog(this,
                    "Successfully imported " + students.size() + " students!",
                    "Import Success", JOptionPane.INFORMATION_MESSAGE);
            statusLabel.setText("Imported " + students.size() + " students");

        } catch (IOException ex) {
            JOptionPane.showMessageDialog(this,
                    "Error reading Excel file: " + ex.getMessage(),
                    "Import Error", JOptionPane.ERROR_MESSAGE);
            statusLabel.setText("Import failed");
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this,
                    "Error importing students: " + ex.getMessage(),
                    "Import Error", JOptionPane.ERROR_MESSAGE);
            statusLabel.setText("Import failed");
        }
    }

    private class AttendanceButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            List<Student> students = studentDAO.getAllStudents();
            if (students.isEmpty()) {
                JOptionPane.showMessageDialog(StudentAttendanceApp.this,
                        "No students found. Please import students first.",
                        "No Students", JOptionPane.WARNING_MESSAGE);
                return;
            }

            AttendanceScreen attendanceScreen = new AttendanceScreen(students, absenceDAO);
            attendanceScreen.setVisible(true);
        }
    }

    private class SampleDataButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                statusLabel.setText("Loading sample data...");
                SampleDataLoader.loadSampleData();
                JOptionPane.showMessageDialog(StudentAttendanceApp.this,
                        "Sample data loaded successfully!\n10 students have been added to the database.",
                        "Sample Data Loaded",
                        JOptionPane.INFORMATION_MESSAGE);
                statusLabel.setText("Sample data loaded (10 students)");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(StudentAttendanceApp.this,
                        "Error loading sample data: " + ex.getMessage(),
                        "Error",
                        JOptionPane.ERROR_MESSAGE);
                statusLabel.setText("Failed to load sample data");
            }
        }
    }

    private class ViewAbsenteesButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            AbsenteesReportScreen reportScreen = new AbsenteesReportScreen(absenceDAO);
            reportScreen.setVisible(true);
        }
    }

    public static void main(String[] args) {
        System.out.println("CLASSPATH: " + System.getProperty("java.class.path"));
        System.out.flush();
        SwingUtilities.invokeLater(() -> {
            new StudentAttendanceApp().setVisible(true);
        });
    }
}
