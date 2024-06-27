package com.example.DOAN1.Repositories;

import com.example.DOAN1.Entities.Department;
import com.example.DOAN1.Entities.Faculty;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FacultyRepository extends JpaRepository<Faculty,Long> {
    public List<Faculty> findByDepartment(Department department);
}
