package com.kit.pillgood.controller;

import com.kit.pillgood.persistence.dto.EditOcrDTO;
import com.kit.pillgood.service.OCRService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;

@RestController
@RequestMapping("/ocr")
public class OCRController {
    private final OCRService ocrService;
    @Autowired
    public OCRController(OCRService ocrService) {
        this.ocrService = ocrService;
    }

    @PostMapping("/create/original")
    public EditOcrDTO createOCR(@RequestParam Long groupMemberIndex,
                                @RequestParam String groupMemberName,
                                @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dateStart,
                                @RequestParam MultipartFile image) {
        EditOcrDTO editOcrDTO = ocrService.sendImage(groupMemberIndex, groupMemberName, dateStart, image);
        return editOcrDTO; // 이미지 전송에 대한 성공 및 실패만 처리
    }

    @PostMapping("/create")
    public void createPrescriptionAndTakePillAndTakePillCheckByOCRData(@ModelAttribute EditOcrDTO editOcrDTO) {
        ocrService.createPrescriptionAndTakePillAndTakePillCheck(editOcrDTO);
    }

}
