package library.repository;

import library.models.PreferredAuthor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface PreferredAuthorRepo extends JpaRepository<PreferredAuthor, Long> {

    @Query(value=" select pa.user_id " +
            " from preferred_author pa " +
            " where pa.author_id = :authorId ",nativeQuery = true)
    Set<Long> findAllUserIdsByAuthorId(Long authorId);
}
