package com.example.demo.models;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="isbn")
    private String isbn;

    @Column(name = "title")
    private String title;

    @Column(name="edition")
    private String edition;

    @Column(name="publisher")
    private String publisher;

    @Column(name="book_count")
    private int bookCount;

    @ManyToMany(mappedBy = "books")
    private List<Author> authors;

}
