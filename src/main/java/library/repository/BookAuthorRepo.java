package library.repository;

import library.models.BookAuthor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookAuthorRepo extends JpaRepository<BookAuthor,Long> {

    List<BookAuthor> findByBookId(Long bookId);

}
