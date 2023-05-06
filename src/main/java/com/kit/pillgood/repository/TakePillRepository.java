package com.kit.pillgood.repository;

import com.kit.pillgood.domain.TakePill;
import com.kit.pillgood.persistence.dto.TakePillAndTakePillCheckDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TakePillRepository  extends JpaRepository<TakePill, Long> {
//    List<TakePill> findTakePillsByPrescriptionIndex(Long prescriptionIndex, String takePillTime);

    @Query("SELECT tp, tpc FROM TakePill tp JOIN tp.takePillCheck tpc WHERE tp.prescription.prescriptionIndex = :prescriptionIndex")
    List<TakePillAndTakePillCheckDTO> findTakePillAndCheckByPrescriptionIndex(@Param("prescriptionIndex") Long prescriptionIndex);
}
