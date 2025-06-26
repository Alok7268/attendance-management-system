package com.studentattendance.ui;

import com.studentattendance.dao.AbsenceDAOImpl;
import com.studentattendance.model.Student;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.HashSet;
import java.util.Set;

public class AttendanceScreen extends JFrame {
    private List<Student> students;
    private AbsenceDAOImpl absenceDAO;
    private JTable studentTable;
    private DefaultTableModel tableModel;
    private JButton presentButton;
    private JButton absentButton;
    private JButton finishButton;
    private JLabel progressLabel;
    private int currentStudentIndex = 0;
    private Set<Integer> absentStudents = new HashSet<>();

    public AttendanceScreen(List<Student> students, AbsenceDAOImpl absenceDAO) {
        this.students = students;
        this.absenceDAO = absenceDAO;
        initializeUI();
        updateDisplay();
    }

    private void initializeUI() {
        setTitle("Take Attendance");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        // Create table model
        String[] columnNames = {"Sl No", "Name", "Roll Number", "Status"};
        tableModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Make table non-editable
            }
        };

        // Populate table with students
        for (int i = 0; i < students.size(); i++) {
            Student student = students.get(i);
            Object[] row = {
                i + 1,
                student.getStudentName(),
                student.getRollNumber(),
                "Present" // Default status
            };
            tableModel.addRow(row);
        }

        // Create and configure table
        studentTable = new JTable(tableModel);
        studentTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        studentTable.setRowHeight(25);
        studentTable.getColumnModel().getColumn(0).setPreferredWidth(60);
        studentTable.getColumnModel().getColumn(1).setPreferredWidth(200);
        studentTable.getColumnModel().getColumn(2).setPreferredWidth(120);
        studentTable.getColumnModel().getColumn(3).setPreferredWidth(100);

        // Custom cell renderer for highlighting
        studentTable.setDefaultRenderer(Object.class, new AttendanceTableCellRenderer());

        JScrollPane scrollPane = new JScrollPane(studentTable);
        add(scrollPane, BorderLayout.CENTER);

        // Create control panel
        JPanel controlPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);

        // Progress label
        progressLabel = new JLabel();
        progressLabel.setFont(new Font("Arial", Font.BOLD, 14));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 3;
        controlPanel.add(progressLabel, gbc);

        // Present button
        presentButton = new JButton("Present");
        presentButton.setFont(new Font("Arial", Font.BOLD, 14));
        presentButton.setBackground(Color.GREEN);
        presentButton.setForeground(Color.WHITE);
        presentButton.setPreferredSize(new Dimension(120, 40));
        presentButton.addActionListener(new PresentButtonListener());
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        controlPanel.add(presentButton, gbc);

        // Absent button
        absentButton = new JButton("Absent");
        absentButton.setFont(new Font("Arial", Font.BOLD, 14));
        absentButton.setBackground(Color.RED);
        absentButton.setForeground(Color.WHITE);
        absentButton.setPreferredSize(new Dimension(120, 40));
        absentButton.addActionListener(new AbsentButtonListener());
        gbc.gridx = 1;
        gbc.gridy = 1;
        controlPanel.add(absentButton, gbc);

        // Finish button
        finishButton = new JButton("Finish");
        finishButton.setFont(new Font("Arial", Font.BOLD, 14));
        finishButton.setBackground(Color.BLUE);
        finishButton.setForeground(Color.WHITE);
        finishButton.setPreferredSize(new Dimension(120, 40));
        finishButton.addActionListener(new FinishButtonListener());
        gbc.gridx = 2;
        gbc.gridy = 1;
        controlPanel.add(finishButton, gbc);

        add(controlPanel, BorderLayout.SOUTH);

        // Set window properties
        setSize(700, 500);
        setLocationRelativeTo(null);
    }

    private void updateDisplay() {
        if (currentStudentIndex < students.size()) {
            // Highlight current student
            studentTable.setRowSelectionInterval(currentStudentIndex, currentStudentIndex);
            studentTable.scrollRectToVisible(studentTable.getCellRect(currentStudentIndex, 0, true));
            
            // Update progress
            progressLabel.setText("Student " + (currentStudentIndex + 1) + " of " + students.size() + 
                                " - " + students.get(currentStudentIndex).getStudentName());
        } else {
            progressLabel.setText("Attendance completed for all students");
            presentButton.setEnabled(false);
            absentButton.setEnabled(false);
        }
        
        // Repaint table to update colors
        studentTable.repaint();
    }

    private class PresentButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (currentStudentIndex < students.size()) {
                // Remove from absent set if previously marked absent
                absentStudents.remove(currentStudentIndex);
                
                // Update table
                tableModel.setValueAt("Present", currentStudentIndex, 3);
                
                // Move to next student
                currentStudentIndex++;
                updateDisplay();
            }
        }
    }

    private class AbsentButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (currentStudentIndex < students.size()) {
                // Add to absent set
                absentStudents.add(currentStudentIndex);
                
                // Update table
                tableModel.setValueAt("Absent", currentStudentIndex, 3);
                
                // Move to next student
                currentStudentIndex++;
                updateDisplay();
            }
        }
    }

    private class FinishButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            // Save attendance to database
            for (Integer index : absentStudents) {
                Student student = students.get(index);
                absenceDAO.markAbsent(student.getId());
            }
            
            // Show completion message
            JOptionPane.showMessageDialog(AttendanceScreen.this,
                "Attendance saved successfully!\n" +
                "Present: " + (students.size() - absentStudents.size()) + "\n" +
                "Absent: " + absentStudents.size(),
                "Attendance Complete",
                JOptionPane.INFORMATION_MESSAGE);
            
            dispose();
        }
    }

    private class AttendanceTableCellRenderer extends DefaultTableCellRenderer {
        @Override
        public Component getTableCellRendererComponent(JTable table, Object value,
                boolean isSelected, boolean hasFocus, int row, int column) {
            
            Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
            
            if (row == currentStudentIndex) {
                // Highlight current student
                c.setBackground(Color.YELLOW);
                c.setForeground(Color.BLACK);
            } else if (absentStudents.contains(row)) {
                // Highlight absent students
                c.setBackground(Color.PINK);
                c.setForeground(Color.BLACK);
            } else if (row < currentStudentIndex) {
                // Present students (already processed)
                c.setBackground(Color.LIGHT_GRAY);
                c.setForeground(Color.BLACK);
            } else {
                // Default
                c.setBackground(Color.WHITE);
                c.setForeground(Color.BLACK);
            }
            
            return c;
        }
    }
}
