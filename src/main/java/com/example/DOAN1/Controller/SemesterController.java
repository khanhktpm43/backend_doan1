package com.example.DOAN1.Controller;

import com.example.DOAN1.Entities.Semester;
import com.example.DOAN1.Repositories.SemesterRepository;
import com.example.DOAN1.RequestResponse.ResponseObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/semester")
@CrossOrigin(origins = "*")
public class SemesterController {
    @Autowired
    SemesterRepository repository;

    @GetMapping("/")
    public ResponseEntity<ResponseObject> getAll(){
        List<Semester> list = repository.findAll();
        return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject("ok","",list));

    }
    @GetMapping("/{id}")
    public ResponseEntity<ResponseObject> getById(@PathVariable Long id){
        if(repository.existsById(id)){
            Optional<Semester> semester = repository.findById(id);
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject("ok","",semester.get()));
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResponseObject("fail","id not exist",null));

    }
    @PostMapping("/")
    public ResponseEntity<ResponseObject> create(@RequestBody Semester semester){
      //  if(!repository.existsById(semester.getId()) || semester.getId()==null){
            repository.save(semester);
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject("ok","",semester));

//        }
//        return ResponseEntity.status(HttpStatus.FOUND).body(new ResponseObject("fail","id exist",null));

    }
    @PutMapping("/{id}")
    public ResponseEntity<ResponseObject> update(@PathVariable Long id, @RequestBody Semester semester){
        if(repository.existsById(id) && semester !=  null){
            Optional<Semester> semesterOptional= repository.findById(id);
            semester.setId(id);
            semester.setAssignmentList(semesterOptional.get().getAssignmentList());
            repository.save(semester);
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject("ok","",semester));
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResponseObject("fail","id not exist",null));
    }
     @DeleteMapping("/{id}")
    public  ResponseEntity<ResponseObject> delete(@PathVariable Long id){
        if(repository.existsById(id)){
            repository.deleteById(id);
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject("ok","",null));
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResponseObject("fail","id not exist",null));

     }

}
