package com.blessingtree.repository;

import com.blessingtree.model.GiftAll;
import org.springframework.data.domain.Example;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GiftAllRepository extends JpaRepository<GiftAll, Long> {
    @Override
    void deleteById(Long id);

    @Override
    <S extends GiftAll> List<S> findAll(Example<S> example);

    @Override
    long count();
}
