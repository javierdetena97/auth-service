package com.example.authservice.repositories;

import com.example.authservice.commons.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
