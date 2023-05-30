package com.kit.pillgood.service;
import com.kit.pillgood.controller.ModelController;
import com.kit.pillgood.domain.Prescription;
import com.kit.pillgood.persistence.dto.EditOcrDTO;
import com.kit.pillgood.persistence.dto.OriginalOcrDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;

@Service
public class OCRService {

    private final ModelController modelController;
    private final PrescriptionService prescriptionService;
    private final TakePillService takePillService;
    private final TakePillCheckService takePillCheckService;

    @Autowired
    public OCRService(ModelController modelController,
                      PrescriptionService prescriptionService,
                      TakePillService takePillService,
                      TakePillCheckService takePillCheckService) {
        this.modelController = modelController;
        this.prescriptionService = prescriptionService;
        this.takePillService = takePillService;
        this.takePillCheckService = takePillCheckService;
    }

    public EditOcrDTO sendImage(Long groupMemberIndex, String groupMemberName, LocalDate dateStart, MultipartFile image) {
        OriginalOcrDTO resultOCR = modelController.sendImage(image);

        EditOcrDTO editOcrDTO = EditOcrDTO.builder()
                .groupMemberIndex(groupMemberIndex)
                .groupMemberName(groupMemberName)
                .startDate(dateStart)
                .hospitalName(resultOCR.getHospitalName())
                .phoneNumber(resultOCR.getPhoneNumber())
                .diseaseCode(resultOCR.getDiseaseCode())
                .pillList(resultOCR.getPillNameList())
                .build();

        return editOcrDTO; // FCM활용해서 클라이언트에 알림
    }

    public void createPrescriptionAndTakePillAndTakePillCheck(EditOcrDTO editOcrDTO) {
        Long prescriptionIndex = prescriptionService.createPrescriptionByOCRData(editOcrDTO);
        Long takePillIndex = takePillService.createTakePillByOCRData(prescriptionIndex, editOcrDTO);
        takePillCheckService.createTakePillCheckByOCRData(takePillIndex, editOcrDTO);
    }

}