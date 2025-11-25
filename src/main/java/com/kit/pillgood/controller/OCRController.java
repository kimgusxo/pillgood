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

import java.io.IOException;
import java.sql.SQLException;
import java.util.concurrent.CompletableFuture;

import java.time.LocalDate;

@RestController
@RequestMapping("/ocr")
public class OCRController {

    private static final Logger log = LoggerFactory.getLogger(OCRController.class);

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
    @PostMapping("/original")
    public ResponseEntity<ResponseFormat> createOCR(@RequestParam Long groupMemberIndex,
                                                    @RequestParam String groupMemberName,
                                                    @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dateStart,
                                                    @RequestParam String userFCMToken,
                                                    @RequestPart("image") MultipartFile image) throws NonRegistrationGroupException {

        log.info("createOCR - 요청 수신, groupMemberIndex={}, groupMemberName={}, dateStart={}",
                groupMemberIndex, groupMemberName, dateStart);

        if (!groupMemberRepository.existsByGroupMemberIndex(groupMemberIndex)) {
            log.info("createOCR - [err] 존재하지 않은 GroupMemberIndex, groupMemberIndex={}", groupMemberIndex);
            throw new NonRegistrationGroupException();
        }

        if (!groupMemberRepository.existsByGroupMemberName(groupMemberName)) {
            log.info("createOCR - [err] 존재하지 않은 GroupMemberName, groupMemberName={}", groupMemberName);
            throw new NonRegistrationGroupException();
        }

        if (image == null) {
            log.info("createOCR - [err] image가 null입니다.");
            ResponseFormat responseFormat = ResponseFormat.of("Image is null", HttpStatus.NOT_FOUND.value());
            return new ResponseEntity<>(responseFormat, HttpStatus.NOT_FOUND);
        }

        log.info("createOCR - 비동기 OCR 처리 시작, imageName={}, size={}",
                image.getOriginalFilename(), image.getSize());

        CompletableFuture.supplyAsync(() -> {
            try {
                EditOcrDTO editOcrDTO = ocrService.sendImage(groupMemberIndex, groupMemberName, dateStart, image);
                log.debug("createOCR(async) - OCR 결과 수신, editOcrDTO={}", editOcrDTO);

                ocrService.sendOcrData(userFCMToken, editOcrDTO);
                log.info("createOCR(async) - FCM 전송 완료, userFCMToken(일부)={}",
                        userFCMToken.substring(0, Math.min(10, userFCMToken.length())));

                return ResponseFormat.of("success", HttpStatus.OK.value());
            } catch (JsonProcessingException e) {
                log.error("createOCR(async) - OCR 전송 중 JsonProcessingException 발생", e);
                throw new RuntimeException(e);
            } catch (Exception e) {
                log.error("createOCR(async) - 비동기 처리 중 예외 발생", e);
                throw new RuntimeException(e);
            }
        });

        ResponseFormat responseFormat = ResponseFormat.of("success", HttpStatus.OK.value());
        log.debug("createOCR - 클라이언트에 즉시 성공 응답 반환");
        return new ResponseEntity<>(responseFormat, HttpStatus.OK);
    }

    /**
     * 생성된 OCR 정보로 처방전, 복용 현황, 복용 현황 확인 생성
     * @param: EditOcrDTO editOcrDTO, OCR 결과와 사용자가 설정한 값을 합친 정보
     * @return: ResponseEntity<ResponseFormat>, 처방전, 복용현황, 복용현황 확인 결과가 담긴 응답 객체
     **/
    @PostMapping("/prescriptions")
    public ResponseEntity<ResponseFormat> createPrescriptionAndTakePillAndTakePillCheckByOCRData(@RequestBody EditOcrDTO editOcrDTO) throws NonExistsPrescriptionIndexException, NonExistsTakePillException, SQLException {
        log.info("createPrescriptionAndTakePillAndTakePillCheckByOCRData - 요청 수신, editOcrDTO={}", editOcrDTO);

        editOcrDTO = pillService.searchPillNameByPartiallyPillName(editOcrDTO);

        log.debug("createPrescriptionAndTakePillAndTakePillCheckByOCRData - 약 이름 자동 보정 완료, editOcrDTO={}", editOcrDTO);

        ocrService.createPrescriptionAndTakePillAndTakePillCheck(editOcrDTO);

        log.info("createPrescriptionAndTakePillAndTakePillCheckByOCRData - 처방전/복용데이터 생성 완료");

        ResponseFormat responseFormat = ResponseFormat.of("success", HttpStatus.OK.value());

        return new ResponseEntity<>(responseFormat, HttpStatus.OK);
    }
}
