package com.kit.pillgood.service;

import com.kit.pillgood.domain.TakePill;
import com.kit.pillgood.persistence.dto.MedicationInfoDTO;
import com.kit.pillgood.persistence.dto.TakePillCheckAndGroupMemberIndexDTO;
import com.kit.pillgood.persistence.dto.TakePillCheckDTO;
import com.kit.pillgood.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class TakePillService {
    private final GroupMemberRepository groupMemberRepository;
    private final TakePillCheckRepository takePillCheckRepository;
    private final TakePillRepository takePillRepository;
    private final PrescriptionRepository prescriptionRepository;
    private final DiseaseRepository diseaseRepository;
    private final PillRepository pillRepository;

    @Autowired
    public TakePillService(GroupMemberRepository groupMemberRepository,
                           TakePillCheckRepository takePillCheckRepository,
                           TakePillRepository takePillRepository,
                           PrescriptionRepository prescriptionRepository,
                           DiseaseRepository diseaseRepository,
                           PillRepository pillRepository) {
        this.groupMemberRepository = groupMemberRepository;
        this.takePillCheckRepository = takePillCheckRepository;
        this.takePillRepository = takePillRepository;
        this.prescriptionRepository = prescriptionRepository;
        this.diseaseRepository = diseaseRepository;
        this.pillRepository = pillRepository;
    }

    public TakePill createTakePill(Long prescriptionIndex, Long pillIndex, Integer takeDay, Integer takeCount) {
        // 복용해야 할 약 생성
    }

    public List<TakePill> createTakePillCheckList(TakePill takePill, LocalDate takeDateStart, Integer takePillTimeStart) {
        // 복용해야 할 약 리스트 생성
    }

    public TakePillCheckAndGroupMemberIndexDTO searchTakePillCheckListByUserIndexBetweenTakeDate(Long prescriptionIndex) {
        // 유저 인덱스, 시작 시간, 끝 시간으로 처방전을 검색
    }

    public MedicationInfoDTO searchMedicationInfoListByUserIndexAndTakeDate(Long userIndex, LocalDate takeDate) {
        // 유저 인덱스와 해당 날짜로 그룹원의 복용 정보(약, 질병 등) 검색
    }

    public List<TakePillCheckAndGroupMemberIndexDTO> updateTakePillCheck(Long takePillCheckIndex, TakePillCheckDTO takePillCheckDTO) {
        // 복용 현황 갱신
    }

0}
