package com.blessingtree.repository;

import com.blessingtree.model.Parent;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ParentRepository extends JpaRepository<Parent, Long> {
    @Override
    List<Parent> findAll(Sort sort);

    @Override
    <S extends Parent> S save(S entity);

    Optional<Parent> findParentById(Long id);

    Optional<Parent> findParentByBtid(String bt_id);

    @Override
    void deleteById(Long aLong);

    @Override
    long count();

    @Query("""
        SELECT DISTINCT p
        FROM Parent p
        JOIN p.children c
        JOIN c.gifts g
        WHERE g.sponsor IS NULL ORDER BY p.lastName, p.firstName
        """)
    List<Parent> findParentsWithUnsponsoredGifts();
}
