package com.example.demo.repository;

import com.example.demo.models.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface BookRepo extends JpaRepository<Book, Long> {

    @Query(value = "Select coalesce(max(id),0)+1 from book ",nativeQuery = true)
    long findNextId();

    List<Book> findByAuthor(String author);

}
