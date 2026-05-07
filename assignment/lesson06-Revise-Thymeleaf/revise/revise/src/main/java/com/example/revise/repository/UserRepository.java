package com.example.revise.repository;

import com.example.revise.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);

    Boolean existsByEmail(String email);

    Page<User> findAll(Pageable pageable);


    @Query("SELECT u FROM User u WHERE u.id>:cursor AND " +
            "(LOWER(u.name) LIKE LOWER(CONCAT('%', :search, '%')) OR " +
            "LOWER(u.email) LIKE LOWER(CONCAT('%', :search, '%')))" +
            "ORDER BY u.id ASC"
    )
    List<User> findNextPageWithSearch(
            @Param("cursor") String cursor,
            @Param("search") String search,
            Pageable limit);

    List<User> findFirstPageWithSearch(String search, Pageable limit);
}
