package com.kit.pillgood.controller;

import com.google.gson.JsonObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.util.Base64;
import java.util.Map;

@RestController
@RequestMapping("/model")
public class ModelController {
    private final RestTemplate restTemplate;

    @Autowired
    public ModelController(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @PostMapping("")
    public void sendImage(@RequestParam MultipartFile image) {

        try {
            // 이미지 데이터를 Base64 인코딩하여 문자열로 변환
            String encodedImage = Base64.getEncoder().encodeToString(image.getBytes());

            // JSON 객체 생성 및 이미지 데이터 추가
            JsonObject json = new JsonObject ();
            json.addProperty("image", encodedImage);

            // HTTP 요청 헤더 설정
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);

            // HTTP 요청 본문 설정
            HttpEntity<String> requestEntity = new HttpEntity<>(json.toString(), headers);

            // 파이썬 모델 서버 URL
            String url = "http://127.0.0.1:5000/ocr";

            // POST 요청 보내기
            ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.POST, requestEntity, String.class);

            // 응답 처리
            if (response.getStatusCode() == HttpStatus.OK) {
                // 응답 성공 시 처리 로직 작성
                Map<String, String> OCRResult = response.getBody();

            } else {
                // 응답 실패 시 처리 로직 작성
                System.out.println("요청 실패: " + response.getStatusCode());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}





