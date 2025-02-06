package library.services.mappers;

import library.dto.ReviewsDto;
import library.models.Reviews;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ReviewsMapper extends MapperInterface<Reviews, ReviewsDto> {

    Reviews dtoToEntity(ReviewsDto reviewsDto);

    ReviewsDto entityToDto(Reviews reviews);

}
