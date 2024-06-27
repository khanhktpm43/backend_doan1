package com.example.DOAN1.Controller;

import com.example.DOAN1.Entities.Department;
import com.example.DOAN1.Entities.Faculty;
import com.example.DOAN1.Repositories.DepartmentRepository;
import com.example.DOAN1.Repositories.FacultyRepository;
import com.example.DOAN1.RequestResponse.ResponseObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("api/faculty")
@CrossOrigin(origins = "*")
public class FacultyController {
    @Autowired
    FacultyRepository repository;
    @Autowired
    DepartmentRepository departmentRepository;

    @GetMapping("/")
    public ResponseEntity<ResponseObject> getAll(){
        List<Faculty> list = repository.findAll();
        return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject("ok","",list));
    }
    @GetMapping("/{id}")
    public ResponseEntity<ResponseObject> getById(@PathVariable Long id){
        Optional<Faculty> faculty = repository.findById(id);
        if(faculty.isPresent()){

                return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject("ok","",faculty.get()));


        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResponseObject("fail","id not exist",null));
    }
    @GetMapping("/department/{id}")
    @Transactional
    public ResponseEntity<ResponseObject> getByDepartment(@PathVariable Long id){
        Optional<Department> department = departmentRepository.findById(id);
        if(department.isPresent()){
            List<Faculty> list = repository.findByDepartment(department.get());
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject("ok","",list));
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResponseObject("fail","",null));
    }
    @PostMapping("/")
    public  ResponseEntity<ResponseObject> create(@RequestBody Faculty newFaculty){
        if( departmentRepository.existsById(newFaculty.getDepartment().getId())) {
            repository.save(newFaculty);
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject("ok", "", newFaculty));
        }
        return  ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResponseObject("fail", "", null));
    }
    @PutMapping("{id}")
    public ResponseEntity<ResponseObject> update(@PathVariable Long id, @RequestBody Faculty newFaculty){
        if(repository.existsById(id) && departmentRepository.existsById(newFaculty.getId())){
            newFaculty.setId(id);
            Optional<Faculty> faculty = repository.findById(id);
            newFaculty.setLecturerList(faculty.get().getLecturerList());
            repository.save(newFaculty);
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject("ok","",newFaculty));
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResponseObject("fail","id or departmentId not exist",null));
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseObject> delete(@PathVariable Long id){
        repository.deleteById(id);
        return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject("ok","",null));
    }
}
