package com.kit.pillgood.controller;

import com.kit.pillgood.service.OCRService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

@RestController
@RequestMapping("/ocr")
public class OCRController {
    private final OCRService ocrService;
    @Autowired
    public OCRController(OCRService ocrService) {
        this.ocrService = ocrService;
    }

    @PostMapping("/create")
    public void createOCR(@RequestParam  MultipartFile image) {
        ocrService.sendImage(image);
    }

}
