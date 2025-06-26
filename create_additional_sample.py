#!/usr/bin/env python3
"""
Script to create additional sample Excel file with different course data
"""

from openpyxl import Workbook
import os

# Create a new workbook
wb = Workbook()
ws = wb.active
ws.title = "Students_Batch2"

# Add headers
headers = ["Student Name", "Roll Number", "Course Name"]
ws.append(headers)

# Additional sample student data with different courses
students_data = [
    ["Alex Thompson", "ME2021001", "Mechanical Engineering"],
    ["Maria Rodriguez", "ME2021002", "Mechanical Engineering"],
    ["James Wilson", "EE2021001", "Electrical Engineering"],
    ["Lisa Chen", "EE2021002", "Electrical Engineering"],
    ["Robert Johnson", "CE2021001", "Civil Engineering"],
    ["Emma Davis", "CE2021002", "Civil Engineering"],
    ["William Brown", "ME2022001", "Mechanical Engineering"],
    ["Sofia Garcia", "EE2022001", "Electrical Engineering"],
    ["Michael Miller", "CE2022001", "Civil Engineering"],
    ["Isabella Martinez", "BT2021001", "Biotechnology"],
    ["Benjamin Jones", "BT2021002", "Biotechnology"],
    ["Charlotte Wilson", "CH2021001", "Chemical Engineering"],
    ["Lucas Anderson", "CH2021002", "Chemical Engineering"],
    ["Amelia Taylor", "PH2021001", "Physics"],
    ["Henry Thomas", "PH2021002", "Physics"],
    ["Harper Jackson", "MT2021001", "Mathematics"],
    ["Alexander White", "MT2021002", "Mathematics"],
    ["Evelyn Harris", "BT2022001", "Biotechnology"],
    ["Sebastian Martin", "CH2022001", "Chemical Engineering"],
    ["Abigail Thompson", "PH2022001", "Physics"]
]

# Add student data
for student in students_data:
    ws.append(student)

# Auto-adjust column widths
for column_cells in ws.columns:
    length = max(len(str(cell.value)) for cell in column_cells)
    ws.column_dimensions[column_cells[0].column_letter].width = length + 2

# Make headers bold
from openpyxl.styles import Font
bold_font = Font(bold=True)
for cell in ws[1]:
    cell.font = bold_font

# Save the workbook
filename = "sample_students_batch2.xlsx"
wb.save(filename)
print(f"✓ Successfully created second Excel file: {filename}")
print(f"✓ File contains {len(students_data)} student records")
print(f"✓ File location: {os.path.abspath(filename)}")
print("\nThis file contains students from different engineering disciplines:")
print("- Mechanical Engineering")
print("- Electrical Engineering") 
print("- Civil Engineering")
print("- Biotechnology")
print("- Chemical Engineering")
print("- Physics")
print("- Mathematics")
