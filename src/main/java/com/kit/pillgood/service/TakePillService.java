package com.kit.pillgood.service;

import com.kit.pillgood.domain.GroupMember;
import com.kit.pillgood.exeptions.exeption.NonExistsMedicationInfoException;
import com.kit.pillgood.domain.Pill;
import com.kit.pillgood.domain.TakePill;
import com.kit.pillgood.exeptions.exeption.NonExistsTakePillException;
import com.kit.pillgood.exeptions.exeption.NonRegistrationUserException;
import com.kit.pillgood.persistence.dto.*;
import com.kit.pillgood.persistence.projection.MedicationInfoSummary;
import com.kit.pillgood.persistence.projection.PrescriptionIndexSummary;
import com.kit.pillgood.persistence.projection.TakePillAndTakePillCheckSummary;
import com.kit.pillgood.repository.*;
import com.kit.pillgood.util.EntityConverter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.*;

@Service
public class TakePillService {
    private final Logger LOGGER = LoggerFactory.getLogger(TakePillService.class);
    private final GroupMemberRepository groupMemberRepository;
    private final TakePillRepository takePillRepository;
    private final PrescriptionRepository prescriptionRepository;
    private final PillRepository pillRepository;
    private final UserRepository userRepository;

    @Autowired
    public TakePillService(GroupMemberRepository groupMemberRepository,
                           TakePillRepository takePillRepository,
                           PrescriptionRepository prescriptionRepository,
                           PillRepository pillRepository, UserRepository userRepository){
        this.groupMemberRepository = groupMemberRepository;
        this.takePillRepository = takePillRepository;
        this.prescriptionRepository = prescriptionRepository;
        this.pillRepository = pillRepository;
        this.userRepository = userRepository;
    }

    @Transactional
    public List<Long> createTakePillByOCRData(Long prescriptionIndex, EditOcrDTO editOcrDTO) throws NonExistsTakePillException {

        List<TakePill> takePillList = new ArrayList<>();

        if(takePillList.isEmpty()){
            LOGGER.info(".createTakePillByOCRData [ERR] TakePill이 존재하지 않습니다.");
            throw new NonExistsTakePillException();
        }

        // 약 개수가 여러개니까 리스트로 저장해서 필 인덱스를 추출
        for(PillScheduleDTO pillScheduleDTO : editOcrDTO.getPillList()) {
            Pill pill = pillRepository.findByPillName(pillScheduleDTO.getPillName(), PageRequest.of(0, 1));

            TakePill takePill = EntityConverter.toTakePill(prescriptionIndex, pill, pillScheduleDTO);
            takePillList.add(takePill);
        }

        takePillList = takePillRepository.saveAll(takePillList);

        LOGGER.info(".createTakePillByOCRData TakePill 생성 완료 takePillList={}",takePillList);

        List<Long> takePillIndexList = new ArrayList<>();

        for(TakePill takePill : takePillList) {
            takePillIndexList.add(takePill.getTakePillIndex());
        }

        return takePillIndexList;
    }

    @Transactional
    public List<TakePillAndTakePillCheckAndGroupMemberIndexDTO> searchTakePillCheckListByUserIndexBetweenTakeDate(Long userIndex, LocalDate dateStart, LocalDate dateEnd) throws NonRegistrationUserException {
       try{

            if(!userRepository.existsByUserIndex(userIndex)){
                throw new NonRegistrationUserException();
            }

            List<GroupMember> groupMembers = groupMemberRepository.findByUser_UserIndex(userIndex);
            List<TakePillAndTakePillCheckAndGroupMemberIndexDTO> takePillAndTakePillCheckAndGroupMemberIndexDTOList = new ArrayList<>();

            for(GroupMember groupMember : groupMembers) {
                List<PrescriptionIndexSummary> prescriptionIndexList = prescriptionRepository.findPrescriptionIndexByGroupMember_GroupMemberIndexAndPrescriptionDateBetween(groupMember.getGroupMemberIndex(), dateStart, dateEnd);
                for(PrescriptionIndexSummary prescriptionIndex : prescriptionIndexList) {
                    List<TakePillAndTakePillCheckSummary> takePillAndTakePillCheckSummaries = takePillRepository.findTakePillAndCheckByPrescriptionIndex(prescriptionIndex.getPrescriptionIndex());
                    List<TakePillAndTakePillCheckDTO> takePillAndTakePillCheckDTOs = new ArrayList<>();
                    for(TakePillAndTakePillCheckSummary takePillAndTakePillCheckSummary : takePillAndTakePillCheckSummaries) {
                        TakePillAndTakePillCheckDTO takePillAndTakePillCheckDTO
                                = EntityConverter.toTakePillAndTakePillCheckDTO(takePillAndTakePillCheckSummary);
                        takePillAndTakePillCheckDTOs.add(takePillAndTakePillCheckDTO);
                    }
                    TakePillAndTakePillCheckAndGroupMemberIndexDTO takePillAndTakePillCheckAndGroupMemberIndexDTO
                            = EntityConverter.toTakePillAndTakePillCheckAndGroupMemberIndexDTO(groupMember, takePillAndTakePillCheckDTOs);

                    takePillAndTakePillCheckAndGroupMemberIndexDTOList.add(takePillAndTakePillCheckAndGroupMemberIndexDTO);
                }
            }
            return takePillAndTakePillCheckAndGroupMemberIndexDTOList;
       }
       catch (NonRegistrationUserException ignore){
           throw new NonRegistrationUserException();
       }
    }

    @Transactional
    public List<MedicationInfoDTO> searchMedicationInfoListByGroupMemberIndexListAndTargetDate(List<Long> groupMemberIndexList, LocalDate targetDate) throws NonExistsMedicationInfoException {
        List<MedicationInfoDTO> medicationInfoDTOs = new ArrayList<>();
        for(Long groupMemberIndex : groupMemberIndexList) {
            List<MedicationInfoSummary> medicationInfoSummaries = takePillRepository.findMedicationInfoByGroupMemberIndexAndTargetDate(groupMemberIndex, targetDate);
            for(MedicationInfoSummary medicationInfoSummary : medicationInfoSummaries) {
                MedicationInfoDTO medicationInfoDTO = EntityConverter.toMedicationInfo(medicationInfoSummary);
                medicationInfoDTOs.add(medicationInfoDTO);

                if (medicationInfoSummary != null) {
                    medicationInfoDTOs.add(medicationInfoDTO);
                }
            }
        }

        if(medicationInfoDTOs.size() == 0){
            LOGGER.info(".searchMedicationInfoListByGroupMemberIndexListAndTargetDate [err] medicationInfoSummary is null");
            throw new NonExistsMedicationInfoException();
        }
        return medicationInfoDTOs;
    }
}
