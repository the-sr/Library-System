package library.repository;

import library.models.Reviews;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReviewsRepo extends JpaRepository<Reviews, Long> {

    @Query(value = " select avg(r.rating) " +
            " from reveiws r" +
            " where r.book_id = :bookId ", nativeQuery = true)
    Float getAverageRating(long bookId);

    List<Reviews> findAllByBookId(long bookId);

    List<Reviews> findAllByUserId(long userId);

    List<Reviews> findAllByUserIdAndBookId(long userId, long bookId);

}
