package com.example.demo.repository;

import com.example.demo.models.Address;
import com.example.demo.payloads.res.AddressRes;
import com.example.demo.projection.AddressProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AddressRepo extends JpaRepository<Address, Long> {

    @Query(value = "select coalesce(max(id),0)+1 from address", nativeQuery = true)
    Long findNextId();

    Optional<AddressProjection> findById(long id);

    List<AddressProjection> findByUserId(long userId);
}
