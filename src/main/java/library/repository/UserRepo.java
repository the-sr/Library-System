package library.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import library.models.User;
import library.projection.UserProjection;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepo extends JpaRepository<User, Long> {

    @Query(value = "select coalesce(max(id),0)+1 from users ", nativeQuery = true)
    Long findNextId();

    @Query(value = "Select * from users u where u.email= ?1", nativeQuery = true)
    Optional<User> findByUsername(String userName);

    @Query(value = "select u.id,u.first_name,u.middle_name,u.last_name,u.email,u.phone,u.is_active from users u where u.email= ?1", nativeQuery = true)
    Optional<UserProjection> findByEmail(String email);

    @Query(value = "select u.id,u.first_name,u.middle_name,u.last_name,u.email,u.role,u.phone,u.is_active,u.borrowed_book_count from users u where u.is_active=true", nativeQuery = true)
    List<UserProjection> findAllByIsActive();

    @Query(value = "select u.id,u.first_name,u.middle_name,u.last_name,u.email,u.role,u.phone,u.is_active,u.borrowed_book_count from users u where u.is_active=false", nativeQuery = true)
    List<UserProjection> findAllByInactive();

    @Query(value = "select u.id,u.first_name,u.middle_name,u.last_name,u.email,u.role,u.phone,u.is_active,u.borrowed_book_count from users u where u.is_active=true and u.email= ?1", nativeQuery = true)
    List<UserProjection> findAllByIsActiveAndEmail(String email);

    @Query(value = "select u from users u ", nativeQuery = true)
    Page<UserProjection> findAllPagewise(Pageable pageable);
}