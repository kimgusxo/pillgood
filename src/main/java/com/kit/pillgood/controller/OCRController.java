package com.kit.pillgood.controller;

import com.kit.pillgood.service.OCRService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/ocr")
public class OCRController {
    private final OCRService ocrService;

    @Autowired
    public OCRController(OCRService ocrService) {
        this.ocrService = ocrService;
    }

    @GetMapping("")
    public void test() {
        ocrService.extractPrescriptionTextFromImage();
    }
}
