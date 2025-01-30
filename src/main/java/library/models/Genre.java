package library.models;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "genre")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor

public class Genre {
    @Id
    private Long id;
    private String name;
    @OneToMany(mappedBy = "genre")
    private List<BookGenre> bookGenre;
}
