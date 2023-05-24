package com.kit.pillgood.service;

import com.kit.pillgood.exeptions.exeption.NonExistsPrescriptionIndexException;
import com.kit.pillgood.exeptions.exeption.NonRegistrationGroupException;
import com.kit.pillgood.persistence.dto.PrescriptionAndDiseaseNameDTO;
import com.kit.pillgood.persistence.projection.PrescriptionAndDiseaseNameSummary;
import com.kit.pillgood.repository.GroupMemberRepository;
import com.kit.pillgood.repository.PrescriptionRepository;
import com.kit.pillgood.util.EntityConverter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class PrescriptionService {
    private final Logger LOGGER = LoggerFactory.getLogger(PrescriptionService.class);
    private final PrescriptionRepository prescriptionRepository;
    private final GroupMemberRepository groupMemberRepository;

    @Autowired
    public PrescriptionService(PrescriptionRepository prescriptionRepository, GroupMemberRepository groupMemberRepository) {
        this.prescriptionRepository = prescriptionRepository;
        this.groupMemberRepository = groupMemberRepository;
    }

//    public PrescriptionDTO createPrescription(Long userIndex, Long groupMemberIndex, MultipartFile imageFile) {
//        // 처방전을 생성 OCR 사용부분
//    }

    @Transactional
    public List<PrescriptionAndDiseaseNameDTO> searchGroupMemberPrescriptionsByGroupMemberIndex(Long groupMemberIndex) throws NonRegistrationGroupException {

        if(!groupMemberRepository.existsByGroupMemberIndex(groupMemberIndex)){
            LOGGER.info("[err] 존재하지 않는 그룹원={} 조회", groupMemberIndex);
            throw new NonRegistrationGroupException();
        }

        List<PrescriptionAndDiseaseNameSummary> prescriptionAndDiseaseNameSummaries = prescriptionRepository.findPrescriptionAndDiseaseNameByGroupMemberIndex(groupMemberIndex);
        List<PrescriptionAndDiseaseNameDTO> prescriptionAndDiseaseNameDTOs = new ArrayList<>();

        for(PrescriptionAndDiseaseNameSummary prescriptionAndDiseaseNameSummary : prescriptionAndDiseaseNameSummaries) {
            prescriptionAndDiseaseNameDTOs.add(EntityConverter.toPrescriptionAndDiseaseNameDTO(prescriptionAndDiseaseNameSummary));
        }

        LOGGER.info("처방전 조회 성공 {}", prescriptionAndDiseaseNameDTOs);
        return prescriptionAndDiseaseNameDTOs;
    }

    public void deletePrescription(Long prescriptionIndex) throws NonExistsPrescriptionIndexException {
        if(!prescriptionRepository.existsByPrescriptionIndex(prescriptionIndex)){
            LOGGER.info("[err] prescriptionIndex={}을 찾을 수 없음", prescriptionIndex);
            throw new NonExistsPrescriptionIndexException();
        }
        prescriptionRepository.deleteById(prescriptionIndex);
        LOGGER.info("[err] prescriptionIndex={} 삭제 성공", prescriptionIndex);
    }

}
