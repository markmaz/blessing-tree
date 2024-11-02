package com.blessingtree.repository;

import com.blessingtree.model.User;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findUserByUsername(String username);

    Optional<User> findUserByUserId(Long id);

    @Override
    List<User> findAll(Sort sort);

    @Override
    <S extends User> S save(S entity);

    @Override
    void delete(User entity);
}
