package com.example.DOAN1.Controller;

import com.example.DOAN1.Entities.LecturerRank;
import com.example.DOAN1.Repositories.LecturerRankRepository;
import com.example.DOAN1.RequestResponse.ResponseObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("api/ranks")
@CrossOrigin(origins = "*")
public class LecturerRankController {
    @Autowired
    LecturerRankRepository repository;

    @GetMapping("/")
    public ResponseEntity<ResponseObject> getAll(){
        List<LecturerRank> list=repository.findAll();
        return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject("ok","",list));
    }
    @GetMapping("/{id}")
    public ResponseEntity<ResponseObject> getById(@PathVariable Long id){
        Optional<LecturerRank> rank = repository.findById(id);
        if(rank.isPresent()){
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject("ok","", rank.get()));

        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResponseObject("fail","id not exist",null));

    }
    @PostMapping("/")
    public ResponseEntity<ResponseObject> create(@RequestBody LecturerRank rank){
        repository.save(rank);
        return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject("ok","",rank));

    }
    @PutMapping("/{id}")
    public ResponseEntity<ResponseObject> update(@PathVariable Long id, @RequestBody LecturerRank rank){
        if(repository.existsById(id)){
            Optional<LecturerRank> lecturerRank = repository.findById(id);
            rank.setId(id);
            rank.setLecturerList(lecturerRank.get().getLecturerList());
            repository.save(rank);
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject("ok","",rank));
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResponseObject("fail","",null));
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseObject> delete(@PathVariable Long id){
        if(repository.existsById(id)) {
            repository.deleteById(id);
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject("ok", "", null));
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResponseObject("fail","id not exist",null));

    }
}
