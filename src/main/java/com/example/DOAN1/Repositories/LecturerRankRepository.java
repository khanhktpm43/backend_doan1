package com.example.DOAN1.Repositories;

import com.example.DOAN1.Entities.LecturerRank;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LecturerRankRepository extends JpaRepository<LecturerRank,Long> {
}
