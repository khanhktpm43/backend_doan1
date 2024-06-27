package com.example.DOAN1.Controller;

import com.example.DOAN1.Entities.*;
import com.example.DOAN1.Repositories.AssignmentRepository;
import com.example.DOAN1.Repositories.LecturerRepository;
import com.example.DOAN1.Repositories.SchoolYearRepository;
import com.example.DOAN1.Repositories.SemesterRepository;
import com.example.DOAN1.RequestResponse.RequestObject;
import com.example.DOAN1.RequestResponse.ResponseObject;
import com.example.DOAN1.Service.UploadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("api/assignment")
@CrossOrigin(origins = "*")
public class AssignmentController {
    @Autowired
    AssignmentRepository assignmentRepository;
    @Autowired
    SchoolYearRepository schoolYearRepository;
    @Autowired
    SemesterRepository semesterRepository;
    @Autowired
    LecturerRepository lecturerRepository;
    @Autowired
    UploadService uploadService;

    @GetMapping("/")
    public ResponseEntity<ResponseObject> getAll() {
        List<Assignment> list = assignmentRepository.findAll();
        List<AssignmentPlusPlus> list1 = new ArrayList<>();
        for (Assignment item: list){
            AssignmentPlusPlus item2 = new AssignmentPlusPlus();

            item2.setId(item.getId());
            item2.setName(item.getLecturer().getName());
            item2.setStandardTime(item.getStandardTime());
            item2.setExactTime(item.getExactTime());
            item2.setSemesterName(item.getSemester().getName());
            item2.setNameyear(item.getSchoolYear().getFromYear().toString()+"-"+item.getSchoolYear().getToYear().toString());
            list1.add(item2);
        }

        return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject("ok", "", list1));
    }
    @PostMapping("/get-data/")
    public ResponseEntity<ResponseObject> getByLecturerAndYear(@RequestBody RequestObject object) {

        List<Assignment> list = assignmentRepository.findByLecturerAAndSchoolYear(object.getLecturer(),object.getYear());
        List<AssignmentPlus> list1 = new ArrayList<>();
        for(Assignment item: list){
            AssignmentPlus item2 = new AssignmentPlus();
            item2.setTcTHCS(item.getTcTHCS());
            item2.setTcSP(item.getTcSP());
            item2.setId(item.getId());
            item2.setLecturer(item.getLecturer());
            item2.setStandardTime(item.getStandardTime());
            item2.setExactTime(item.getExactTime());
            item2.setSemesterName(item.getSemester().getName());
            list1.add(item2);
        }
        return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject("ok", "", list1));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseObject> getById(@PathVariable Long id) {
        Optional<Assignment> assignment = assignmentRepository.findById(id);
        if (assignment.isPresent())
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject("ok", "", assignment));
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResponseObject("fail", "id not exist", null));
    }
    @GetMapping("/lecturer/{id}")
    public ResponseEntity<ResponseObject> getByLecturer(@PathVariable Long id) {
        Optional<Lecturer> lecturer = lecturerRepository.findById(id);
        if(lecturer.isPresent()) {
            List<Assignment> list = assignmentRepository.findByLecturer(lecturer.get());
            List<AssignmentPlus> list1 = new ArrayList<>();
            for(Assignment item:list){
                AssignmentPlus item2 = new AssignmentPlus();
                item2.setId(item.getId());
                item2.setTcSP(item.getTcSP());
                item2.setTcTHCS(item.getTcTHCS());
                item2.setTime(item.getSchoolYear().getFromYear().toString()+"-"+item.getSchoolYear().getToYear().toString());
                item2.setExactTime(item.getExactTime());
                item2.setStandardTime(item.getStandardTime());
                list1.add(item2);
            }
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject("ok", "", list1));
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResponseObject("fail", "id lecturer not exist", null));
    }

    @PostMapping("/")
    public ResponseEntity<ResponseObject> uploadData(@RequestParam(value = "file") MultipartFile file, @RequestParam("semester") String semesterId, @RequestParam("year") String yearId) {
        System.out.print(semesterId);
        System.out.print(file);
        if (semesterRepository.existsById(Long.valueOf(semesterId)) && schoolYearRepository.existsById(Long.valueOf(yearId))) {
            Optional<Semester> semester = semesterRepository.findById(Long.valueOf(semesterId));
            Optional<SchoolYear> year = schoolYearRepository.findById(Long.valueOf(yearId));
            List<Assignment> listExistTime = assignmentRepository.findBySemesterAndSchoolYear(semester.get(), year.get());
            try {
                List<AssignmentPlus> list = uploadService.uploadExcelFile(file, semester.get(), year.get());
                List<Assignment> list1 = new ArrayList<>();
                for (AssignmentPlus dto1 : list) {
                    Assignment dto2 = new Assignment();
                    dto2.setId(dto1.getId());
                    dto2.setExactTime(dto1.getExactTime());
                    dto2.setStandardTime(dto1.getStandardTime());
                    dto2.setSemester(dto1.getSemester());
                    dto2.setSchoolYear(dto1.getSchoolYear());
                    dto2.setTcSP(dto1.getTcSP());
                    dto2.setTcTHCS(dto1.getTcTHCS());
                    dto2.setLecturer(dto1.getLecturer());
                    if(listExistTime.indexOf(dto2) !=-1){
                        dto2.setId(listExistTime.get(listExistTime.indexOf(dto2)).getId());
                    }
                    list1.add(dto2);
                }
                assignmentRepository.saveAll(list1);
                return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject("ok", "", list));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseObject("fail", "", null));
    }
    @PutMapping("/{id}")
    public ResponseEntity<ResponseObject> update(@PathVariable Long id, @RequestBody Assignment assignment){
        if(assignmentRepository.existsById(id)){
            assignment.setId(id);
            assignmentRepository.save(assignment);
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject("ok", "", assignment));
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResponseObject("fail", "id not exist", null));
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseObject> delete(@PathVariable long id){
        if(assignmentRepository.existsById(id)){
            assignmentRepository.deleteById(id);
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject("ok", "", null));
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResponseObject("fail", "id not exist", null));
    }

}
