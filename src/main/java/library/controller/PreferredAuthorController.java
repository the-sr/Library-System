package library.controller;

import library.dto.AuthorDto;
import library.services.PreferredAuthorService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class PreferredAuthorController {

    private final PreferredAuthorService preferredAuthorService;

    @PreAuthorize("hasAuthority('MEMBER')")
    @PostMapping("/add-preferred-author")
    public ResponseEntity<?> addPreferredAuthor(@RequestBody List<AuthorDto> req) {
        return ResponseEntity.ok(preferredAuthorService.addPreferredAuthor(req));
    }

    @GetMapping("/preferred-authors")
    public ResponseEntity<?> getPreferredAuthors(@RequestParam long userId) {
        return ResponseEntity.ok(preferredAuthorService.getPreferredAuthors(userId));
    }

    @PreAuthorize("hasAuthority('MEMBER')")
    @GetMapping("/remove-preferred-authors")
    public ResponseEntity<?> removePreferredAuthors(@RequestParam long authorId) {
        return ResponseEntity.ok().body(preferredAuthorService.removePreferredAuthor(authorId));
    }

}
