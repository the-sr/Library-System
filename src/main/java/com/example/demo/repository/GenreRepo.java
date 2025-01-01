package com.example.demo.repository;

import com.example.demo.models.Genre;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface GenreRepo extends JpaRepository<Genre, Long> {

    @Query(value = "Select coalesce(max(id),0)+1 from genre", nativeQuery = true)
    Long findNextId();

    @Query(value = "select * from Genre g where g.name= ?1", nativeQuery = true)
    Optional<Genre> findByName(String name);

    @Query(value = "select * from Genre g where g.name= ?1", nativeQuery = true)
    List<Genre> findByBookId(Long bookId);

}
