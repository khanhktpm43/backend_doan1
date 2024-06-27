package com.example.DOAN1.Controller;

import com.example.DOAN1.Entities.Lecturer;
import com.example.DOAN1.Repositories.LecturerRepository;
import com.example.DOAN1.RequestResponse.ResponseObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("api/lecturer")
@CrossOrigin(origins = "*")
public class LecturerController {
    @Autowired
    LecturerRepository repository;
    @GetMapping("/")
    public ResponseEntity<ResponseObject> getAll(){
        List<Lecturer> list = repository.findAll();
        return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject("ok","",list));
    }
    @GetMapping("/{id}")
    public ResponseEntity<ResponseObject> getById(@PathVariable Long id){
        if(repository.existsById(id)){
            Optional<Lecturer> lecturer = repository.findById(id);
            Lecturer lecturer1 = lecturer.get();
            String idFaculty = String.valueOf(lecturer1.getFaculty().getId());

            return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject("ok",idFaculty,lecturer1));
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResponseObject("fail","id not exist",null));
    }

    @PostMapping("/")
    public ResponseEntity<ResponseObject> create(@RequestBody Lecturer lecturer){
        if(lecturer != null){
            repository.save(lecturer);
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject("ok","",lecturer));
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseObject("fail","new object null",null));

    }
    @PutMapping("/{id}")
    public  ResponseEntity<ResponseObject> update(@PathVariable Long id, @RequestBody Lecturer lecturer){
        if(repository.existsById(id) && lecturer != null){
            Optional<Lecturer> lecturer1 = repository.findById(id);

            lecturer.setId(id);
            lecturer.setUser(lecturer1.get().getUser());
            lecturer.setAssignmentList(lecturer1.get().getAssignmentList());
            repository.save(lecturer);
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject("ok","",lecturer));
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResponseObject("fail","id not exist",null));

    }
    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseObject> delete(@PathVariable Long id){
        if(repository.existsById(id)){
            repository.deleteById(id);
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject("ok","",null));

        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResponseObject("fail","id not exist", null));

    }

}
