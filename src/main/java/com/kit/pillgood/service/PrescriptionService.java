package com.kit.pillgood.service;

import com.kit.pillgood.persistence.dto.PrescriptionAndDiseaseNameDTO;
import com.kit.pillgood.persistence.dto.PrescriptionDTO;
import com.kit.pillgood.persistence.projection.PrescriptionAndDiseaseNameSummary;
import com.kit.pillgood.repository.GroupMemberRepository;
import com.kit.pillgood.repository.PrescriptionRepository;
import com.kit.pillgood.repository.UserRepository;
import com.kit.pillgood.util.EntityConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@Service
public class PrescriptionService {
    private final PrescriptionRepository prescriptionRepository;

    @Autowired
    public PrescriptionService(PrescriptionRepository prescriptionRepository) {
        this.prescriptionRepository = prescriptionRepository;
    }

//    public PrescriptionDTO createPrescription(Long userIndex, Long groupMemberIndex, MultipartFile imageFile) {
//        // 처방전을 생성 OCR 사용부분
//    }

    @Transactional
    public List<PrescriptionAndDiseaseNameDTO> searchGroupMemberPrescriptionsByGroupMemberIndex(Long groupMemberIndex) {
        List<PrescriptionAndDiseaseNameSummary> prescriptionAndDiseaseNameSummaries = prescriptionRepository.findPrescriptionAndDiseaseNameByGroupMemberIndex(groupMemberIndex);
        prescriptionAndDiseaseNameSummaries.stream().forEach(p-> System.out.println("p.getPrescriptionIndex() = " + p.getPrescriptionIndex()));
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
