package com.blessingtree.repository;

import com.blessingtree.model.FamilyNote;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FamilyNoteRepository extends JpaRepository<FamilyNote, Long> {
    @Override
    void deleteById(Long aLong);
}
