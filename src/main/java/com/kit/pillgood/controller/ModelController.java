package com.kit.pillgood.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kit.pillgood.persistence.dto.OriginalOcrDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.*;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;

@RestController
@RequestMapping("/model")
public class ModelController {
    private final RestTemplate restTemplate;

    @Autowired
    public ModelController(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @PostMapping("")
    public OriginalOcrDTO sendImage(@RequestParam MultipartFile image) {

        try {
            // 이미지를 임시 파일로 저장
            Path tempFilePath = Files.createTempFile("image", image.getOriginalFilename());
            Files.copy(image.getInputStream(), tempFilePath, StandardCopyOption.REPLACE_EXISTING);

            // Multipart 요청 본문 생성
            MultiValueMap<String, Object> requestBody = new LinkedMultiValueMap<>();
            requestBody.add("image", new FileSystemResource(tempFilePath.toFile()));

            // HTTP 요청 헤더 설정
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.MULTIPART_FORM_DATA);

            // HTTP 요청 본문 및 헤더 설정
            HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(requestBody, headers);

            // 파이썬 모델 서버 URL
            String url = "http://127.0.0.1:5000/ocr";

            // POST 요청 보내기
            RestTemplate restTemplate = new RestTemplate();
            ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.POST, requestEntity, String.class);

            // 임시 파일 삭제
            Files.delete(tempFilePath);

            // 응답 처리
            if (response.getStatusCode() == HttpStatus.OK) {
                // 응답 성공 시 처리 로직 작성
                String responseBody = response.getBody();

                // JSON 문자열을 Map으로 변환
                ObjectMapper objectMapper = new ObjectMapper();
                OriginalOcrDTO resultOCR = objectMapper.readValue(responseBody, new TypeReference<OriginalOcrDTO>() {});
                return resultOCR;
            } else {
                // 응답 실패 시 처리 로직 작성
                System.out.println("요청 실패: " + response.getStatusCode());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}





