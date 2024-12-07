package com.portfolio.my_note.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.portfolio.my_note.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, Integer>{
    User findByUsername(String username);    
    
}
