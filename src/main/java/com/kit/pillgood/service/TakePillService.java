package com.kit.pillgood.service;

import com.kit.pillgood.domain.GroupMember;
import com.kit.pillgood.domain.Pill;
import com.kit.pillgood.domain.Prescription;
import com.kit.pillgood.domain.TakePill;
import com.kit.pillgood.exeptions.exeption.NonRegistrationUserException;
import com.kit.pillgood.persistence.dto.*;
import com.kit.pillgood.persistence.projection.MedicationInfoSummary;
import com.kit.pillgood.persistence.projection.PrescriptionIndexSummary;
import com.kit.pillgood.persistence.projection.TakePillAndTakePillCheckSummary;
import com.kit.pillgood.repository.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.*;

@Service
public class TakePillService {
    private final Logger LOGGER = LoggerFactory.getLogger(TakePillService.class);
    private final GroupMemberRepository groupMemberRepository;
    private final TakePillRepository takePillRepository;
    private final PrescriptionRepository prescriptionRepository;
    private final PillRepository pillRepository;

    @Autowired
    public TakePillService(GroupMemberRepository groupMemberRepository,
                           TakePillRepository takePillRepository,
                           PrescriptionRepository prescriptionRepository,
                           PillRepository pillRepository){
        this.groupMemberRepository = groupMemberRepository;
        this.takePillRepository = takePillRepository;
        this.prescriptionRepository = prescriptionRepository;
        this.pillRepository = pillRepository;
    }

    public List<Long> createTakePillByOCRData(Long prescriptionIndex, EditOcrDTO editOcrDTO) {

        List<TakePill> takePillList = new ArrayList<>();

        // 약 개수가 여러개니까 리스트로 저장해서 필 인덱스를 추출
        for(PillScheduleDTO list : editOcrDTO.getPillList()) {
            Pill pill = pillRepository.findByPillName(list.getPillName());

            // TakePill
            TakePill takePill = TakePill.builder()
                    .takePillIndex(null)
                    .prescription(Prescription.builder()
                            .prescriptionIndex(prescriptionIndex)
                            .build())
                    .pill(Pill.builder()
                            .pillIndex(pill.getPillIndex())
                            .build())
                    .takePillCheck(null)
                    .takeDay(list.getTakeDay())
                    .takeCount(list.getTakeCount())
                    .build();

            takePillList.add(takePill);
        }

        takePillList = takePillRepository.saveAll(takePillList);

        List<Long> takePillIndexList = new ArrayList<>();

        for(TakePill takePill : takePillList) {
            takePillIndexList.add(takePill.getTakePillIndex());
        }

        return takePillIndexList;
    }

//    public List<TakePill> createTakePillCheckList(TakePill takePill, LocalDate takeDateStart, Integer takePillTimeStart) {
//        // 복용해야 할 약 리스트 생성
//    }

    @Transactional
    public List<TakePillAndTakePillCheckAndGroupMemberIndexDTO> searchTakePillCheckListByUserIndexBetweenTakeDate(Long userIndex, LocalDate dateStart, LocalDate dateEnd) throws NonRegistrationUserException {

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

    @Transactional
    public List<MedicationInfoDTO> searchMedicationInfoListByGroupMemberIndexListAndTargetDate(List<Long> groupMemberIndexList, LocalDate targetDate) {
        List<MedicationInfoDTO> medicationInfoDTOs = new ArrayList<>();
        for(Long groupMemberIndex : groupMemberIndexList) {
           MedicationInfoSummary medicationInfoSummary = takePillRepository.findMedicationInfoByGroupMemberIndexAndTargetDate(groupMemberIndex, targetDate);
           MedicationInfoDTO medicationInfoDTO = MedicationInfoDTO.builder()
                   .groupMemberIndex(medicationInfoSummary.getGroupMemberIndex())
                   .groupMemberName(medicationInfoSummary.getGroupMemberName())
                   .pillIndex(medicationInfoSummary.getPillIndex())
                   .diseaseIndex(medicationInfoSummary.getDiseaseIndex())
                   .pillName(medicationInfoSummary.getPillName())
                   .diseaseName(medicationInfoSummary.getDiseaseName())
                   .takePillCheckIndex(medicationInfoSummary.getTakePillCheckIndex())
                   .takeCheck(medicationInfoSummary.getTakeCheck())
                   .takePillTime(medicationInfoSummary.getTakePillTime())
                   .build();
           medicationInfoDTOs.add(medicationInfoDTO);
        }
        return medicationInfoDTOs;
    }

//    public List<TakePillCheckAndGroupMemberIndexDTO> updateTakePillCheck(Long takePillCheckIndex, TakePillCheckDTO takePillCheckDTO) {
//        // 복용 현황 갱신
//    }

}
