package library.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import library.models.Book;

import java.util.List;
import java.util.Optional;

@Repository
public interface BookRepo extends JpaRepository<Book, Long> {

    @Query(value = "Select coalesce(max(id),0)+1 from book ", nativeQuery = true)
    long findNextId();

    @Override
    Page<Book> findAll(Pageable pageable);

    @Query(value = "select * from Book b where b.title= ?1", nativeQuery = true)
    List<Book> findByTitle(String title);

    @Query(value = "select * from Book b where b.title= ?1", nativeQuery = true)
    List<Book> findByAuthor(String author);

    @Query(value = "select * from Book b where b.title= ?1", nativeQuery = true)
    Optional<Book> findByISBN(String isbn);

}