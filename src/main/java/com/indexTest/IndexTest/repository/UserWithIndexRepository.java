package com.indexTest.IndexTest.repository;

import com.indexTest.IndexTest.Entity.UserWithIndex;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserWithIndexRepository extends JpaRepository<UserWithIndex, Long> {
    UserWithIndex findByEmail(String email);
}
