package com.blessingtree.repository;

import com.blessingtree.model.Parent;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ParentRepository extends JpaRepository<Parent, Long> {
    @Override
    @EntityGraph(attributePaths = {"notes"})
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
        WHERE g.sponsor IS NULL ORDER BY p.btid
        """)
    List<Parent> findParentsWithUnsponsoredGifts();

    @Query("""
        SELECT p
        FROM Parent p JOIN p.children c 
        WHERE c.id NOT IN (SELECT g.child.id FROM Gift g WHERE g.sponsor IS NOT NULL)
    """)
    List<Parent> findUnsponsoredChildren();
}
