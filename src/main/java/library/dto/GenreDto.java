package library.dto;

import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GenreDto {

    private long id;

    @NotNull(message = "Genre name is required")
    private String name;
}