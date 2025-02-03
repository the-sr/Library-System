package library.repository;

import library.models.BookAuthor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookAuthorRepo extends JpaRepository<BookAuthor,Long> {

    List<BookAuthor> findAllByBookId(Long bookId);

    List<BookAuthor> findAllByAuthorId(Long authorId);

    void deleteAllByBookId(Long bookId);

    void deleteByBookIdAndAuthorId(Long bookId, Long authorId);

}
