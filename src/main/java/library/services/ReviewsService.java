package library.services;

import library.dto.ReviewsDto;

import java.util.List;

public interface ReviewsService {

    ReviewsDto addReview(ReviewsDto req);

    ReviewsDto addReply(ReviewsDto req);

    List<ReviewsDto> getReviewsByBookId(long bookId);

    List<ReviewsDto> getAllReviewsByUserId(long userId);

    ReviewsDto updateReview(ReviewsDto req);

    String deleteReview(long reviewId);

}
