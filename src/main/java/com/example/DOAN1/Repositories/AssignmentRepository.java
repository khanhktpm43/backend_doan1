package com.example.DOAN1.Repositories;

import com.example.DOAN1.Entities.Assignment;
import com.example.DOAN1.Entities.Lecturer;
import com.example.DOAN1.Entities.SchoolYear;
import com.example.DOAN1.Entities.Semester;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AssignmentRepository extends JpaRepository<Assignment,Long> {
    @Query("SELECT a FROM Assignment a WHERE a.semester = :semesterValue AND a.schoolYear = :schoolYearValue")
    List<Assignment> findBySemesterAndSchoolYear(@Param("semesterValue") Semester semesterValue, @Param("schoolYearValue") SchoolYear schoolYearValue);
    //List<Assignment> findByLecturer(Lecturer lecturer);
    @Query("SELECT a FROM Assignment a WHERE a.lecturer = :lecturerValue ORDER BY a.schoolYear.fromYear, a.semester.id")
    List<Assignment> findByLecturer(@Param("lecturerValue") Lecturer lecturerValue);
    @Query("SELECT a FROM Assignment a WHERE a.lecturer = :lecturerValue AND a.schoolYear = :schoolYearValue ORDER BY  a.semester.id")
    List<Assignment> findByLecturerAAndSchoolYear(@Param("lecturerValue") Lecturer lecturerValue ,@Param("schoolYearValue") SchoolYear schoolYearValue);
}
