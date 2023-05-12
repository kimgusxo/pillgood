package com.kit.pillgood.service;

import com.kit.pillgood.domain.GroupMember;
import com.kit.pillgood.domain.TakePill;
import com.kit.pillgood.persistence.dto.MedicationInfoDTO;
import com.kit.pillgood.persistence.dto.TakePillAndTakePillCheckAndGroupMemberIndexDTO;
import com.kit.pillgood.persistence.dto.TakePillAndTakePillCheckDTO;
import com.kit.pillgood.persistence.dto.TakePillCheckAndGroupMemberIndexDTO;
import com.kit.pillgood.persistence.projection.PrescriptionIndexSummary;
import com.kit.pillgood.persistence.projection.TakePillAndTakePillCheckSummary;
import com.kit.pillgood.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;

import java.time.LocalDate;
import java.util.*;

@Service
public class TakePillService {
    private final GroupMemberRepository groupMemberRepository;
    private final TakePillRepository takePillRepository;
    private final PrescriptionRepository prescriptionRepository;

    @Autowired
    public TakePillService(GroupMemberRepository groupMemberRepository,
                           TakePillRepository takePillRepository,
                           PrescriptionRepository prescriptionRepository){
        this.groupMemberRepository = groupMemberRepository;
        this.takePillRepository = takePillRepository;
        this.prescriptionRepository = prescriptionRepository;
    }

//    public TakePill createTakePill(Long prescriptionIndex, Long pillIndex, Integer takeDay, Integer takeCount) {
//        // 복용해야 할 약 생성
//    }

//    public List<TakePill> createTakePillCheckList(TakePill takePill, LocalDate takeDateStart, Integer takePillTimeStart) {
//        // 복용해야 할 약 리스트 생성
//    }

    public List<TakePillAndTakePillCheckAndGroupMemberIndexDTO> searchTakePillCheckListByUserIndexBetweenTakeDate(Long userIndex, LocalDate dateStart, LocalDate dateEnd) {
        List<GroupMember> groupMembers = groupMemberRepository.findByUser_UserIndex(userIndex);
        List<TakePillAndTakePillCheckAndGroupMemberIndexDTO> takePillAndTakePillCheckAndGroupMemberIndexDTOList = new ArrayList<>();

        for(GroupMember groupMember : groupMembers) {
            List<PrescriptionIndexSummary> prescriptionIndexList = prescriptionRepository.findPrescriptionIndexByGroupMember_GroupMemberIndexAndPrescriptionDateBetween(groupMember.getGroupMemberIndex(), dateStart, dateEnd);
            for(PrescriptionIndexSummary prescriptionIndex : prescriptionIndexList) {
                List<TakePillAndTakePillCheckSummary> takePillAndTakePillCheckSummaries = takePillRepository.findTakePillAndCheckByPrescriptionIndex(prescriptionIndex.getPrescriptionIndex());
                List<TakePillAndTakePillCheckDTO> takePillAndTakePillCheckDTOs = new ArrayList<>();
                for(TakePillAndTakePillCheckSummary takePillAndTakePillCheckSummary : takePillAndTakePillCheckSummaries) {
                    TakePillAndTakePillCheckDTO takePillAndTakePillCheckDTO
                        = TakePillAndTakePillCheckDTO.builder()
                            .takePillIndex(takePillAndTakePillCheckSummary.getTakePillIndex())
                            .prescriptionIndex(takePillAndTakePillCheckSummary.getPrescriptionIndex())
                            .pillIndex(takePillAndTakePillCheckSummary.getPillIndex())
                            .takeDay(takePillAndTakePillCheckSummary.getTakeDay())
                            .takeCount(takePillAndTakePillCheckSummary.getTakeCount())
                            .takePillCheckIndex(takePillAndTakePillCheckSummary.getTakePillCheckIndex())
                            .takeDate(takePillAndTakePillCheckSummary.getTakeDate())
                            .takePillTime(takePillAndTakePillCheckSummary.getTakePillTime())
                            .takeCheck(takePillAndTakePillCheckSummary.getTakeCheck())
                            .build();
                    takePillAndTakePillCheckDTOs.add(takePillAndTakePillCheckDTO);
                }
                TakePillAndTakePillCheckAndGroupMemberIndexDTO takePillAndTakePillCheckAndGroupMemberIndexDTO =
                        TakePillAndTakePillCheckAndGroupMemberIndexDTO.builder()
                                .groupMemberIndex(groupMember.getGroupMemberIndex())
                                .takePillAndTakePillCheckDTOs(takePillAndTakePillCheckDTOs)
                                .build();
                takePillAndTakePillCheckAndGroupMemberIndexDTOList.add(takePillAndTakePillCheckAndGroupMemberIndexDTO);
            }
        }
        return takePillAndTakePillCheckAndGroupMemberIndexDTOList;
    }

    public MedicationInfoDTO searchMedicationInfoListByGroupMemberIndexListAndTargetDate(List<Long> groupMemberIndexList, LocalDate targetDate) {
        return takePillRepository.
    }

//    public List<TakePillCheckAndGroupMemberIndexDTO> updateTakePillCheck(Long takePillCheckIndex, TakePillCheckDTO takePillCheckDTO) {
//        // 복용 현황 갱신
//    }

}
