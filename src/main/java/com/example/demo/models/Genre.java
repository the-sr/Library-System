package com.example.demo.models;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Table(name="genre")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor

public class Genre {
    @Id
    private Long id;
    private String name;
    @ManyToMany(mappedBy = "genre")
    private List<Book> books;
}
