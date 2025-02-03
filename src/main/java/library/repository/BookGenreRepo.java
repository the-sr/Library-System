package library.repository;

import library.models.BookGenre;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookGenreRepo extends JpaRepository<BookGenre,Long> {
}
