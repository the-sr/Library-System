package library.models;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
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
    @ManyToMany(mappedBy = "genre")
    private List<Book> books;
}
