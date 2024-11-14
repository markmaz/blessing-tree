package com.blessingtree.repository;

import com.blessingtree.model.Sponsor;
import org.springframework.data.domain.Example;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SponsorRepository extends JpaRepository<Sponsor, Long> {
    @Override
    <S extends Sponsor> List<S> findAll(Example<S> example);

    @Override
    <S extends Sponsor> S save(S entity);

    @Override
    Optional<Sponsor> findById(Long aLong);

    @Override
    void deleteById(Long aLong);

    @Override
    long count();
}
