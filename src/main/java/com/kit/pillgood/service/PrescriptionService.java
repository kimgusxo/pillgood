package com.kit.pillgood.service;

import com.kit.pillgood.domain.Disease;
import com.kit.pillgood.domain.Prescription;
import com.kit.pillgood.exeptions.exeption.NonExistsPrescriptionIndexException;
import com.kit.pillgood.exeptions.exeption.NonRegistrationGroupException;
import com.kit.pillgood.persistence.dto.EditOcrDTO;
import com.kit.pillgood.persistence.dto.PartiallyTakePillDTO;
import com.kit.pillgood.persistence.dto.PrescriptionAndDiseaseNameDTO;
import com.kit.pillgood.persistence.projection.PartiallyTakePillSummary;
import com.kit.pillgood.persistence.projection.PrescriptionAndDiseaseNameSummary;
import com.kit.pillgood.repository.DiseaseRepository;
import com.kit.pillgood.repository.GroupMemberRepository;
import com.kit.pillgood.repository.PrescriptionRepository;
import com.kit.pillgood.repository.TakePillRepository;
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
    private final TakePillRepository takePillRepository;
    private final GroupMemberRepository groupMemberRepository;
    private final DiseaseRepository diseaseRepository;

    @Autowired
    public PrescriptionService(PrescriptionRepository prescriptionRepository,
                               TakePillRepository takePillRepository,
                               GroupMemberRepository groupMemberRepository,
                               DiseaseRepository diseaseRepository) {
        this.prescriptionRepository = prescriptionRepository;
        this.takePillRepository = takePillRepository;
        this.groupMemberRepository = groupMemberRepository;
        this.diseaseRepository = diseaseRepository;
    }

    @Transactional
    public Long createPrescriptionByOCRData(EditOcrDTO editOcrDTO) {
        Disease disease = diseaseRepository.findByDiseaseCode(editOcrDTO.getDiseaseCode());

        Prescription prescription = EntityConverter.toPrescription(disease, editOcrDTO);
        prescription = prescriptionRepository.save(prescription);

        LOGGER.info(".createPrescriptionByOCRData Prescription 생성 성공 prescription={}", prescription);

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
            List<PartiallyTakePillSummary> partiallyTakePillSummaries = takePillRepository.findPartiallyTakePillByPrescriptionIndex(prescriptionAndDiseaseNameSummary.getPrescriptionIndex());
            List<PartiallyTakePillDTO> partiallyTakePillDTOList = new ArrayList<>();
            for(PartiallyTakePillSummary partiallyTakePillSummary : partiallyTakePillSummaries) {
                PartiallyTakePillDTO partiallyTakePillDTO = PartiallyTakePillDTO.builder()
                        .pillName(partiallyTakePillSummary.getPillName())
                        .takeDay(partiallyTakePillSummary.getTakeDay())
                        .takeCount(partiallyTakePillSummary.getTakeCount())
                        .build();
                partiallyTakePillDTOList.add(partiallyTakePillDTO);
            }
            prescriptionAndDiseaseNameDTOs.add(EntityConverter.toPrescriptionAndDiseaseNameDTO(prescriptionAndDiseaseNameSummary, partiallyTakePillDTOList));
        }

        LOGGER.info("처방전 조회 성공 {}", prescriptionAndDiseaseNameDTOs);
        return prescriptionAndDiseaseNameDTOs;
    }

    @Transactional
    public void deletePrescription(Long prescriptionIndex) throws NonExistsPrescriptionIndexException {
        if(!prescriptionRepository.existsByPrescriptionIndex(prescriptionIndex)){
            LOGGER.info("[err] prescriptionIndex={}을 찾을 수 없음", prescriptionIndex);
            throw new NonExistsPrescriptionIndexException();
        }
        prescriptionRepository.deleteById(prescriptionIndex);
        LOGGER.info("[err] prescriptionIndex={} 삭제 성공", prescriptionIndex);
    }

}
