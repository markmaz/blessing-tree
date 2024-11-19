package com.blessingtree.repository;

import com.blessingtree.model.Gift;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
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

    @Override
    long count();

    Page<Gift> findBySponsorIsNull(Pageable pageable);

    @Query("SELECT g from Gift g join g.child c join g.child.parent p GROUP BY g.child.parent.id order by g.child.parent.firstName")
    List<Gift> findGiftsGroupedByFamily();

}
