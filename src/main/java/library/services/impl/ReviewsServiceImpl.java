package library.services.impl;

import library.dto.ReviewsDto;
import library.exception.CustomException;
import library.models.Book;
import library.models.Reviews;
import library.repository.BookRepo;
import library.repository.ReviewsRepo;
import library.services.ReviewsService;
import library.services.mappers.ReviewsMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor

public class ReviewsServiceImpl implements ReviewsService {

    private final ReviewsMapper reviewsMapper;
    private final ReviewsRepo reviewsRepo;
    private final BookRepo bookRepo;

    @Override
    public ReviewsDto addReview(ReviewsDto req) {
        Reviews reviews = reviewsMapper.dtoToEntity(req);
        reviews.setCreatedDate(LocalDateTime.now());
        reviews=reviewsRepo.save(reviews);
        Float averageRatings= reviewsRepo.getAverageRating(req.getBookId());
        Book book=bookRepo.findById(req.getBookId()).orElseThrow(()->new CustomException("Book not found", HttpStatus.NOT_FOUND));
        book.setAverageRating(averageRatings);
        bookRepo.save(book);
        return reviewsMapper.entityToDto(reviews);
    }

    @Override
    public ReviewsDto addReply(ReviewsDto req) {
        return null;
    }

    @Override
    public List<ReviewsDto> getReviewsByBookId(long bookId) {
        List<Reviews> reviewsList=reviewsRepo.findAllByBookId(bookId);
        List<ReviewsDto> res=new ArrayList<>();
        reviewsList.forEach(reviews -> res.add(reviewsMapper.entityToDto(reviews)));
        return res;
//        return reviewsList.stream().map(reviewsMapper::entityToDto).collect(Collectors.toList());
    }

    @Override
    public List<ReviewsDto> getAllReviewsByUserId(long userId) {
        List<Reviews> reviewsList=reviewsRepo.findAllByUserId(userId);
        return reviewsList.stream().map(reviewsMapper::entityToDto).collect(Collectors.toList());
    }

    @Override
    public ReviewsDto updateReview(ReviewsDto req) {
        Reviews reviews=reviewsRepo.findById(req.getId()).orElseThrow(()->new CustomException("Review not found", HttpStatus.NOT_FOUND));
        reviews.setComment(req.getComment());
        reviews.setRating(req.getRating());
        reviews=reviewsRepo.save(reviews);
        Float averageRatings= reviewsRepo.getAverageRating(req.getBookId());
        Book book=bookRepo.findById(req.getBookId()).orElseThrow(()->new CustomException("Book not found", HttpStatus.NOT_FOUND));
        book.setAverageRating(averageRatings);
        bookRepo.save(book);
        return reviewsMapper.entityToDto(reviews);
    }

    @Override
    public String deleteReview(long reviewId) {
        reviewsRepo.deleteById(reviewId);
        return "Review deleted successfully";
    }
}
