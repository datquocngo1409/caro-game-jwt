package com.example.carogamejwt.repository;

import com.example.carogamejwt.model.CustomUser;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomUserRepository extends CrudRepository<CustomUser, Long> {
    CustomUser findByUsername(String username);
}
