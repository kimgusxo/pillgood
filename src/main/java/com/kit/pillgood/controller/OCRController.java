package com.kit.pillgood.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.kit.pillgood.common.ResponseFormat;
import com.kit.pillgood.exeptions.exeption.NonExistsPrescriptionIndexException;
import com.kit.pillgood.exeptions.exeption.NonExistsTakePillException;
import com.kit.pillgood.exeptions.exeption.NonRegistrationGroupException;
import com.kit.pillgood.persistence.dto.EditOcrDTO;
import com.kit.pillgood.repository.GroupMemberRepository;
import com.kit.pillgood.service.OCRService;
import com.kit.pillgood.service.PillService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.sql.SQLException;
import java.util.concurrent.CompletableFuture;

import java.time.LocalDate;

@RestController
@RequestMapping("/ocr")
public class OCRController {
    private final Logger LOGGER = LoggerFactory.getLogger(OCRController.class);
    private final OCRService ocrService;
    private final PillService pillService;
    private final GroupMemberRepository groupMemberRepository;

    @Autowired
    public OCRController(OCRService ocrService,
                         PillService pillService, GroupMemberRepository groupMemberRepository) {
        this.ocrService = ocrService;
        this.pillService = pillService;
        this.groupMemberRepository = groupMemberRepository;
    }

    /**
     * 사용자의 처방전으로 OCR 정보 생성
     * @param: Long groupMemberIndex, 처방전을 생성할 그룹원 인덱스
     * @param: String groupMemberName, 처방전을 생성할 그룹원 이름
     * @param: LocalDate dateStart, 그룹원의 약 복용 시작 일자
     * @param: String userFCMToken, FCM을 보내기 위한 사용자 토큰
     * @param: MultipartFile image, 모델서버로 전송할 이미지
     * @return: ResponseEntity<ResponseFormat>, OCR 결과가 담긴 응답 객체
     **/
    @PostMapping("/create/original")
    public ResponseEntity<ResponseFormat> createOCR(@RequestParam Long groupMemberIndex,
                                                    @RequestParam String groupMemberName,
                                                    @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dateStart,
                                                    @RequestParam String userFCMToken,
                                                    @RequestParam MultipartFile image) throws NonRegistrationGroupException {

        if(!groupMemberRepository.existsByGroupMemberIndex(groupMemberIndex)){
            LOGGER.info("createOCR. [err] 존재하지 않은 GroupMemberIndex GroupMemberIndex={}", groupMemberIndex);
            throw new NonRegistrationGroupException();
        }

        if(!groupMemberRepository.existsByGroupMemberName(groupMemberName)){
            LOGGER.info("createOCR. [err] 존재하지 않은 GroupMemberName GroupMemberName={}", groupMemberName);
            throw new NonRegistrationGroupException();
        }

        if (image != null) {
            CompletableFuture.supplyAsync(() -> {
                EditOcrDTO editOcrDTO = ocrService.sendImage(groupMemberIndex, groupMemberName, dateStart, image);
                try {
                    ocrService.sendOcrData(userFCMToken, editOcrDTO);

                    ResponseFormat responseFormat = ResponseFormat.of("success", HttpStatus.OK.value());
                    return new ResponseEntity<>(responseFormat, HttpStatus.OK);

                } catch (JsonProcessingException e) {
                    LOGGER.info("createOCR. [err] OCR 전송 오류");
                    throw new RuntimeException(e);
                }
            });

            ResponseFormat responseFormat = ResponseFormat.of("success", HttpStatus.OK.value());
            return new ResponseEntity<>(responseFormat, HttpStatus.OK);

        } else {
            ResponseFormat responseFormat = ResponseFormat.of("Image is null", HttpStatus.NOT_FOUND.value());
            LOGGER.info("createOCR. [err] image를 찾을 수 없음");
            return new ResponseEntity<>(responseFormat, HttpStatus.NOT_FOUND);
        }
    }

    /**
     * 생성된 OCR 정보로 처방전, 복용 현황, 복용 현황 확인 생성
     * @param: EditOcrDTO editOcrDTO, OCR 결과와 사용자가 설정한 값을 합친 정보
     * @return: ResponseEntity<ResponseFormat>, 처방전, 복용현황, 복용현황 확인 결과가 담긴 응답 객체
     **/
    @PostMapping("/create")
    public ResponseEntity<ResponseFormat> createPrescriptionAndTakePillAndTakePillCheckByOCRData(@ModelAttribute EditOcrDTO editOcrDTO) throws NonExistsPrescriptionIndexException, NonExistsTakePillException, NonExistsPrescriptionIndexException, NonExistsTakePillException, SQLException {
        editOcrDTO = pillService.searchPillNameByPartiallyPillName(editOcrDTO);
        ocrService.createPrescriptionAndTakePillAndTakePillCheck(editOcrDTO);

        ResponseFormat responseFormat = ResponseFormat.of("success", HttpStatus.OK.value());
        return new ResponseEntity<>(responseFormat, HttpStatus.OK);
    }
}
