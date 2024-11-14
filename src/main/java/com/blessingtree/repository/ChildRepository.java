package com.blessingtree.repository;

import com.blessingtree.model.Child;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ChildRepository extends JpaRepository<Child, Long> {
    List<Child> findAllByParentId(Long id);

    @Override
    <S extends Child> S save(S entity);

    @Override
    void deleteById(Long aLong);

    @Override
    Child getReferenceById(Long aLong);

    @Override
    long count();
}
