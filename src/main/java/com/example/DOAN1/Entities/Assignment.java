package com.example.DOAN1.Entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.sun.tools.javac.jvm.Gen;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Objects;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "assignment")
public class Assignment {
    @Id
  //  @GeneratedValue(strategy = GenerationType.AUTO)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "assignment_seq")
    @SequenceGenerator(name = "assignment_seq", sequenceName = "ASSIGNMENT_SEQ", allocationSize = 1)
    protected Long id;
    protected Integer tcSP;
    protected Integer tcTHCS;
    protected Double standardTime;
        protected Double exactTime;
    @JsonBackReference("semester")
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "semesters_id")
    protected Semester semester;
    @JsonBackReference("schoolYear")
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "school_years_id")
    protected SchoolYear schoolYear;

    @JsonBackReference("lecturer")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "lecturer_id")
    protected Lecturer lecturer;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Assignment that = (Assignment) o;
        return Objects.equals(semester, that.semester) && Objects.equals(schoolYear, that.schoolYear) && Objects.equals(lecturer, that.lecturer);
    }

    @Override
    public int hashCode() {
        return Objects.hash(semester, schoolYear, lecturer);
    }
}
