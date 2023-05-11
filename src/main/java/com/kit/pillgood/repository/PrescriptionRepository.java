package com.kit.pillgood.repository;

import com.kit.pillgood.domain.Prescription;
import com.kit.pillgood.persistence.projection.PrescriptionAndDiseaseNameSummary;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface PrescriptionRepository extends JpaRepository<Prescription, Long> {
    @Query("select p.prescriptionIndex, gm.groupMemberIndex, d.diseaseIndex," +
            "p.prescriptionRegistrationDate, p.prescriptionDate, p.hospitalPhone, p.hospitalName, d.diseaseName " +
            "from Prescription p join p.disease d join p.groupMember gm where gm.groupMemberIndex = :groupMemberIndex")
    List<PrescriptionAndDiseaseNameSummary> findPrescriptionAndDiseaseNameByGroupMemberIndex(@Param("groupMemberIndex") Long groupMemberIndex);
    List<Long> findPrescriptionIndexByGroupMember_GroupMemberIndexAndPrescriptionDateBetween(Long groupMemberIndex, LocalDate dateStart, LocalDate dateEnd);
}