package library.services.mappers;

import library.dto.ReviewDto;
import library.models.Review;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ReviewMapper extends MapperInterface<Review, ReviewDto> {

    Review dtoToEntity(ReviewDto reviewDto);

    ReviewDto entityToDto(Review review);

}
