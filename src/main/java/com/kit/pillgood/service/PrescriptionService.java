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
    private final UserRepository userRepository;
    private final GroupMemberRepository groupMemberRepository;
    private final PrescriptionRepository prescriptionRepository;
    private final NotificationService notificationService;
    private final OCRService ocrService;
    private final DiseaseService diseaseService;
    private final TakePillService takePillService;

    @Autowired
    public PrescriptionService(UserRepository userRepository,
                               GroupMemberRepository groupMemberRepository,
                               PrescriptionRepository prescriptionRepository,
                               NotificationService notificationService,
                               OCRService ocrService,
                               DiseaseService diseaseService,
                               TakePillService takePillService) {
        this.userRepository = userRepository;
        this.groupMemberRepository = groupMemberRepository;
        this.prescriptionRepository = prescriptionRepository;
        this.notificationService = notificationService;
        this.ocrService = ocrService;
        this.diseaseService = diseaseService;
        this.takePillService = takePillService;
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
