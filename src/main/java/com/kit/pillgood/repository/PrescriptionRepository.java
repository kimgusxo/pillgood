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
    @Query("select p, d.diseaseName from Prescription p join p.disease d join p.groupMember gm where gm.groupMemberIndex = :groupMemberIndex")
    List<PrescriptionAndDiseaseNameDTO> findPrescriptionAndDiseaseNameByGroupMemberIndex(@Param("groupMemberIndex") Long groupMemberIndex);
//    List<Long> findPrescriptionIndexByGroupMemberAndPrescriptionDateBetween(Long groupMemberIndex, LocalDate dateStart, LocalDate dateEnd);
}