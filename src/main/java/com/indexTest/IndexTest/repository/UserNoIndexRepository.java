package com.indexTest.IndexTest.repository;
import com.indexTest.IndexTest.Entity.UserNoIndex;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserNoIndexRepository extends JpaRepository<UserNoIndex, Long> {
    UserNoIndex findByEmail(String email);
}
