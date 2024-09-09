package com.example.demo.repository;

import com.example.demo.models.Author;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;
import java.util.Set;

public interface AuthorRepo extends JpaRepository<Author, Long> {

    @Query(value = "Select coalesce(max(id),0)+1 from author ", nativeQuery = true)
    Long findNextId();

    @Query(value = "select * from Author a where a.email= ?1", nativeQuery = true)
    Optional<Author> findByEmail(String email);

    boolean existsByEmail(String email);

    @Query(value = "select * from Author a where a.email= ?1", nativeQuery = true)
    Set<Author> findByBookId(Long bookId);
}
