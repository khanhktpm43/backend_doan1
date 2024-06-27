package com.example.DOAN1.Service;

import com.example.DOAN1.Entities.*;
import com.example.DOAN1.Repositories.AssignmentRepository;
import com.example.DOAN1.Repositories.LecturerRepository;
import com.example.DOAN1.Repositories.SchoolYearRepository;
import com.example.DOAN1.Repositories.SemesterRepository;
import jdk.internal.org.jline.utils.Log;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UploadService {
    @Autowired
    AssignmentRepository assignmentRepository;
    @Autowired
    SemesterRepository semesterRepository;
    @Autowired
    LecturerRepository lecturerRepository;
    @Autowired
    SchoolYearRepository schoolYearRepository;

    public List<AssignmentPlus> uploadExcelFile(MultipartFile file, Semester semester, SchoolYear year) throws IOException {
        try (InputStream inputStream = file.getInputStream()) {
            Workbook workbook = new XSSFWorkbook(inputStream);
            Sheet sheet = workbook.getSheetAt(0);
            List<AssignmentPlus> list = new ArrayList<AssignmentPlus>();
            List<Assignment> listExistTime = assignmentRepository.findBySemesterAndSchoolYear(semester, year);
            for (Row row : sheet) {
                if (row.getRowNum() == 0) {
                    continue;
                }
                AssignmentPlus assignment = new AssignmentPlus();
                Assignment assignment1 = new Assignment();
                Optional<Lecturer> lecturer = lecturerRepository.findById((long) row.getCell(1).getNumericCellValue());

                assignment.setLecturer(lecturer.get());
                assignment.setLecturerId(lecturer.get().getId());
                assignment.setSemester(semester);
                assignment.setSchoolYear(year);
                assignment.setTcSP((int) row.getCell(2).getNumericCellValue());
                assignment.setTcTHCS((int) row.getCell(3).getNumericCellValue());
                assignment.setStandardTime(row.getCell(4).getNumericCellValue());
                if (row.getCell(5) != null) {
                    assignment.setExactTime(row.getCell(5).getNumericCellValue());
                } else {
                    assignment.setExactTime(null);
                }

                assignment1.setTcTHCS(assignment.getTcTHCS());
                assignment1.setTcSP(assignment.getTcSP());
                assignment1.setExactTime(assignment.getExactTime());
                assignment1.setSemester(assignment.getSemester());
                assignment1.setStandardTime(assignment.getStandardTime());
                assignment1.setSchoolYear(assignment.getSchoolYear());
                assignment1.setLecturer(assignment1.getLecturer());

                if(listExistTime.indexOf(assignment1) != -1){
                    assignment.setId(listExistTime.get(listExistTime.indexOf(assignment1)).getId());

                }
                list.add(assignment);


            }

            // assignmentRepository.saveAll(list);
            return list;
        }
    }
}
