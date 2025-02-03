package library.models;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "book",indexes = {
        @Index(name = "indexed_book_isbn", columnList = "isbn", unique = true),
})
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "isbn")
    private String isbn;

    @Column(name = "title")
    private String title;

    @Column(name = "edition")
    private String edition;

    @Column(name = "publisher")
    private String publisher;

    @Column(name = "book_count")
    private int bookCount;

    @OneToMany(mappedBy = "book")
    private List<BookAuthor> bookAuthor;



}