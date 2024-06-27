package com.example.DOAN1.Controller;

import com.example.DOAN1.Entities.SchoolYear;
import com.example.DOAN1.Repositories.SchoolYearRepository;
import com.example.DOAN1.RequestResponse.ResponseObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/school-year")
@CrossOrigin(origins = "*")
public class SchoolYearController {
    @Autowired
    SchoolYearRepository repository;

    @GetMapping("/")
    public ResponseEntity<ResponseObject> getAll() {
        List<SchoolYear> list = repository.findAll();
        return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject("ok", "", list));

    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseObject> getById(@PathVariable Long id) {
        if (repository.existsById(id)) {
            Optional<SchoolYear> schoolYear = repository.findById(id);
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject("ok", "", schoolYear.get()));
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResponseObject("fail", "id not exist", null));

    }

    @PostMapping("/")
    public ResponseEntity<ResponseObject> create(@RequestBody SchoolYear schoolYear) {

            repository.save(schoolYear);
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject("ok", "", schoolYear));


    }

    @PutMapping("/{id}")
    public ResponseEntity<ResponseObject> update(@PathVariable Long id, @RequestBody SchoolYear schoolYear) {
        if (repository.existsById(id) && schoolYear != null) {
            Optional<SchoolYear> year = repository.findById(id);
            schoolYear.setId(id);
            schoolYear.setAssignmentList(year.get().getAssignmentList());
            repository.save(schoolYear);
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject("ok", "", schoolYear));
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResponseObject("fail", "id not exist", null));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseObject> delete(@PathVariable Long id) {
        if (repository.existsById(id)) {
            repository.deleteById(id);
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject("ok", "", null));
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResponseObject("fail", "id not exist", null));
    }
}
