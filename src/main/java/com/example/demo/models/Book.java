package com.example.demo.models;

import jakarta.persistence.*;
import lombok.*;

import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Book {
    @Id
    private Long id;

    @Column(name="isbn",unique = true,nullable = false)
    private String isbn;

    @Column(name = "title",nullable = false)
    private String title;

    @Column(name="edition")
    private String edition;

    @Column(name="publisher")
    private String publisher;

    @Column(name="book_count")
    private int bookCount;

    @ManyToMany(mappedBy = "books",cascade = CascadeType.ALL)
    private Set<Author> authors;

}
