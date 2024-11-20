package com.blessingtree.repository;

import com.blessingtree.model.CallLog;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CallLogRepository extends JpaRepository<CallLog, Long> {
    @Override
    <S extends CallLog> S save(S entity);

    @Override
    void deleteById(Long aLong);

    @Override
    List<CallLog> findAll(Sort sort);
}
