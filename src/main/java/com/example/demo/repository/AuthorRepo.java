package com.example.demo.repository;

import com.example.demo.models.Author;
import com.example.demo.projection.AuthorProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface AuthorRepo extends JpaRepository<Author, Long> {

    @Query(value = "Select coalesce(max(id),0)+1 from author ",nativeQuery = true)
    Long findNextId();

    Optional<Author> findByEmail(String email);

    boolean existsByEmail(String email);
}
