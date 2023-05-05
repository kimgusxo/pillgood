package com.kit.pillgood.repository;

import com.kit.pillgood.domain.Prescription;
import com.kit.pillgood.persistence.dto.PrescriptionAndDiseaseNameDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface PrescriptionRepository extends JpaRepository<Prescription, Long> {
    @Query("select p, p.disease.diseaseName from Prescription p where p.groupMember.groupMemberIndex = :groupMemberIndex")
    List<PrescriptionAndDiseaseNameDTO> findPrescriptionAndDiseaseNameByGroupMemberIndex(@Param("groupMemberIndex") Long groupMemberIndex);
    List<Long> findPrescriptionIndexByGroupMemberBetween(Long groupMember, LocalDate dateStart, LocalDate dateEnd);
}
