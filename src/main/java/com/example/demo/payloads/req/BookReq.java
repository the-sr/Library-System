package com.example.demo.payloads.req;

import com.example.demo.models.Genre;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.List;
import java.util.Set;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BookReq {
    @NotNull(message = "ISBN is required")
    private String isbn;
    @NotNull(message = "Book Title is required")
    private String title;
    private String edition;
    @NotNull(message = "Publisher Name is required")
    private String publisher;
    @Min(value = 1,message = "Minimum Number of book is ONE")
    private int bookCount;
    @NotNull(message = "Author Details is required")
    private Set<AuthorReq> authors;
    @NotNull(message = "At least 1 genre is required")
    private List<GenreReq> genre;
}
