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

    @Query("select gm.groupMemberIndex as groupMemberIndex, gm.groupMemberName as groupMemberName, " +
            "p.pillIndex as pillIndex, p.pillName as pillName, " +
            "d.diseaseIndex as diseaseIndex, d.diseaseName AS diseaseName, " +
            "c.takePillCheckIndex as takePillCheckIndex, c.takeCheck as takeCheck, c.takePillTime as takePillTime " +
            "from GroupMember gm join gm.prescriptions pc join pc.takePills t join t.pill p join pc.disease d left join t.takePillCheck c " +
            "where gm.groupMemberIndex = :groupMemberIndex and pc.prescriptionDate = :targetDate")
    MedicationInfoSummary findMedicationInfoByGroupMemberIndexAndTargetDate(@Param("groupMemberIndex") Long groupMemberIndex,
                                                                            @Param("targetDate") LocalDate targetDate);
}
