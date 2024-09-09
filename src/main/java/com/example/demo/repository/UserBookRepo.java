package com.example.demo.repository;

import com.example.demo.models.UserBook;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserBookRepo extends JpaRepository<UserBook, Long> {

    @Query(value = "Select coalesce(max(id),0)+1 from user_book ", nativeQuery = true)
    long findNextId();

    @Query(value = "Select ub.* from user_book ub where ub.isActive=true",nativeQuery = true)
    List<UserBook> getAllUserBooks();

    @Query(value = "select ub.* from user_book ub where ub.user_id = ?1 AND ub.book_id = ?2;", nativeQuery = true)
    Optional<UserBook> findByUserIdAndBookId(long userId, long bookId);

    @Query(value = "select ub.* from user_book ub where ub.user_id = ?1;", nativeQuery = true)
    List<UserBook> findByUserId(long userId);

    @Query(value = "select ub.* from user_book ub where ub.book_id = ?1;", nativeQuery = true)
    List<UserBook> findByUserIdAndBookId(long bookId);
}
