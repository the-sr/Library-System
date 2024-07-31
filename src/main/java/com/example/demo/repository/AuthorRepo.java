package com.example.demo.repository;

import com.example.demo.models.Author;
import com.example.demo.projection.AuthorProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface AuthorRepo extends JpaRepository<Author, Long> {

    @Query(value = "Select coalesce(max(id),0)+1 from authoor ",nativeQuery = true)
    Long findNextId();

    Author findByEmail(String email);

    boolean existsByEmail(String email);
}
