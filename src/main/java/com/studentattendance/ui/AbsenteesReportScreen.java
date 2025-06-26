package com.studentattendance.ui;

import com.studentattendance.dao.AbsenceDAOImpl;
import com.studentattendance.model.Student;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.util.List;

public class AbsenteesReportScreen extends JFrame {
    private AbsenceDAOImpl absenceDAO;
    private JTable absenteesTable;
    private DefaultTableModel tableModel;
    private JLabel titleLabel;
    private JButton printButton;
    private JButton closeButton;

    public AbsenteesReportScreen(AbsenceDAOImpl absenceDAO) {
        this.absenceDAO = absenceDAO;
        initializeUI();
        loadAbsentees();
    }

    private void initializeUI() {
        setTitle("Today's Absentees Report");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        // Title panel
        JPanel titlePanel = new JPanel();
        titleLabel = new JLabel("Today's Absentees - " + LocalDate.now().toString());
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        titlePanel.add(titleLabel);
        add(titlePanel, BorderLayout.NORTH);

        // Create table model
        String[] columnNames = {"Sl No", "Student Name", "Roll Number", "Course Name"};
        tableModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Make table non-editable
            }
        };

        // Create and configure table
        absenteesTable = new JTable(tableModel);
        absenteesTable.setRowHeight(25);
        absenteesTable.getColumnModel().getColumn(0).setPreferredWidth(60);
        absenteesTable.getColumnModel().getColumn(1).setPreferredWidth(200);
        absenteesTable.getColumnModel().getColumn(2).setPreferredWidth(120);
        absenteesTable.getColumnModel().getColumn(3).setPreferredWidth(150);

        JScrollPane scrollPane = new JScrollPane(absenteesTable);
        add(scrollPane, BorderLayout.CENTER);

        // Button panel
        JPanel buttonPanel = new JPanel(new FlowLayout());
        
        printButton = new JButton("Print Report");
        printButton.setFont(new Font("Arial", Font.PLAIN, 14));
        printButton.addActionListener(new PrintButtonListener());
        buttonPanel.add(printButton);

        closeButton = new JButton("Close");
        closeButton.setFont(new Font("Arial", Font.PLAIN, 14));
        closeButton.addActionListener(e -> dispose());
        buttonPanel.add(closeButton);

        add(buttonPanel, BorderLayout.SOUTH);

        // Set window properties
        setSize(600, 400);
        setLocationRelativeTo(null);
    }

    private void loadAbsentees() {
        // Clear existing data
        tableModel.setRowCount(0);

        try {
            LocalDate today = LocalDate.now();
            List<Student> absentStudents = absenceDAO.getAbsentStudents(today);

            if (absentStudents.isEmpty()) {
                Object[] row = {"", "No absentees found for today", "", ""};
                tableModel.addRow(row);
            } else {
                for (int i = 0; i < absentStudents.size(); i++) {
                    Student student = absentStudents.get(i);
                    Object[] row = {
                        i + 1,
                        student.getStudentName(),
                        student.getRollNumber(),
                        student.getCourseName()
                    };
                    tableModel.addRow(row);
                }
            }

            // Update title with count
            titleLabel.setText("Today's Absentees - " + LocalDate.now().toString() + 
                             " (Total: " + absentStudents.size() + ")");

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this,
                "Error loading absentees: " + e.getMessage(),
                "Error",
                JOptionPane.ERROR_MESSAGE);
        }
    }

    private class PrintButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            printReport();
        }
    }

    private void printReport() {
        try {
            LocalDate today = LocalDate.now();
            List<Student> absentStudents = absenceDAO.getAbsentStudents(today);

            StringBuilder report = new StringBuilder();
            report.append("STUDENT ATTENDANCE REPORT\n");
            report.append("=========================\n");
            report.append("Date: ").append(today.toString()).append("\n");
            report.append("Total Absentees: ").append(absentStudents.size()).append("\n\n");

            if (absentStudents.isEmpty()) {
                report.append("No students were absent today.\n");
            } else {
                report.append("ABSENTEES LIST:\n");
                report.append("---------------\n");
                
                for (int i = 0; i < absentStudents.size(); i++) {
                    Student student = absentStudents.get(i);
                    report.append(String.format("%d. %-25s %-15s %s\n", 
                        i + 1,
                        student.getStudentName(),
                        student.getRollNumber(),
                        student.getCourseName()));
                }
            }

            // Display report in a dialog
            JTextArea textArea = new JTextArea(report.toString());
            textArea.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 12));
            textArea.setEditable(false);
            
            JScrollPane scrollPane = new JScrollPane(textArea);
            scrollPane.setPreferredSize(new Dimension(500, 400));
            
            JOptionPane.showMessageDialog(this, scrollPane, "Absentees Report", JOptionPane.INFORMATION_MESSAGE);

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this,
                "Error generating report: " + ex.getMessage(),
                "Error",
                JOptionPane.ERROR_MESSAGE);
        }
    }
}
