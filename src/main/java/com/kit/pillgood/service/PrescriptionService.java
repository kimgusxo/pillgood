package com.kit.pillgood.service;

import com.kit.pillgood.persistence.dto.PrescriptionAndDiseaseNameDTO;
import com.kit.pillgood.persistence.dto.PrescriptionDTO;
import com.kit.pillgood.repository.GroupMemberRepository;
import com.kit.pillgood.repository.PrescriptionRepository;
import com.kit.pillgood.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

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

    public List<PrescriptionAndDiseaseNameDTO> searchGroupMemberPrescriptionsByGroupMemberIndex(Long groupMemberIndex) {
        return prescriptionRepository.findPrescriptionAndDiseaseNameByGroupMemberIndex(groupMemberIndex);
    }

    public void deletePrescription(Long prescriptionIndex) {
        prescriptionRepository.deleteById(prescriptionIndex);
    }

}
