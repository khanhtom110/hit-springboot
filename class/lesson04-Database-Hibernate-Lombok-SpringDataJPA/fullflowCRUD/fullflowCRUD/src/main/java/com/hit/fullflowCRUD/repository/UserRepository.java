package com.hit.fullflowCRUD.repository;

import com.hit.fullflowCRUD.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


/*

KHONG NEN SU DUNG JPA, PHAI THANH THAO SQL SYNTAX


*/


@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByEmail(String email);

    boolean existsByEmail(String email);

    List<User> findByNameContaining(String keyword);

    List<User> findByAgeGreaterThan(Integer age);
}