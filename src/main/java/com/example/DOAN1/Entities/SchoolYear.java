package com.example.DOAN1.Entities;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.Year;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "school_year")
public class SchoolYear {
    @Id
 //   @GeneratedValue(strategy = GenerationType.AUTO)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "school_year_seq")
    @SequenceGenerator(name = "school_year_seq", sequenceName = "SCHOOL_YEAR_SEQ", allocationSize = 1)
    private  Long id;

    private Integer fromYear;

    private Integer toYear;
    @JsonManagedReference("schoolYear")
    @OneToMany(mappedBy = "schoolYear", fetch = FetchType.LAZY)
    private List<Assignment> assignmentList;
}
