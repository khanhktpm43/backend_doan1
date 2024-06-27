package com.example.DOAN1.Controller;

import com.example.DOAN1.Entities.Department;
import com.example.DOAN1.Repositories.DepartmentRepository;
import com.example.DOAN1.RequestResponse.ResponseObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("api/departments")
@CrossOrigin(origins = "*")
public class DepartmentController {
    @Autowired
    DepartmentRepository repository;

    @GetMapping("/")
    public ResponseEntity<ResponseObject> getAll(){
        List<Department> list = repository.findAll();
        return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject("ok","", list));
    }
    @GetMapping("/{id}")
    public ResponseEntity<ResponseObject> getById(@PathVariable Long id){
        Optional<Department> department  = repository.findById(id);
        if(department.isPresent())
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject("ok","", department.get()));
        else
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResponseObject("fail","id not exist", null));
    }
    @PostMapping("/")
    public  ResponseEntity<ResponseObject> create(@RequestBody Department newDepartment){
        repository.save(newDepartment);
        return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject("ok","", newDepartment));
    }
    @PutMapping("/{id}")

    public  ResponseEntity<ResponseObject> update(@PathVariable Long id,@RequestBody Department newDepartment){
        if(repository.existsById(id)) {
            Optional<Department> department = repository.findById(id);
            newDepartment.setId(id);
            newDepartment.setFacultyList(department.get().getFacultyList());
            repository.save(newDepartment);
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject("ok", "", newDepartment));
        }else
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResponseObject("fail", "id not exist", null));
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseObject> delete(@PathVariable Long id){
        if(repository.existsById(id)){
            repository.deleteById(id);
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject("ok", "", null));
        }else
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResponseObject("fail", "id not exist", null));
    }
}
