package library.controller;

import library.dto.GenreDto;
import library.services.PreferredGenreService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class PreferredGenreController {

    private final PreferredGenreService preferredGenreService;

    @PostMapping("/add-preferred-genre")
    public ResponseEntity<?> addPreferredGenre(@RequestBody List<GenreDto> req) {
        return ResponseEntity.ok(preferredGenreService.addPreferredGenre(req));
    }

}
