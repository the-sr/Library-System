package com.example.demo.dto.req;

import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GenreReq {
    @NotNull(message = "Genre name is required")
    private String name;
}
