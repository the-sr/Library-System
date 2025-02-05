package library.repository;

import library.models.PreferredGenre;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface PreferredGenreRepo extends JpaRepository<PreferredGenre, Long> {

    Set<Long> findAllUserIdsByGenreId(Long genreId);
}
