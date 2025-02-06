package library.controller;

import library.dto.GenreDto;
import library.services.PreferredGenreService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("/preferred-genre")
    public ResponseEntity<?> getPreferredGenres(@RequestParam long userId) {
        return ResponseEntity.ok(preferredGenreService.getPreferredGenres(userId));
    }

    @PreAuthorize("hasAuthority('MEMBER')")
    @GetMapping("/remove-preferred-genres")
    public ResponseEntity<?> removePreferredGenres(@RequestParam long genreId) {
        return ResponseEntity.ok().body(preferredGenreService.removePreferredGenre(genreId));
    }

}
