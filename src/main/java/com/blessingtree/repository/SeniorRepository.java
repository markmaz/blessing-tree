package com.blessingtree.repository;

import com.blessingtree.model.Senior;
import com.blessingtree.model.Sponsor;
import org.springframework.data.domain.Example;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SeniorRepository extends JpaRepository<Senior, Long> {
    @Override
    List<Senior> findAll();
}
