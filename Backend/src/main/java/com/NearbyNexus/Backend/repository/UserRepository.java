package com.NearbyNexus.Backend.repository;

import com.NearbyNexus.Backend.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByEmail(String email);
}
