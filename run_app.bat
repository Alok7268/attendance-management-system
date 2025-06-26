@echo off
echo Starting Student Attendance Management System...
cd /d "%~dp0"
echo CLASSPATH: target;lib\*
java -cp "target;lib\*" com.studentattendance.ui.StudentAttendanceApp > output.log 2>&1
type output.log
pause
