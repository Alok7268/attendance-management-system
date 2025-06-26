# Student Attendance Management System

A Java desktop application for managing student attendance with Excel import functionality.

## Features

### ✅ Implemented Features:
1. **Student Data Management**
   - SQLite database for storing student information
   - Students table: id (Primary Key), student_name, roll_number, course_name
   - Sample data loader for demonstration

2. **Attendance Management**
   - Interactive attendance screen with student list
   - Present/Absent buttons to mark attendance
   - Row highlighting to show current student and status
   - Progress tracking through the student list
   - Attendance data stored in Absence table: date_time, student_id

3. **Absentees Report**
   - View today's absentees
   - Print functionality for reports
   - Real-time data from database

4. **User Interface**
   - Clean, intuitive Swing-based GUI
   - Color-coded attendance status
   - Progress indicators
   - Status messages

### 📝 Partially Implemented:
- **Excel Import**: Framework is in place but has dependency compatibility issues with current POI version

## How to Run

### Prerequisites:
- Java 11 or later installed
- Windows operating system (tested on Windows)

### Running the Application:

#### Option 1: Using Batch File (Recommended)
1. Double-click `run_app.bat`

#### Option 2: Using Command Line
1. Open PowerShell/Command Prompt
2. Navigate to the application directory
3. Run: `java -cp "target;lib\*" com.studentattendance.ui.StudentAttendanceApp`

## How to Use

1. **Start the Application**
   - Launch using one of the methods above
   - The main window will appear with several options

2. **Load Sample Data**
   - Click "Load Sample Data" to populate the database with 10 sample students
   - This is useful for testing the attendance functionality

3. **Take Attendance**
   - Click "Take Attendance" to open the attendance screen
   - You'll see a list of all students with Sl No, Name, Roll Number, and Status
   - The current student is highlighted in yellow
   - Use "Present" or "Absent" buttons to mark attendance
   - The system will automatically move to the next student
   - Click "Finish" when done to save attendance to database

4. **View Absentees**
   - Click "View Today's Absentees" to see who was marked absent today
   - Use "Print Report" to generate a formatted attendance report

5. **Import from Excel** (Currently has compatibility issues)
   - Click "Import Students from Excel"
   - Select an Excel file with columns: Student Name, Roll Number, Course Name
   - The system will clear existing data and import new students

## Database

The application creates a SQLite database (`student_attendance.db`) in the application directory with two tables:

### Students Table
- `id` (INTEGER PRIMARY KEY AUTOINCREMENT)
- `student_name` (TEXT NOT NULL)
- `roll_number` (TEXT NOT NULL UNIQUE)
- `course_name` (TEXT NOT NULL)

### Absence Table
- `id` (INTEGER PRIMARY KEY AUTOINCREMENT)
- `date_time` (DATETIME NOT NULL)
- `student_id` (INTEGER NOT NULL, Foreign Key)

## Project Structure

```
StudentAttendanceApp/
├── src/main/java/com/studentattendance/
│   ├── model/          # Data models (Student, Absence)
│   ├── dao/            # Database access objects
│   ├── utils/          # Utility classes (Database, ExcelImporter, SampleDataLoader)
│   └── ui/             # User interface classes
├── lib/                # JAR dependencies
├── target/             # Compiled classes
├── pom.xml            # Maven configuration (for reference)
├── run_app.bat        # Windows batch file to run the app
└── README.md          # This file
```

## Dependencies

- SQLite JDBC Driver (sqlite-jdbc-3.42.0.0.jar)
- Apache POI (for Excel functionality - partial compatibility)
- Apache Commons libraries (supporting dependencies)

## Troubleshooting

1. **Application won't start**: Ensure Java 11+ is installed and PATH is set correctly
2. **Database errors**: Check write permissions in the application directory
3. **Excel import issues**: Known compatibility issue with POI dependencies - use "Load Sample Data" instead

## Future Enhancements

- Fix Excel import dependency compatibility
- Add date range reports
- Export attendance to PDF
- User authentication
- Student photo management
- Backup and restore functionality

---

**Created by:** Student Attendance App Development Team  
**Version:** 1.0.0  
**Last Updated:** June 2025
