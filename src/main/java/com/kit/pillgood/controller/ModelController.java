package com.kit.pillgood.controller;

import com.google.gson.JsonObject;
import com.kit.pillgood.persistence.dto.OriginalOcrDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.util.Base64Utils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.Base64;


@RestController
@RequestMapping("/model")
public class ModelController {
    private final RestTemplate restTemplate;

    @Autowired
    public ModelController(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    /**
     * OCR 결과를 얻기 위해 파이썬 모델 서버로 이미지 전송
     * @param: MultipartFile image, 모델서버로 전송할 이미지
     * @return: ResponseEntity<ResponseFormat>, OCR 결과가 담긴 응답 객체
     **/
    @PostMapping("")
    public OriginalOcrDTO sendImage(@RequestParam byte[] image) {

        try {

            // 이미지 데이터를 Base64 인코딩하여 문자열로 변환
            String encodedImage = Base64Utils.encodeToString(image);
            // JSON 객체 생성 및 이미지 데이터 추가
            JsonObject json = new JsonObject();
            json.addProperty("image",  encodedImage);
            // HTTP 요청 헤더 설정
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
    
            // HTTP 요청 본문 설정
            HttpEntity<String> requestEntity = new HttpEntity<>(json.toString(), headers);
            // 파이썬 모델 서버 URL
            String url = "http://127.0.0.1:5000/ocr";
            // POST 요청 보내기

            ResponseEntity<OriginalOcrDTO> response = restTemplate.exchange(url, HttpMethod.POST, requestEntity, OriginalOcrDTO.class);

            // 응답 처리
            if (response.getStatusCode() == HttpStatus.OK) {
                OriginalOcrDTO OCRResult = response.getBody();

                return OCRResult;
            } else {
                // 응답 실패 시 처리 로직 작성
                System.out.println("요청 실패1: " + response.getStatusCode());
            }
        } catch (Exception e) {
            System.out.println("요청 실패2: " + e.getMessage());
        }
        return null;
    }
}





