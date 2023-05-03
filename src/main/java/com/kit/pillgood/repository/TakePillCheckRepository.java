package com.kit.pillgood.repository;

import com.kit.pillgood.domain.TakePillCheck;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface TakePillCheckRepository  extends JpaRepository<TakePillCheck, Long> {
    List<TakePillCheck> findTakePillCheckByPrescriptionIndexAndTakeDateBetween(Long PrescriptionIndex, LocalDate takeDateStart, LocalDate takeDateEnd);
    List<TakePillCheck> findTakePillCheckByPrescriptionIndexAndTakeDate(Long prescriptionIndex, LocalDate takeDate);
    List<TakePillCheck> findTakePillCheckByPrescriptionIndexAndTakeDateAndTakePillTime(Long prescriptionIndex, LocalDate takeDate, Integer takePillTIme);
}
