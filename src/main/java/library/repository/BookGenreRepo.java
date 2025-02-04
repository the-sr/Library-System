package library.repository;

import library.models.BookGenre;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public interface BookGenreRepo extends JpaRepository<BookGenre,Long> {

    List<BookGenre> findAllByBookId(Long bookId);

    List<BookGenre> findAllByGenreId(Long genreId);

    void deleteAllByBookId(Long bookId);

    void deleteByBookIdAndGenreId(Long bookId, Long genreId);

    Set<Long> findAllIdsByGenreId(Long genreId);
}
