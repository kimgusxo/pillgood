package com.kit.pillgood.service;

import com.kit.pillgood.exeptions.exeption.NonRegistrationGroupException;
import com.kit.pillgood.persistence.dto.PrescriptionAndDiseaseNameDTO;
import com.kit.pillgood.persistence.projection.PrescriptionAndDiseaseNameSummary;
import com.kit.pillgood.repository.GroupMemberRepository;
import com.kit.pillgood.repository.PrescriptionRepository;
import com.kit.pillgood.util.EntityConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class PrescriptionService {
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

        if(groupMemberRepository.findByGroupMemberIndex(groupMemberIndex) == null){
            throw new NonRegistrationGroupException();
        }

        List<PrescriptionAndDiseaseNameSummary> prescriptionAndDiseaseNameSummaries = prescriptionRepository.findPrescriptionAndDiseaseNameByGroupMemberIndex(groupMemberIndex);
        List<PrescriptionAndDiseaseNameDTO> prescriptionAndDiseaseNameDTOs = new ArrayList<>();

        for(PrescriptionAndDiseaseNameSummary prescriptionAndDiseaseNameSummary : prescriptionAndDiseaseNameSummaries) {
            prescriptionAndDiseaseNameDTOs.add(EntityConverter.toPrescriptionAndDiseaseNameDTO(prescriptionAndDiseaseNameSummary));
        }
        return prescriptionAndDiseaseNameDTOs;
    }

    public void deletePrescription(Long prescriptionIndex) {
        prescriptionRepository.deleteById(prescriptionIndex);
    }

}
