package com.blessingtree.repository;

import com.blessingtree.model.Parent;
import com.blessingtree.model.ParentAll;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ParentAllRepository extends JpaRepository<ParentAll, Long> {
    @Override
    @EntityGraph(attributePaths = {"notes"})
    List<ParentAll> findAll(Sort sort);

    @Override
    Optional<ParentAll> findById(Long aLong);

    @Override
    <S extends ParentAll> S save(S entity);

    @Override
    void deleteById(Long aLong);

    @Override
    long count();

}
