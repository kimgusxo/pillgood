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
    public ResponseEntity<String> createOCR(@RequestParam Long groupMemberIndex,
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
                    return ResponseEntity.ok("OCR 정보 전송 성공.");
                } catch (JsonProcessingException e) {
                    throw new RuntimeException(e);
                }
            });
            return ResponseEntity.ok("이미지 전송 성공.");
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("이미지 전송 실패.");
        }
    }

    @PostMapping("/create")
    public void createPrescriptionAndTakePillAndTakePillCheckByOCRData(@ModelAttribute EditOcrDTO editOcrDTO) {
        editOcrDTO = pillService.searchPillNameByPartiallyPillName(editOcrDTO);
        ocrService.createPrescriptionAndTakePillAndTakePillCheck(editOcrDTO);
    }
}
