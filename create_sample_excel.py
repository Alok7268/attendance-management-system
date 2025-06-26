#!/usr/bin/env python3
"""
Script to create sample Excel file for Student Attendance App testing
"""

try:
    from openpyxl import Workbook
    import os
    
    # Create a new workbook
    wb = Workbook()
    ws = wb.active
    ws.title = "Students"
    
    # Add headers
    headers = ["Student Name", "Roll Number", "Course Name"]
    ws.append(headers)
    
    # Sample student data
    students_data = [
        ["John Smith", "CS2021001", "Computer Science"],
        ["Emily Johnson", "CS2021002", "Computer Science"],
        ["Michael Brown", "CS2021003", "Computer Science"],
        ["Sarah Davis", "CS2021004", "Computer Science"],
        ["David Wilson", "CS2021005", "Computer Science"],
        ["Jessica Garcia", "IT2021001", "Information Technology"],
        ["Christopher Martinez", "IT2021002", "Information Technology"],
        ["Amanda Rodriguez", "IT2021003", "Information Technology"],
        ["Matthew Anderson", "IT2021004", "Information Technology"],
        ["Ashley Taylor", "IT2021005", "Information Technology"],
        ["Joshua Thomas", "SE2021001", "Software Engineering"],
        ["Samantha Hernandez", "SE2021002", "Software Engineering"],
        ["Andrew Moore", "SE2021003", "Software Engineering"],
        ["Brittany Martin", "SE2021004", "Software Engineering"],
        ["Daniel Jackson", "SE2021005", "Software Engineering"],
        ["Lauren Thompson", "CS2022001", "Computer Science"],
        ["Ryan White", "CS2022002", "Computer Science"],
        ["Megan Lopez", "CS2022003", "Computer Science"],
        ["Kevin Lee", "IT2022001", "Information Technology"],
        ["Nicole Gonzalez", "IT2022002", "Information Technology"],
        ["Brandon Harris", "SE2022001", "Software Engineering"],
        ["Rachel Clark", "SE2022002", "Software Engineering"],
        ["Tyler Lewis", "CS2023001", "Computer Science"],
        ["Stephanie Robinson", "CS2023002", "Computer Science"],
        ["Justin Walker", "IT2023001", "Information Technology"],
        ["Michelle Perez", "SE2023001", "Software Engineering"],
        ["Aaron Hall", "CS2023003", "Computer Science"],
        ["Kimberly Allen", "IT2023002", "Information Technology"],
        ["Jordan Young", "SE2023002", "Software Engineering"],
        ["Rebecca King", "CS2023004", "Computer Science"]
    ]
    
    # Add student data
    for student in students_data:
        ws.append(student)
    
    # Auto-adjust column widths
    for column_cells in ws.columns:
        length = max(len(str(cell.value)) for cell in column_cells)
        ws.column_dimensions[column_cells[0].column_letter].width = length + 2
    
    # Make headers bold
    for cell in ws[1]:
        cell.font = cell.font.copy(bold=True)
    
    # Save the workbook
    filename = "sample_students.xlsx"
    wb.save(filename)
    print(f"✓ Successfully created Excel file: {filename}")
    print(f"✓ File contains {len(students_data)} student records")
    print(f"✓ File location: {os.path.abspath(filename)}")
    print("\nData structure:")
    print("- Column A: Student Name")
    print("- Column B: Roll Number") 
    print("- Column C: Course Name")
    print("\nThis file is ready to be imported into your Student Attendance App!")
    
except ImportError:
    print("❌ openpyxl library not found.")
    print("You can install it with: pip install openpyxl")
    print("Or use the CSV file that was already created: sample_students.csv")
    
except Exception as e:
    print(f"❌ Error creating Excel file: {e}")
    print("You can use the CSV file that was already created: sample_students.csv")
