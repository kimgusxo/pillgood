package com.kit.pillgood.service;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingException;
import com.google.firebase.messaging.Message;
import com.google.firebase.messaging.Notification;
import com.kit.pillgood.controller.ModelController;
import com.kit.pillgood.exeptions.exeption.NonExistsPrescriptionIndexException;
import com.kit.pillgood.exeptions.exeption.NonExistsTakePillException;
import com.kit.pillgood.persistence.dto.EditOcrDTO;
import com.kit.pillgood.persistence.dto.OriginalOcrDTO;
import com.kit.pillgood.persistence.dto.PillScheduleDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.util.List;

@Service
public class OCRService {
    private final Logger LOGGER = LoggerFactory.getLogger(OCRService.class);
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

    public void sendOcrData(String userFCMToken, EditOcrDTO editOcrDTO) throws JsonProcessingException {

        List<PillScheduleDTO> pillList = editOcrDTO.getPillList();
        ObjectMapper objectMapper = new ObjectMapper();
        String pillListJson = objectMapper.writeValueAsString(pillList);

        Message message = Message.builder()
                .setNotification(Notification.builder()
                        .setTitle("알림")
                        .setBody("OCR 등록 완료")
                        .build())
                .putData("그룹원 이름", editOcrDTO.getGroupMemberName())
                .putData("복용 시작 날짜", editOcrDTO.getStartDate().toString())
                .putData("병원 이름", editOcrDTO.getHospitalName())
                .putData("병원 전화번호", editOcrDTO.getPhoneNumber())
                .putData("질병 코드", editOcrDTO.getDiseaseCode())
                .putData("약 정보", pillListJson)
                .setToken(userFCMToken)
                .build();

        // FCM 메시지 전송
        try {
            String response = FirebaseMessaging.getInstance().send(message);
            System.out.println("Successfully sent message: " + response);
        } catch (FirebaseMessagingException e) {
            System.out.println("Failed to send message: " + e.getMessage());
        }
    }

    public void createPrescriptionAndTakePillAndTakePillCheck(EditOcrDTO editOcrDTO) throws NonExistsPrescriptionIndexException, NonExistsTakePillException {
        Long prescriptionIndex = prescriptionService.createPrescriptionByOCRData(editOcrDTO);
        if(prescriptionIndex == null){
            LOGGER.info(".createPrescriptionAndTakePillAndTakePillCheck [ERR] 생성된 Prescription이 없습니다.");
            throw new NonExistsPrescriptionIndexException();
        }
        List<Long> takePillIndexList = takePillService.createTakePillByOCRData(prescriptionIndex, editOcrDTO);
        if(takePillIndexList.size() == 0){
            LOGGER.info(".createPrescriptionAndTakePillAndTakePillCheck [ERR] 생성된 TakePill이 없습니다.");
            throw new NonExistsTakePillException();
        }
        takePillCheckService.createTakePillCheckByOCRData(takePillIndexList, editOcrDTO);
        LOGGER.info(".createPrescriptionAndTakePillAndTakePillCheck 수행 완료");

    }

}