package com.example.DOAN1.Repositories;

import com.example.DOAN1.Entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository  extends JpaRepository<User, Long> {
     User findByUserName(String username);
     boolean existsByUserName(String username);
}
