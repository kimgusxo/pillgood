package com.kit.pillgood.repository;

import com.kit.pillgood.domain.TakePill;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TakePillRepository  extends JpaRepository<TakePill, Long> {
    List<TakePill> findTakePillByPrescriptionIndex(Long prescriptionIndex, String takePillTime);
}
