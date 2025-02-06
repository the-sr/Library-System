package library.controller;

import library.dto.AuthorDto;
import library.services.PreferredAuthorService;
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
public class PreferredAuthorController {

    private final PreferredAuthorService preferredAuthorService;

    @PostMapping("/add-preferred-author")
    public ResponseEntity<?> addPreferredAuthor(@RequestBody List<AuthorDto> req) {
        return ResponseEntity.ok(preferredAuthorService.addPreferredAuthor(req));
    }

}
