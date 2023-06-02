package com.kit.pillgood.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.kit.pillgood.persistence.dto.EditOcrDTO;
import com.kit.pillgood.service.OCRService;
import com.kit.pillgood.service.PillService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.util.concurrent.CompletableFuture;

import java.time.LocalDate;

@RestController
@RequestMapping("/ocr")
public class OCRController {
    private final OCRService ocrService;
    private final PillService pillService;

    @Autowired
    public OCRController(OCRService ocrService,
                         PillService pillService) {
        this.ocrService = ocrService;
        this.pillService = pillService;
    }

    @PostMapping("/create/original")
    public ResponseEntity<ResponseFormat> createOCR(@RequestParam Long groupMemberIndex,
                                            @RequestParam String groupMemberName,
                                            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dateStart,
                                            @RequestParam String userFCMToken,
                                            @RequestParam MultipartFile image) {
        if (image != null) {
            CompletableFuture.supplyAsync(() -> {
                EditOcrDTO editOcrDTO = ocrService.sendImage(groupMemberIndex, groupMemberName, dateStart, image);
                System.out.println("실행중");
                try {
                    ocrService.sendOcrData(userFCMToken, editOcrDTO);

                    ResponseFormat responseFormat = ResponseFormat.of("success", HttpStatus.OK.value());
                    return new ResponseEntity<>(responseFormat, HttpStatus.OK);

                } catch (JsonProcessingException e) {
                    throw new RuntimeException(e);
                }
            });

            ResponseFormat responseFormat = ResponseFormat.of("success", HttpStatus.OK.value());
            return new ResponseEntity<>(responseFormat, HttpStatus.OK);

        } else {
            ResponseFormat responseFormat = ResponseFormat.of("Image is null", HttpStatus.NOT_FOUND.value());
            return new ResponseEntity<>(responseFormat, HttpStatus.NOT_FOUND);
        }
    }

    // ocr data로 테이블 데이터 생성
    @PostMapping("/create")
    public ResponseEntity<ResponseFormat> createPrescriptionAndTakePillAndTakePillCheckByOCRData(@ModelAttribute EditOcrDTO editOcrDTO) throws NonExistsPrescriptionIndexException, NonExistsTakePillException {
        editOcrDTO = pillService.searchPillNameByPartiallyPillName(editOcrDTO);
        ocrService.createPrescriptionAndTakePillAndTakePillCheck(editOcrDTO);

        ResponseFormat responseFormat = ResponseFormat.of("success", HttpStatus.OK.value());
        return new ResponseEntity<>(responseFormat, HttpStatus.OK);
    }

}
