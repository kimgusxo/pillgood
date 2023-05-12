package com.kit.pillgood.repository;

import com.kit.pillgood.domain.TakePill;
import com.kit.pillgood.persistence.dto.TakePillAndTakePillCheckDTO;
import com.kit.pillgood.persistence.projection.MedicationInfoSummary;
import com.kit.pillgood.persistence.projection.TakePillAndTakePillCheckSummary;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface TakePillRepository  extends JpaRepository<TakePill, Long> {
//    List<TakePill> findTakePillsByPrescriptionIndex(Long prescriptionIndex, String takePillTime);

    @Query("select tp.takePillIndex as takePillIndex, p.prescriptionIndex as prescriptionIndex, tp.pill.pillIndex as pillIndex, " +
            "tp.takeDay as takeDay, tp.takeCount as takeCount, tpc.takePillCheckIndex as takePillCheckIndex, " +
            "tpc.takeDate as takeDate, tpc.takePillTime as takePillTime, tpc.takeCheck as takeCheck " +
            "from TakePill tp join TakePillCheck tpc on tp.takePillIndex = tpc.takePill.takePillIndex " +
            "join tp.prescription p on p.prescriptionIndex = :prescriptionIndex")
    List<TakePillAndTakePillCheckSummary> findTakePillAndCheckByPrescriptionIndex(@Param("prescriptionIndex") Long prescriptionIndex);

    @Query("SELECT gm.groupMemberIndex AS groupMemberIndex, gm.groupMemberName AS groupMemberName, " +
            "p.pillIndex AS pillIndex, p.pillName AS pillName, " +
            "d.diseaseIndex AS diseaseIndex, d.diseaseName AS diseaseName, " +
            "c.takePillCheckIndex AS takePillCheckIndex, c.takeCheck as takeCheck, c.takePillTime as takePillTime " +
            "FROM GroupMember gm JOIN gm.prescriptions pc JOIN pc.takePills t JOIN t.pill p JOIN pc.disease d LEFT JOIN t.takePillCheck c " +
            "WHERE gm.groupMemberIndex = :groupMemberIndex AND pc.prescriptionDate = :targetDate")
    MedicationInfoSummary findMedicationInfoByGroupMemberIndexAndTargetDate(@Param("groupMemberIndex") Long groupMemberIndex,
                                                                            @Param("targetDate") LocalDate targetDate);
}
