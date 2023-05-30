package com.kit.pillgood.service;

import com.kit.pillgood.domain.Disease;
import com.kit.pillgood.domain.GroupMember;
import com.kit.pillgood.domain.Prescription;
import com.kit.pillgood.exeptions.exeption.NonExistsPrescriptionIndexException;
import com.kit.pillgood.exeptions.exeption.NonRegistrationGroupException;
import com.kit.pillgood.persistence.dto.EditOcrDTO;
import com.kit.pillgood.persistence.dto.PrescriptionAndDiseaseNameDTO;
import com.kit.pillgood.persistence.projection.PrescriptionAndDiseaseNameSummary;
import com.kit.pillgood.repository.DiseaseRepository;
import com.kit.pillgood.repository.GroupMemberRepository;
import com.kit.pillgood.repository.PrescriptionRepository;
import com.kit.pillgood.util.EntityConverter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class PrescriptionService {
    private final Logger LOGGER = LoggerFactory.getLogger(PrescriptionService.class);
    private final PrescriptionRepository prescriptionRepository;
    private final GroupMemberRepository groupMemberRepository;
    private final DiseaseRepository diseaseRepository;

    @Autowired
    public PrescriptionService(PrescriptionRepository prescriptionRepository,
                               GroupMemberRepository groupMemberRepository,
                               DiseaseRepository diseaseRepository) {
        this.prescriptionRepository = prescriptionRepository;
        this.groupMemberRepository = groupMemberRepository;
        this.diseaseRepository = diseaseRepository;
    }

    public Long createPrescriptionByOCRData(EditOcrDTO editOcrDTO) {
        Disease disease = diseaseRepository.findByDiseaseCode(editOcrDTO.getDiseaseCode());

        Prescription prescription = Prescription.builder()
                .prescriptionIndex(null)
                .groupMember(GroupMember.builder()
                        .groupMemberIndex(editOcrDTO.getGroupMemberIndex())
                        .build())
                .disease(Disease.builder()
                        .diseaseIndex(disease.getDiseaseIndex())
                        .build())
                .prescriptionRegistrationDate(LocalDate.now())
                .prescriptionDate(editOcrDTO.getStartDate())
                .hospitalPhone(editOcrDTO.getPhoneNumber())
                .hospitalName(editOcrDTO.getHospitalName())
                .build();

        prescription = prescriptionRepository.save(prescription);
        return prescription.getPrescriptionIndex();
    }

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
