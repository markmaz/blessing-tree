package com.blessingtree.repository;

import com.blessingtree.model.Gift;
import org.springframework.data.domain.Example;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface GiftRepository extends JpaRepository<Gift, Long> {
    @Override
    <S extends Gift> S save(S entity);

    @Override
    void deleteById(Long id);

    @Override
    <S extends Gift> List<S> findAll(Example<S> example);

    @Override
    Optional<Gift> findById(Long id);
}
