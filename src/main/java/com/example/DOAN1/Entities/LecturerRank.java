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
@Table(name="lecturer_rank")
public class LecturerRank {
    @Id
 //   @GeneratedValue(strategy = GenerationType.AUTO)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "lecturer_rank_seq")
    @SequenceGenerator(name = "lecturer_rank_seq", sequenceName = "LECTURER_RANK_SEQ", allocationSize = 1)
    private Long id;
    private String name;
    private int standardTime;
    @JsonManagedReference("lecturer_rank")
    @OneToMany(mappedBy = "lecturerRank", fetch = FetchType.LAZY)
    private List<Lecturer> lecturerList;
}
