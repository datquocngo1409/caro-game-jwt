package com.example.carogamejwt.repository;

import com.example.carogamejwt.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
