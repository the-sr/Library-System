package library.controller;

import library.dto.ReviewsDto;
import library.services.ReviewsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class ReviewsController {

    private final ReviewsService reviewsService;

    @PostMapping("/add-review")
    public ResponseEntity<?> addReview(@RequestBody ReviewsDto req){
        return ResponseEntity.ok(reviewsService.addReview(req));
    }

    @GetMapping("/reviews-by-user")
    public ResponseEntity<?> getReviewsByUserId(@RequestParam long userId){
        return ResponseEntity.ok(reviewsService.getAllReviewsByUserId(userId));
    }

    @GetMapping("/review-by-book")
    public ResponseEntity<?> getReviewsByBookId(@RequestParam long bookId){
        return ResponseEntity.ok(reviewsService.getReviewsByBookId(bookId));
    }

    @PutMapping("/review")
    public ResponseEntity<?> updateReview(@RequestBody ReviewsDto req){
        return ResponseEntity.ok(reviewsService.updateReview(req));
    }

    @DeleteMapping("/review/{id}")
    public ResponseEntity<?> deleteReview(@PathVariable long id){
        return ResponseEntity.ok(reviewsService.deleteReview(id));
    }


}
