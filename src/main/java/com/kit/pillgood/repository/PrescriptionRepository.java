package com.kit.pillgood.repository;

import com.kit.pillgood.domain.Prescription;
import com.kit.pillgood.persistence.projection.PrescriptionAndDiseaseNameSummary;
import com.kit.pillgood.persistence.projection.PrescriptionIndexSummary;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface PrescriptionRepository extends JpaRepository<Prescription, Long> {
    @Query("select p.prescriptionIndex as prescriptionIndex, gm.groupMemberIndex as groupMemberIndex, d.diseaseIndex as diseaseIndex," +
            "p.prescriptionRegistrationDate as prescriptionRegistrationDate, p.prescriptionDate as prescriptionDate, p.hospitalPhone as hospitalPhone, p.hospitalName as hospitalName, d.diseaseName as diseaseName " +
            "from Prescription p left join p.disease d left join p.groupMember gm where gm.groupMemberIndex = :groupMemberIndex")
    List<PrescriptionAndDiseaseNameSummary> findPrescriptionAndDiseaseNameByGroupMemberIndex(@Param("groupMemberIndex") Long groupMemberIndex);
    List<PrescriptionIndexSummary> findPrescriptionIndexByGroupMember_GroupMemberIndexAndPrescriptionDateBetween(Long groupMemberIndex, LocalDate dateStart, LocalDate dateEnd);
}