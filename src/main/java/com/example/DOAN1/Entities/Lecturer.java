package com.example.DOAN1.Entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name="lecturers")
public class Lecturer {
    @Id
 //   @GeneratedValue(strategy = GenerationType.AUTO)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "lecturers_seq")
    @SequenceGenerator(name = "lecturers_seq", sequenceName = "LECTURERS_SEQ", allocationSize = 1)
    private Long id;
    private String name;
    private String mail;
    @JsonBackReference("faculty")
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "faculty_id")
    private Faculty faculty;
    @JsonBackReference("lecturer_rank")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "lecturer_ranks_id")
    private LecturerRank lecturerRank;
//    @JsonManagedReference("lecturer")
//    @OneToOne(mappedBy = "lecturer",fetch = FetchType.LAZY)
    @JsonBackReference("user")
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

   @JsonManagedReference("lecturer")
    @OneToMany(mappedBy = "lecturer", fetch = FetchType.LAZY)
    private List<Assignment> assignmentList;
}
