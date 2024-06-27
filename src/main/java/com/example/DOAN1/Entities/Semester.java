package com.example.DOAN1.Entities;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "semester")
public class Semester {
    @Id
 //   @GeneratedValue(strategy = GenerationType.AUTO)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "semester_seq")
    @SequenceGenerator(name = "semester_seq", sequenceName = "SEMESTER_SEQ", allocationSize = 1)
    private Long id;
    private String name;
    @JsonManagedReference("semester")
    @OneToMany(mappedBy = "semester", fetch = FetchType.LAZY)
    private List<Assignment> assignmentList;
}
