package com.example.DOAN1.Entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name="faculty")
public class Faculty {
    @Id
 //   @GeneratedValue(strategy = GenerationType.AUTO)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "faculty_seq")
    @SequenceGenerator(name = "faculty_seq", sequenceName = "FACULTY_SEQ", allocationSize = 1)
    private Long id;
    private String name;
    @JsonBackReference("department")
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "department_id")
    private Department department;
    @JsonManagedReference("faculty")
    @OneToMany(mappedBy = "faculty", fetch = FetchType.LAZY)
    private List<Lecturer> lecturerList;

}
