package com.kit.pillgood.repository;

import com.kit.pillgood.domain.Prescription;
import com.kit.pillgood.persistence.projections.PrescriptionIndexAndDiseaseIndexSummary;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PrescriptionRepository extends JpaRepository<Prescription, Long> {
    List<PrescriptionIndexAndDiseaseIndexSummary> findProjectionsByGroupMemberIndex(Long groupMemberIndex);
}
