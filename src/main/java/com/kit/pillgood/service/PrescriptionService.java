package com.kit.pillgood.service;

import com.kit.pillgood.domain.Prescription;
import com.kit.pillgood.persistence.dto.PrescriptionDTO;
import com.kit.pillgood.persistence.projections.PrescriptionIndexAndDiseaseIndexSummary;
import com.kit.pillgood.repository.GroupMemberRepository;
import com.kit.pillgood.repository.PrescriptionRepository;
import com.kit.pillgood.repository.UserRepository;
import com.kit.pillgood.util.EntityConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@Service
public class PrescriptionService {
    private final UserRepository userRepsitory;
    private final GroupMemberRepository groupMemberRepository;
    private final PrescriptionRepository prescriptionRepsitory;
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
        this.userRepsitory = userRepository;
        this.groupMemberRepository = groupMemberRepository;
        this.prescriptionRepsitory = prescriptionRepository;
        this.notificationService = notificationService;
        this.ocrService = ocrService;
        this.diseaseService = diseaseService;
        this.takePillService = takePillService;
    }

    public PrescriptionDTO createPrescription(Long userIndex, Long groupMemberIndex, MultipartFile imageFile) {
        // 처방전을 생성
    }

    public List<PrescriptionDTO> searchGroupMemberPrescriptionsByGroupMemberIndex(Long groupMemberIndex) {
        List<PrescriptionIndexAndDiseaseIndexSummary> prescriptions = prescriptionRepsitory.findProjectionsByGroupMemberIndex(groupMemberIndex);
        List<PrescriptionDTO> prescriptionDTOs = new ArrayList<>();

        for(Prescription prescription : prescriptions) {
            PrescriptionDTO prescriptionDTO = EntityConverter.toPrescriptionDTO(prescription);
            prescriptionDTOs.add(prescriptionDTO);
        }

        return prescriptionDTOs;
    }

    public void deletePrescription(Long prescriptionIndex) {
        // 처방전을 삭제
    }

}
